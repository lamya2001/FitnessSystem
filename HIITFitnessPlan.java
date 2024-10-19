package com.mycompany.fitnesssystem;

public class HIITFitnessPlan {
    // الخصائص الأساسية لخطة HIIT
    private final String category = "HIIT";
    private final int recommendedDuration = 90; // بالدقائق في الأسبوع
    private final String requiredFitnessLevel = "Advanced";
    private final String healthGoal = "Weight Loss";

    // الخصائص للمستخدمين
    private int userCurrentFitnessLevel; // 1 = Beginner, 2 = Intermediate, 3 = Advanced
    private int additionalMinutes; // الدقائق الإضافية المطلوبة بناءً على مستوى اللياقة

    // البناء
    public HIITFitnessPlan(int userCurrentFitnessLevel) {
        this.userCurrentFitnessLevel = userCurrentFitnessLevel;
        this.additionalMinutes = calculateAdditionalMinutes(userCurrentFitnessLevel);
    }

    // دالة لحساب الدقائق الإضافية
    private int calculateAdditionalMinutes(int fitnessLevel) {
        switch (fitnessLevel) {
            case 1: // Beginner
                return 30;
            case 2: // Intermediate
                return 20;
            case 3: // Advanced
                return 10;
            default:
                return 0; // لا توجد دقائق إضافية لمستويات غير موجودة
        }
    }

    // دالة للحصول على الوقت المطلوب أسبوعياً
    public int getTotalWeeklyExerciseTime() {
        return recommendedDuration + additionalMinutes;
    }

    // دوال للحصول على المعلومات الخاصة بالخطة
    public String getCategory() {
        return category;
    }

    public String getRequiredFitnessLevel() {
        return requiredFitnessLevel;
    }

    public String getHealthGoal() {
        return healthGoal;
    }

    // دالة لطباعة المعلومات الخاصة بالخطة
    public void displayPlanInfo() {
        System.out.println("Fitness Plan Category: " + getCategory());
        System.out.println("Recommended Duration (minutes/week): " + recommendedDuration);
        System.out.println("Required Fitness Level: " + getRequiredFitnessLevel());
        System.out.println("Health Goal: " + getHealthGoal());
        System.out.println("Total Weekly Exercise Time Required: " + getTotalWeeklyExerciseTime() + " minutes");
    }
}
