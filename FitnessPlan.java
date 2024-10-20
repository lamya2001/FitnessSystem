package com.mycompany.fitnesssystem;

public class FitnessPlan
{
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
    
    // Getters
    public String getName() {
        return name;
    }
    public int getMinDurationPerWeek() {
        return minDurationPerWeek;
    }
    public String getMinFitnessLevel() {
        return minFitnessLevel;
    }
    public String getHealthGoal() {
        return healthGoal;
    }
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setMinDurationPerWeek(int minDurationPerWeek) {
        this.minDurationPerWeek = minDurationPerWeek;
    }
    public void setMinFitnessLevel(String minFitnessLevel) {
        this.minFitnessLevel = minFitnessLevel;
    }
    public void setHealthGoal(String healthGoal) {
        this.healthGoal = healthGoal;
    }
    
    public boolean isSuitable(String fitnessGoal, String fitnessLevel) {
        return this.healthGoal.equals(fitnessGoal) && this.minFitnessLevel.equals(fitnessLevel);
    }
    
    public void displayPlanDetails() {
        System.out.println("\n\tFitness Plan: " + getName());
        System.out.println("\tMinimum Duration per Week: " + getMinDurationPerWeek() + " minutes");
        System.out.println("\tMinimum Required Fitness Level: " + getMinFitnessLevel());
        System.out.println("\tHealth Goal: " + getHealthGoal());
        System.out.println();
    }
}
