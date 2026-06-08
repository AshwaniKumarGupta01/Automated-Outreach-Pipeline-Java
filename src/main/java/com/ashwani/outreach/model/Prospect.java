package com.ashwani.outreach.model;

public class Prospect {

    private String name;
    private String email;
    private String company;
    private String linkedinUrl;
    private String website;

    public Prospect(String name,
                    String email,
                    String company,
                    String linkedinUrl,
                    String website) {

        this.name = name;
        this.email = email;
        this.company = company;
        this.linkedinUrl = linkedinUrl;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nEmail: " + email +
                "\nCompany: " + company +
                "\nLinkedIn: " + linkedinUrl +
                "\nWebsite: " + website;
    }
}