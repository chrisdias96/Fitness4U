package com.example.myapplication.JavaBean;

public class Profile {

    private static String firstName;
    private static String lastName;
    private static String gender;
    private static String age;
    private static String weight;
    private static String goalWeight;
    private static String heightFeet;
    private static String heightInches;

    public Profile() {

    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        Profile.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        Profile.lastName = lastName;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        Profile.gender = gender;
    }

    public static String getAge() {
        return age;
    }

    public static void setAge(String age) {
        Profile.age = age;
    }

    public static String getWeight() {
        return weight;
    }

    public static void setWeight(String weight) {
        Profile.weight = weight;
    }

    public static String getGoalWeight() {
        return goalWeight;
    }

    public static void setGoalWeight(String goalWeight) {
        Profile.goalWeight = goalWeight;
    }

    public static String getHeightFeet() {
        return heightFeet;
    }

    public static void setHeightFeet(String heightFeet) {
        Profile.heightFeet = heightFeet;
    }

    public static String getHeightInches() {
        return heightInches;
    }

    public static void setHeightInches(String heightInches) {
        Profile.heightInches = heightInches;
    }
}
