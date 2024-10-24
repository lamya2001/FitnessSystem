package com.mycompany.fitnesssystem;

package class FitnessPlan
{
    protected String name;
    protected int minDurationPerWeek; 
    protected String minFitnessLevel;
    protected String healthGoal;

    package FitnessPlan(String name, int minDurationPerWeek, String minFitnessLevel, String healthGoal) {
        this.name = name;
        this.minDurationPerWeek = minDurationPerWeek;
        this.minFitnessLevel = minFitnessLevel;
        this.healthGoal = healthGoal;
    }
    
    // Getters
    package String getName() {
        return name;
    }
    package int getMinDurationPerWeek() {
        return minDurationPerWeek;
    }
    package String getMinFitnessLevel() {
        return minFitnessLevel;
    }
    package String getHealthGoal() {
        return healthGoal;
    }
    // Setters
    package void setName(String name) {
        this.name = name;
    }
    package void setMinDurationPerWeek(int minDurationPerWeek) {
        this.minDurationPerWeek = minDurationPerWeek;
    }
    package void setMinFitnessLevel(String minFitnessLevel) {
        this.minFitnessLevel = minFitnessLevel;
    }
    package void setHealthGoal(String healthGoal) {
        this.healthGoal = healthGoal;
    }
    
   package boolean isSuitable(String fitnessGoal, String fitnessLevel) {
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
    
    package void displayPlanDetails() {
        System.out.println("\n\tFitness Plan: " + getName());
        System.out.println("\tMinimum Duration per Week: " + getMinDurationPerWeek() + " minutes");
        System.out.println("\tMinimum Required Fitness Level: " + getMinFitnessLevel());
        System.out.println("\tHealth Goal: " + getHealthGoal());
        System.out.println();
    }
}
