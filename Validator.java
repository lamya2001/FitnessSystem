package com.mycompany.fitnesssystem;

public class Validator {

    protected static int getValidInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;        
        }
    }

    protected static int getValidOption(String input, int maxOption) {
        if (isValidInput(input, maxOption)) {
            return Integer.parseInt(input); 
        } else {
            return -1; 
        }
    }

    
    protected static boolean isValidInput(String input, int maxOption) {
        try {
            int option = Integer.parseInt(input);
            return option >= 1 && option <= maxOption; 
        } catch (NumberFormatException e) {
            return false;
        }
    }
    protected static boolean isValidUsername(String username) {
	    // Implement validation logic for username
	    return username.matches("[a-zA-Z0-9]+");
	}

	protected static boolean isValidPassword(String password) {
	    // Implement validation logic for password
	    return password.length() >= 8;
	}
}
