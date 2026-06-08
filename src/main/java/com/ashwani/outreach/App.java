package com.ashwani.outreach;

import java.util.Scanner;

import com.ashwani.outreach.model.Prospect;
import com.ashwani.outreach.service.BrevoService;
import com.ashwani.outreach.service.ProspeoService;

public class App {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter company domain: ");
        String companyDomain = scanner.nextLine();

        ProspeoService prospeoService = new ProspeoService();

        String personId = prospeoService.getFirstPersonId(companyDomain);

        if (personId == null || personId.equals("N/A")) {
            System.out.println("No valid person ID found.");
            prospeoService.shutdown();
            scanner.close();
            return;
        }

        Prospect prospect = prospeoService.enrichPerson(personId);

        if (prospect == null || prospect.getEmail().equals("N/A")) {
            System.out.println("No valid email found.");
            prospeoService.shutdown();
            scanner.close();
            return;
        }

        System.out.println("\n===== Outreach Summary =====");
        System.out.println(prospect);
        System.out.println("============================");

        System.out.print("\nDo you want to send this email? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            BrevoService brevoService = new BrevoService();
            brevoService.sendOutreachEmail(prospect);
        } else {
            System.out.println("Email sending cancelled.");
        }

        scanner.close();
        prospeoService.shutdown();
    }
}