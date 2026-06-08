package com.ashwani.outreach.service;

import java.io.IOException;

import com.ashwani.outreach.model.Prospect;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BrevoService {

    private static final String BREVO_URL = "https://api.brevo.com/v3/smtp/email";

    private final String apiKey;
    private final String senderEmail;
    private final String senderName;

    public BrevoService() {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .load();

        this.apiKey = dotenv.get("BREVO_API_KEY");
        this.senderEmail = dotenv.get("SENDER_EMAIL");
        this.senderName = dotenv.get("SENDER_NAME");
    }

    public void sendOutreachEmail(Prospect prospect) throws IOException {

        OkHttpClient client = new OkHttpClient();

        String subject = "Quick idea for " + prospect.getCompany();

        String htmlContent = """
        <html>
          <body>
            <p>Hi %s,</p>

            <p>I came across %s and noticed your work at %s.</p>

            <p>I am currently building an automated outreach pipeline that helps teams discover decision-makers, verify their emails, and send personalized outreach automatically.</p>

            <p>I thought this could be relevant for your team, especially if you are exploring ways to improve outbound workflows.</p>

            <p>Would you be open to a quick conversation?</p>

            <p>Regards,<br>%s</p>
          </body>
        </html>
        """.formatted(
                prospect.getName(),
                prospect.getLinkedinUrl(),
                prospect.getCompany(),
                senderName
        );

        String jsonBody = """
        {
          "sender": {
            "name": "%s",
            "email": "%s"
          },
          "to": [
            {
              "email": "%s",
              "name": "%s"
            }
          ],
          "subject": "%s",
          "htmlContent": "%s"
        }
        """.formatted(
                senderName,
                senderEmail,
                prospect.getEmail(),
                prospect.getName(),
                escapeJson(subject),
                escapeJson(htmlContent)
        );

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(BREVO_URL)
                .addHeader("api-key", apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Brevo Response Code: " + response.code());

            if (response.body() != null) {
                System.out.println(response.body().string());
            }
        } finally {
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();
        }
    }

    private String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "")
                .replace("\r", "");
    }
}