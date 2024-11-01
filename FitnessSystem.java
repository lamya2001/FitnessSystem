package com.mycompany.fitnesssystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Date;
import java.text.SimpleDateFormat;

public class FitnessSystem {
	private static final String DEFAULT_GOAL = "Weight Loss";
	private static String DEFAULT_LEVEL;
	private static final String CREDENTIALS_FILE = "credentials.txt"; // File to store username and password
   	private static final String FEEDBACK_FILE = "feedback.txt"; // File to store feedback	
        private static Timer inactivityTimer; // Timer for inactivity after login
        private static final int FIRST_WARNING_TIME = 15000; // 15 seconds
        private static final int EXIT_SYSTEM = 25000; // 25 seconds (15+10)
	private static Logger logger = Logger.getLogger("UserActivityLog");
	private static FileHandler fh;
        private static String currentUsername;


	public static void main(String[] args) {
				Scanner scanner = new Scanner(System.in);
            try {
	        fh = new FileHandler("user_activity.log", true); // Append mode set to true
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();
	        fh.setFormatter(formatter);

	        logger.setLevel(Level.INFO); // Set the logging level as needed

	        System.out.println("\n* Welcome to the Personalized Fitness Plan Recommendation System *");
	        // The rest of your code...

	        boolean loggedIn = false;

	        // Prompt for login or account creation until successful login
	        while (!loggedIn) {
	            if (accountExists()) {
	                // Prompt for login if the account exists
	                loggedIn = login(scanner);
	                
	                if (!loggedIn) {
	                    System.out.println("Login failed. Would you like to create a new account? (yes/no)");
	                    String choice = scanner.nextLine().trim().toLowerCase();
	                    if (choice.equals("yes")) {
	                        createAccount(scanner);
	                    }
	                }
	            } else {
	                // No account exists, ask for account creation
	                System.out.println("No account found. Please create a new account.");
	                createAccount(scanner);
	            }
	            logUserActivity("User: " + currentUsername + " logged in", getCurrentTime());
	        }
	        // After successful login, start inactivity timer in the main menu
	        startInactivityTimer();

	        // Show the main menu
	        int systemState = -1;
	        do {
	            System.out.println("\n----------Main Menu------------");
	            System.out.println("1- Start looking for your fit fitness plan.\n" 
			            		+ "2- Exit.\n"
			                    + ">> Enter the process number you want:");

	            String input = scanner.nextLine();
	            systemState = Validator.getValidOption(input, 2);
	            switch (systemState) {
	                case 1:
	                    startFitnessPlan(scanner);
	                    break;
	                case 2:
	                    System.out.println("\nThank you for using the system. Goodbye!\n");
	                    logUserActivity("User: " + currentUsername + " logged out", getCurrentTime());
	        	        fh.close();
	                    System.exit(0);
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please enter 1 or 2.");
	                    break;
	            }
	            resetInactivityTimer();
	        } while (true);

	    } catch (IOException e) {
	        e.printStackTrace();
	    } 
	}
	// Method to check if an account already exists by checking if the file exists
	private static boolean accountExists() {
		File file = new File(CREDENTIALS_FILE);
		return file.exists();
	}

