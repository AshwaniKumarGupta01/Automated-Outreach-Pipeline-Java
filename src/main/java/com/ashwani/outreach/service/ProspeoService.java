package com.ashwani.outreach.service;

import java.io.IOException;

import com.ashwani.outreach.model.Prospect;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProspeoService {

    private static final String SEARCH_URL = "https://api.prospeo.io/search-person";
    private static final String ENRICH_URL = "https://api.prospeo.io/enrich-person";

    private final String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public ProspeoService() {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .load();

        this.apiKey = dotenv.get("PROSPEO_API_KEY");
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
    }

    public String getFirstPersonId(String companyDomain) throws IOException {

        String jsonBody = """
        {
          "page": 1,
          "filters": {
            "company": {
             "websites": {
               "include": ["%s"]
          }
        },
        "person_seniority": {
         "include": ["Founder/Owner"]
    }
  }
}
""".formatted(companyDomain);

        Request request = buildPostRequest(SEARCH_URL, jsonBody);

        try (Response response = client.newCall(request).execute()) {

            String responseBody = response.body() != null ? response.body().string() : "";

            if (!response.isSuccessful()) {
                System.out.println("Search API Error:");
                System.out.println(responseBody);
                return null;
            }

            JsonNode root = mapper.readTree(responseBody);
            JsonNode results = root.get("results");

            if (results == null || !results.isArray() || results.isEmpty()) {
                System.out.println("No search results found.");
                return null;
            }

            JsonNode firstLead = results.get(0);
            JsonNode person = firstLead.get("person");

            String personId = getValue(person, "person_id");

            System.out.println("First Person ID Found: " + personId);

            return personId;
        }
    }

    public Prospect enrichPerson(String personId) throws IOException {

        String jsonBody = """
        {
          "only_verified_email": true,
          "data": {
            "person_id": "%s"
          }
        }
        """.formatted(personId);

        Request request = buildPostRequest(ENRICH_URL, jsonBody);

        try (Response response = client.newCall(request).execute()) {

            String responseBody = response.body() != null ? response.body().string() : "";

            System.out.println("Enrich Response Code: " + response.code());

            if (!response.isSuccessful()) {
                System.out.println("Enrich API Error:");
                System.out.println(responseBody);
                return null;
            }

            JsonNode root = mapper.readTree(responseBody);

            JsonNode person = root.get("person");
            JsonNode company = root.get("company");

            String name = getValue(person, "full_name");
            String linkedinUrl = getValue(person, "linkedin_url");
            String companyName = getValue(company, "name");
            String companyWebsite = getValue(company, "website");

            String email = "N/A";

            if (person != null && person.has("email") && person.get("email").has("email")) {
                email = person.get("email").get("email").asText();
            }

            return new Prospect(
                    name,
                    email,
                    companyName,
                    linkedinUrl,
                    companyWebsite
            );
        }
    }

    private Request buildPostRequest(String url, String jsonBody) {
        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        return new Request.Builder()
                .url(url)
                .addHeader("X-KEY", apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
    }

    private String getValue(JsonNode node, String fieldName) {
        if (node != null && node.has(fieldName) && !node.get(fieldName).isNull()) {
            return node.get(fieldName).asText();
        }
        return "N/A";
    }

    public void shutdown() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }
}