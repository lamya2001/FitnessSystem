package com.mycompany.fitnesssystem;

public class FitnessPlan {
    protected String name;
    protected int minDurationPerWeek; 
    protected String minFitnessLevel;
    protected String healthGoal;

    public FitnessPlan(String name, int minDurationPerWeek, String minFitnessLevel, String healthGoal) {
        this.name = name;
        this.minDurationPerWeek = minDurationPerWeek;
        this.minFitnessLevel = minFitnessLevel;
        this.healthGoal = healthGoal;
    }
    
   
    public boolean isSuitable(String fitnessGoal, String fitnessLevel) {
        return this.healthGoal.equals(fitnessGoal) && this.minFitnessLevel.equals(fitnessLevel);
    }
    
   
    public void displayPlanDetails() {
        System.out.println("Fitness Plan: " + name);
        System.out.println("Minimum Duration per Week: " + minDurationPerWeek + " minutes");
        System.out.println("Minimum Required Fitness Level: " + minFitnessLevel);
        System.out.println("Health Goal: " + healthGoal);
        System.out.println();
    }
}
