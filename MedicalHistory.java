package com.mycompany.fitnesssystem;

import java.util.Scanner;
import java.util.ArrayList;

public class MedicalHistory {
    private static ArrayList<String> healthNotes = new ArrayList<>();
    
    public static void collectMedicalHistory() {
        
        Scanner scanner =new Scanner (System.in);
        
        healthNotes.clear();
        int age;
        int illnesses;
        int surgeries;

//        System.out.println("----------Medical History------------");

        while (true) {
            System.out.println("----------Age------------");
            System.out.println("Enter your age: ");
            String input = scanner.nextLine();
            age = Validator.getValidInteger(input);
            if (age >= 6 && age <= 18) {
                 healthNotes.add("Do not lift heavy weights during exercise, and do not exceed one hour per day.");
                break;
            } else if (age > 18 && age <= 60) {
                break;
            } else if (age > 60) {
                healthNotes.add("Start exercising gradually, and take breaks when feeling pain.");
                break;
            } else {
                System.out.println("Invalid age. Please enter a valid age \"6 and more\".");
            }
        }
        
        while (true) {
            System.out.println("----------Illnesses------------");
            System.out.println("Do you have any illnesses?:\n"
                    + "1- yes\n"
                    + "2- no");
            String inputIllness = scanner.nextLine();
            illnesses = Validator.getValidOption(inputIllness, 2);
            switch (illnesses) {
                case 1:
                    healthNotes.add("Discuss the proposed fitness plan with your doctor to ensure it suits your health condition.");
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option 1 or 2.");
                    continue; 
            }
            break; 
        }
        
        while (true) {
            System.out.println("----------Surgeries------------");
            System.out.println("Did you have any surgeries?:\n"
                        + "1- yes\n"
                        + "2- no");

            String inputSurgeries = scanner.nextLine();
            surgeries = Validator.getValidOption(inputSurgeries, 2);

            switch (surgeries) {
                case 1:
                    healthNotes.add("Ask your doctor about the appropriate time to start exercising after surgery and whether it is suitable for your health condition.");
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option 1 or 2.");
                   continue; 
            }
            break; 
        }
    }
    public static String getHealthNotes() {
        return String.join("\n", healthNotes);
    }
}
