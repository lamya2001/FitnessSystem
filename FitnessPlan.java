package com.mycompany.fitnesssystem;

public class FitnessPlan
{
    protected String name;
    protected int minDurationPerWeek; 
    protected String minFitnessLevel;
    protected String healthGoal;

    protected FitnessPlan(String name, int minDurationPerWeek, String minFitnessLevel, String healthGoal) {
        this.name = name;
        this.minDurationPerWeek = minDurationPerWeek;
        this.minFitnessLevel = minFitnessLevel;
        this.healthGoal = healthGoal;
    }
    
    // Getters
    protected String getName() {
        return name;
    }
    protected int getMinDurationPerWeek() {
        return minDurationPerWeek;
    }
    protected String getMinFitnessLevel() {
        return minFitnessLevel;
    }
    protected String getHealthGoal() {
        return healthGoal;
    }
    // Setters
    protected void setName(String name) {
        this.name = name;
    }
    protected void setMinDurationPerWeek(int minDurationPerWeek) {
        this.minDurationPerWeek = minDurationPerWeek;
    }
    protected void setMinFitnessLevel(String minFitnessLevel) {
        this.minFitnessLevel = minFitnessLevel;
    }
    protected void setHealthGoal(String healthGoal) {
        this.healthGoal = healthGoal;
    }
    
   protected boolean isSuitable(String fitnessGoal, String fitnessLevel) {
    return this.healthGoal.equals(fitnessGoal) && compareFitnessLevels(fitnessLevel, this.minFitnessLevel);
    }

    
    private boolean compareFitnessLevels(String userLevel, String minRequiredLevel) {
        int userLevelValue = getFitnessLevelValue(userLevel);
        int requiredLevelValue = getFitnessLevelValue(minRequiredLevel);

        return userLevelValue >= requiredLevelValue; 
    }

   
    private int getFitnessLevelValue(String level) {
        switch (level) {
            case "Beginner":
                return 1;
            case "Intermediate":
                return 2;
            case "Advanced":
                return 3;
            default:
                return 0; 
        }
    }
    
    protected void displayPlanDetails() {
        System.out.println("\n\tFitness Plan: " + getName());
        System.out.println("\tMinimum Duration per Week: " + getMinDurationPerWeek() + " minutes");
        System.out.println("\tMinimum Required Fitness Level: " + getMinFitnessLevel());
        System.out.println("\tHealth Goal: " + getHealthGoal());
        System.out.println();
    }
}
