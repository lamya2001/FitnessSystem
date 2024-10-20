package com.mycompany.fitnesssystem;

import java.util.Scanner;

public class FitnessSystem {

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int systemState = -1;

    System.out.println("*Welcome to the Personalized Fitness Plan Recommendation System*");

        do {
        System.out.println("----------Main Menu------------");
        System.out.println("Enter The Process number you want:\n"
                + "1- Start looking for your fit fitness plan.\n"
                + "2- Exit.");

        String input = scanner.nextLine();
         systemState = Validator.getValidOption(input, 2);         
            switch (systemState){
                case 1:
                    startFitnessPlan(scanner);
                    break;
                case 2:
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                    break;
              }
        }while (true);
    }


    private static void startFitnessPlan(Scanner scanner) {
        String fitnessGoal = selectFitnessGoal(scanner);
        if (fitnessGoal == null) {
            return;
        }
        
        String fitnessLevel = selectFitnessLevel(scanner);
        if (fitnessLevel == null) {
            return;
        }
        
        MedicalHistory.collectMedicalHistory();
        FitnessPlan[] plans = {
        new HIIT(),
        // أضف خطط أخرى هنا
        };
  
        for (FitnessPlan plan : plans) {
            if (plan.isSuitable(fitnessGoal, fitnessLevel)) {
                plan.displayPlanDetails();
                System.out.println("Required Weekly Exercise Time Based on Your Level:"
                        +ExerciseTimeCalculator.calculateTime(plan.minDurationPerWeek,fitnessLevel));
                System.out.println("Additional Notes:\n"
                        +MedicalHistory.getHealthNotes());               
                return;
            }else{
                System.out.println("No suitable plan found for your fitness goal and level.");
                 return;
            }
        }
    }

    private static String selectFitnessGoal(Scanner scanner) {
        String fitnessGoal = null;
        while (fitnessGoal == null) {
            System.out.println("----------Fitness Goal Menu------------");
            System.out.println("Enter your Fitness Goal:\n"
                    + "1- Weight Loss\n"
                    + "2- Muscle Building\n"
                    + "3- Improve Cardiovascular Health\n"
                    + "4- Stress Relief\n"
                    + "5- Return to Main Menu");
            
            String input = scanner.nextLine();
            int goalOption = Validator.getValidOption(input, 5);
            switch (goalOption) {
                case 1:
                    return "Weight Loss";
                case 2:
                    return "Muscle Building";
                case 3:
                    return "Improve Cardiovascular Health";
                case 4:
                    return "Stress Relief";
                case 5:
                    return null; 
                default:
                    System.out.println("Invalid choice. Please enter a valid option from 1 to 5.");
                    break;
            }
        }
        return fitnessGoal;
    }

    private static String selectFitnessLevel(Scanner scanner) {
        String fitnessLevel = null;
        while (fitnessLevel == null) {
            System.out.println("----------Fitness Level Menu------------");
            System.out.println("Enter your Current Fitness Level:\n"
                    + "1- Beginner\n"
                    + "2- Intermediate\n"
                    + "3- Advanced\n"
                    + "4- Return to Main Menu");

            String input = scanner.nextLine();
            int levelOption = Validator.getValidOption(input, 4);
            
            switch (levelOption) {
                case 1:
                    return "Beginner";
                case 2:
                    return "Intermediate";
                case 3:
                    return "Advanced";
                case 4:
                    return null; 
                default:
                    System.out.println("Invalid choice. Please enter a valid option from 1 to 4.");
                    break;
            }
        }
        return fitnessLevel;
    }


}