	// Account creation method
	private static void createAccount(Scanner scanner) {
		System.out.print("Enter a username: ");
		String username = scanner.nextLine();
		// Validate username and password
		if (!Validator.isValidUsername(username)) {
			System.out.println("Invalid username. Please use only alphanumeric characters.");
			return;
		}
		
		System.out.print("Enter a password: ");
		String password = scanner.nextLine();
		if (!Validator.isValidPassword(password)) {
			System.out.println("Invalid password. Password must be at least 8 characters long.");
			return;
		}

		String hashedPassword = hashPassword(password); // Hash the password

		// Store the credentials in a file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE, true))) {
			writer.write(username + ":" + hashedPassword);
			 writer.newLine(); // Start a new line after each entry
                        System.out.println("Account created successfully!");
		} catch (IOException e) {
			System.out.println("Error storing credentials: " + e.getMessage());
		}
	}

	// Login method that reads the credentials from the file
	private static boolean login(Scanner scanner) {
		System.out.println("\nPlease login to continue.");
		System.out.print("Enter your username: ");
		String username = scanner.nextLine();
		System.out.print("Enter your password: ");
		String password = scanner.nextLine();
                String hashedPassword = hashPassword(password); // Hash the entered password

		// Read stored credentials from file
		try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
			String line;
                        while ((line = reader.readLine()) != null) {
                            String[] credentials = line.split(":");
                            String storedUsername = credentials[0];
                            String storedHashedPassword = credentials[1];

                            if (storedUsername.equals(username) && storedHashedPassword.equals(hashedPassword)) {
                                System.out.println("Login successful! Welcome " + storedUsername + "!");
                                currentUsername = storedUsername;
                                return true;
                            }
                        }
                        System.out.println("Invalid username or password.");
                } catch (IOException e) {
			System.out.println("Error reading credentials: " + e.getMessage());
		}
		return false;
	}

	// Hashing the password using SHA-256
	private static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder();

			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	private static void logUserActivity(String activity, String activityTime) {
		String logMessage = ">" + activityTime + " - " + activity;
		logger.info(logMessage);
	}

	private static String getCurrentTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

	private static void startFitnessPlan(Scanner scanner) {
		resetInactivityTimer(); // Reset timer when starting the fitness plan

		String fitnessGoal = selectFitnessGoal(scanner);
		if (fitnessGoal == null) {
			return;
		}

		String fitnessLevel = selectFitnessLevel(scanner, fitnessGoal);
		if (fitnessLevel == null) {
			return;
		}

		MedicalHistory.collectMedicalHistory();
		FitnessPlan[] plans = { new Cardio(), new StrengthTraining(), new HIIT(), new Yoga(), };

		boolean foundSuitablePlan = false;

		for (FitnessPlan plan : plans) {
			if (plan.isSuitable(fitnessGoal, fitnessLevel)) {
				plan.displayPlanDetails();
				System.out.println("\tRequired Weekly Exercise Time Based on Your Level: "
						+ ExerciseTimeCalculator.calculateTime(plan.getMinDurationPerWeek(), fitnessLevel)
						+ " minutes per week");
				System.out.println("\tAdditional Notes:\n" + MedicalHistory.getHealthNotes());
				foundSuitablePlan = true;	
			}
				resetInactivityTimer(); // Reset timer when starting the fitness plan
		}

		if (!foundSuitablePlan) {
			System.out.println("\n\tNo suitable plan found for your fitness goal and level.\n");
		}
                // Prompt for feedback after displaying the plan
		promptForFeedback(fitnessGoal, fitnessLevel);
	}
            //Method to start the inactivity timer after login
	    private static void startInactivityTimer() {
		inactivityTimer = new Timer();
	
		// First warning after 15 seconds of inactivity
		inactivityTimer.schedule(new TimerTask() {
		    @Override
		    public void run() {
			System.out.println(
				"\nYou have been inactive for 15 seconds. \nIf you do not provide input in the next 10 seconds, The system will shutdown.");
		    }
		}, FIRST_WARNING_TIME);
	
		// Return to login after an additional 10 seconds of inactivity (total 25
		// seconds)
		inactivityTimer.schedule(new TimerTask() {
		    @Override
		    public void run() {
			System.out.println("Returning to login screen due to inactivity.");
			inactivityTimer.cancel(); // Cancel the timer
			exitSystem(); // Reset the state and return to login
		    }
		}, EXIT_SYSTEM);
	    }
	    private static void exitSystem() {
        System.out.println("\nLogging out due to inactivity...");
        System.exit(0);
    }

    // Modified: Method to reset the inactivity timer after user interaction
    protected static void resetInactivityTimer() {
        if (inactivityTimer != null) {
            inactivityTimer.cancel();
        }
        startInactivityTimer(); // Restart the timer after resetting
    }
        //correct
	private static String selectFitnessGoal(Scanner scanner) {
		String fitnessGoal = null;
		while (fitnessGoal == null) {
                        resetInactivityTimer();
			System.out.println("\n----------Fitness Goal Menu------------");
			System.out.println("1- Weight Loss\n" + "2- Muscle Building\n" + "3- Improve Cardiovascular Health\n"
					+ "4- Stress Relief\n" + "5- Return to Main Menu\n" + ">> Enter your Fitness Goal:");

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
					return null; // Return to the main menu
				default:
					fitnessGoal = DEFAULT_GOAL;
					System.out.println("Invalid choice. ");
					System.out.println("Default goal selected: " + DEFAULT_GOAL);
					return fitnessGoal;
			}
		}
		return fitnessGoal;
	}
        
	private static String selectFitnessLevel(Scanner scanner, String fitnessGoal) {	
		String fitnessLevel = null;
		while (fitnessLevel == null) {
                    resetInactivityTimer();
			System.out.println("\n----------Fitness Level Menu------------");
			System.out.println("1- Beginner\n" + "2- Intermediate\n" + "3- Advanced\n" + "4- Return to Main Menu\n"
					+ ">> Enter your Current Fitness Level:");

			String input = scanner.nextLine();
			int levelOption = Validator.getValidOption(input, 4);

			// Check the selected fitness plan and set the minimum required level
			String minRequiredLevel = "";
			switch (fitnessGoal) {
				case "Weight Loss":
					minRequiredLevel = "Beginner";// Beginner or above accepted
					break;
				case "Muscle Building":
					minRequiredLevel = "Intermediate";//Intermediate or above accepted
					break;
				case "Improve Cardiovascular Health":
					minRequiredLevel = "Advanced";// Beginner or above accepted
					break;
				case "Stress Relief":
					minRequiredLevel = "Beginner";// Beginner or above accepted
					break;
			}

			// Set the DEFAULT_LEVEL based on the minRequiredLevel
			DEFAULT_LEVEL = minRequiredLevel;

			switch (levelOption) {
				case 1: // Beginner
                                    if (minRequiredLevel.equals("Beginner")) {
                                        fitnessLevel = "Beginner";
                                    } else {
                                        System.out.println("Invalid choice. Please select a higher fitness level.");
                                    }
                                    break;
                                case 2: // Intermediate
                                    if (minRequiredLevel.equals("Beginner") || minRequiredLevel.equals("Intermediate")) {
                                        fitnessLevel = "Intermediate";
                                    } else {
                                        System.out.println("Invalid choice. Please select a higher fitness level.");
                                    }
                                    break;
                                case 3: // Advanced
                                    if (minRequiredLevel.equals("Beginner") || minRequiredLevel.equals("Intermediate") || minRequiredLevel.equals("Advanced")) {
                                        fitnessLevel = "Advanced";
                                    } else {
                                        System.out.println("Invalid choice. Please select a higher fitness level.");
                                    }
                                    break;
				case 4:
					return null; // Return to the main menu
				default:
					fitnessLevel = DEFAULT_LEVEL;
					 System.out.println("Invalid choice. ");
					 System.out.println("Default Level selected: " + DEFAULT_LEVEL);
					break;
			}
		}
		return fitnessLevel;
	}
        
    // Method to prompt the user for feedback on the fitness plan
    private static void promptForFeedback(String fitnessGoal, String fitnessLevel) {
        Scanner scanner = new Scanner(System.in);
        int rating = -1;
        
        // Ask for a rating between 1 and 5
        while (rating < 1 || rating > 5) {
            resetInactivityTimer();
             System.out.println("\n----------Feed back------------");
            System.out.println("Please rate the fitness plan on a scale from 1 to 5 (5 = Excellent, 1 = Poor): ");
            String input = scanner.nextLine();
            try {
                rating = Integer.parseInt(input);
                if (rating < 1 || rating > 5) {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
            }
        }

        // Store the feedback in a file
        storeFeedback(currentUsername, fitnessGoal, fitnessLevel, rating);
    }

    // Method to store feedback in the feedback file
    private static void storeFeedback(String username, String fitnessGoal, String fitnessLevel, int rating) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE, true))) {
            writer.write("Username: " + username + ", Goal: " + fitnessGoal + ", Level: " + fitnessLevel + ", Rating: " + rating + "\n");
            System.out.println("Thank you for your feedback!");
        } catch (IOException e) {
            System.out.println("Error storing feedback: " + e.getMessage());
        }
    }
}
