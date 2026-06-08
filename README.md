# Automated Outreach Pipeline - Java

![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)
![License](https://img.shields.io/badge/License-MIT-green)
![Status](https://img.shields.io/badge/Status-Completed-success)

## Overview

Automated Outreach Pipeline is an end-to-end command-line outreach automation system built using Java, Maven, Prospeo API, and Brevo API.

The application accepts a company domain as input, discovers relevant prospects, enriches their profiles to retrieve verified email addresses, displays a review summary, and sends personalized outreach emails through Brevo after user confirmation.

This project was developed as part of the SDE Internship Assignment for Subspace / Vocallabs.

---

## Features

* Accepts a company domain as input
* Finds prospects using Prospeo Search Person API
* Enriches prospects using Prospeo Enrich Person API
* Retrieves verified contact information
* Extracts:

  * Name
  * Email Address
  * Company Name
  * LinkedIn Profile
  * Company Website
* Displays outreach summary before sending
* Requires user confirmation before email delivery
* Sends personalized outreach emails using Brevo API
* Uses environment variables for secure credential management
* Handles API failures and edge cases gracefully
* Built using a modular Java service architecture

---

## Architecture

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
Generate Prospect Summary
        ↓
User Confirmation
        ↓
Brevo Email API
        ↓
Email Delivered
```

---

## Tech Stack

| Technology       | Purpose                                    |
| ---------------- | ------------------------------------------ |
| Java 17          | Core application development               |
| Maven            | Dependency management and build automation |
| OkHttp           | HTTP client for API communication          |
| Jackson Databind | JSON parsing and processing                |
| Dotenv Java      | Environment variable management            |
| Prospeo API      | Prospect discovery and enrichment          |
| Brevo API        | Email delivery service                     |
| Git & GitHub     | Version control                            |

---

## Project Structure

```text
automated-outreach-pipeline
│
├── .mvn
│
├── src
│   └── main
│       └── java
│           └── com
│               └── ashwani
│                   └── outreach
│
│                       ├── App.java
│
│                       ├── model
│                       │   └── Prospect.java
│
│                       └── service
│                           ├── ProspeoService.java
│                           └── BrevoService.java
│
├── pom.xml
├── .gitignore
├── LICENSE
└── README.md
```

---

## Environment Variables

Create a `.env` file in the project root:

```env
PROSPEO_API_KEY=your_prospeo_api_key
BREVO_API_KEY=your_brevo_api_key

SENDER_EMAIL=contact@yourdomain.com
SENDER_NAME=Your Name
```

### Security Note

* The `.env` file is excluded from GitHub using `.gitignore`
* API keys are never hardcoded
* Sensitive credentials are stored locally
* Email sending requires explicit user confirmation

---

## Installation

### Clone Repository

```bash
git clone https://github.com/AshwaniKumarGupta01/Automated-Outreach-Pipeline-Java.git
```

### Navigate to Project

```bash
cd Automated-Outreach-Pipeline-Java
```

### Install Dependencies

```bash
mvn clean compile
```

---

## Running the Application

```bash
mvn exec:java "-Dexec.mainClass=com.ashwani.outreach.App"
```

---

## Example Execution

```text
Enter company domain: google.com

First Person ID Found: aaaaa5c4180b95d38fb1b9246

Enrich Response Code: 200

===== Outreach Summary =====

Name: Zara Najam
Email: zaranajam@google.com
Company: Google

LinkedIn:
https://www.linkedin.com/in/zaranajam

Website:
https://google.com

============================

Do you want to send this email? (yes/no): yes

Brevo Response Code: 201

Email sent successfully.
```

---

## Error Handling

The application handles the following scenarios:

### No Matching Prospect

```text
NO_MATCH
```

Returned when no valid email or profile is available.

### API Rate Limit

```text
RATE_LIMITED
```

Returned when API usage limits are exceeded.

### Invalid Credentials

```text
INVALID_API_KEY
```

Returned when API authentication fails.

### Additional Validations

* Empty API responses
* Missing prospect details
* Invalid company domains
* User cancellation before email delivery

---

## Sample Test Domains

You can test the application using:

```text
google.com
microsoft.com
amazon.com
```

---

## Demo Screenshots

Add your screenshots here after uploading them to GitHub.

### Prospect Discovery

```text
screenshots/google-search.png
```

### Prospect Summary

```text
screenshots/prospect-summary.png
```

### Email Delivery

```text
screenshots/brevo-response.png
```

---

## Key Learnings

During this project, I gained hands-on experience with:

* REST API integration using Java
* HTTP communication using OkHttp
* JSON parsing using Jackson
* Environment variable management
* Secure API key handling
* Email automation workflows
* Maven project structure
* Error handling and validation
* Real-world third-party API integration

---

## Future Enhancements

Potential improvements include:

* Bulk prospect discovery
* CSV export functionality
* Custom email templates
* Multi-threaded processing
* CRM integrations
* Logging and monitoring support
* Web-based dashboard
* Automated campaign management

---

## Notes

Prospeo APIs were used for prospect discovery and prospect enrichment, while Brevo was used for email delivery. The implementation follows the internship assignment requirements and recruiter-provided clarifications.

---

## Author

**Ashwani Kumar Gupta**

B.Tech Computer Science & Engineering
Lovely Professional University

GitHub:
https://github.com/AshwaniKumarGupta01

LinkedIn:
https://www.linkedin.com/

---

## License

This project is licensed under the MIT License.
