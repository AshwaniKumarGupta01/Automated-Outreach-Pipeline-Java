# Automated Outreach Pipeline - Java

An end-to-end command-line outreach automation system built using Java, Maven, Prospeo API, and Brevo API.

This project was developed as part of the SDE Internship assignment for Subspace/Vocallabs. The system takes a company domain as input, finds relevant prospects, enriches their profile to retrieve verified email details, shows a safety confirmation checkpoint, and sends a personalized outreach email using Brevo.

## Features

* Accepts a company domain as input
* Finds prospects using Prospeo Search Person API
* Enriches prospects using Prospeo Enrich Person API
* Extracts name, email, company, LinkedIn URL, and website
* Shows outreach summary before sending
* Sends personalized email using Brevo API
* Uses environment variables for secure API key management
* Handles API errors such as `NO_MATCH`, `RATE_LIMITED`, and invalid responses
* Built as a command-line Java application

## Tech Stack

* Java 17
* Maven
* OkHttp
* Jackson Databind
* Dotenv Java
* Prospeo API
* Brevo API

## Project Flow

```text
Input Company Domain
        ↓
Prospeo Search Person API
        ↓
Get Person ID
        ↓
Prospeo Enrich Person API
        ↓
Get Verified Email
        ↓
Show Outreach Summary
        ↓
Safety Confirmation
        ↓
Brevo Email API
        ↓
Email Sent
```

## Project Structure

```text
automated-outreach-pipeline
│
├── src
│   └── main
│       └── java
│           └── com
│               └── ashwani
│                   └── outreach
│                       ├── App.java
│                       ├── model
│                       │   └── Prospect.java
│                       └── service
│                           ├── ProspeoService.java
│                           └── BrevoService.java
│
├── pom.xml
├── .gitignore
└── README.md
```

## Environment Variables

Create a `.env` file in the root directory:

```env
PROSPEO_API_KEY=your_prospeo_api_key
BREVO_API_KEY=your_brevo_api_key
SENDER_EMAIL=contact@yourdomain.com
SENDER_NAME=Your Name
```

> Note: The `.env` file is ignored using `.gitignore` and should never be pushed to GitHub.

## How to Run

Clone the repository:

```bash
git clone https://github.com/AshwaniKumarGupta01/automated-outreach-pipeline-java.git
```

Go to the project folder:

```bash
cd automated-outreach-pipeline-java
```

Install dependencies and compile:

```bash
mvn clean compile
```

Run the application:

```bash
mvn exec:java "-Dexec.mainClass=com.ashwani.outreach.App"
```

## Example Output

```text
Enter company domain: google.com

First Person ID Found: aaaaa5c4180b95d38fb1b9246
Enrich Response Code: 200

===== Outreach Summary =====
Name: Zara Najam
Email: zaranajam@google.com
Company: Google
LinkedIn: https://www.linkedin.com/in/zaranajam
Website: https://google.com
============================

Do you want to send this email? (yes/no): yes

Brevo Response Code: 201
Email sent successfully.
```

## Error Handling

The application handles common API scenarios:

* `NO_MATCH`: No verified email found for the prospect
* `RATE_LIMITED`: API rate limit reached
* `INVALID_API_KEY`: API key authentication issue
* Empty or invalid prospect data
* User cancellation before sending email

## Security Practices

* API keys are stored in `.env`
* `.env` is excluded from GitHub
* No sensitive credentials are hardcoded
* Email sending requires user confirmation

## Assignment Notes

The original assignment suggested Ocean.io, Prospeo, EazyReach, and Brevo. Based on recruiter clarification, Prospeo was used as a replacement for EazyReach to find people, LinkedIn profiles, and email IDs. Ocean.io alternatives were also allowed due to signup issues.

## Author

Ashwani Kumar Gupta
B.Tech CSE Student
Lovely Professional University
GitHub: AshwaniKumarGupta01
