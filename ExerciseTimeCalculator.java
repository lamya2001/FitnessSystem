package com.mycompany.fitnesssystem;

public class ExerciseTimeCalculator {
    package static int calculateTime(int timeInPlan, String fitnessLevel) {
        int additionalMinutes;
        switch (fitnessLevel) {
            case "Beginner":
                additionalMinutes = 30;
                break;
            case "Intermediate":
                additionalMinutes = 20;
                break;
            case "Advanced":
                additionalMinutes = 10;
                break;
            default:
                additionalMinutes = 0;
        }
        return timeInPlan + additionalMinutes;
    }
}
