package com.mycompany.fitnesssystem;

public class Validator {

    public static int getValidInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;        
        }
    }

    public static int getValidOption(String input, int maxOption) {
        if (isValidInput(input, maxOption)) {
            return Integer.parseInt(input); 
        } else {
            return -1; 
        }
    }

    
    public static boolean isValidInput(String input, int maxOption) {
        try {
            int option = Integer.parseInt(input);
            return option >= 1 && option <= maxOption; 
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
