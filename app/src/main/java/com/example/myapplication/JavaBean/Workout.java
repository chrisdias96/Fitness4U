package com.example.myapplication.JavaBean;

import android.os.Parcel;
import android.os.Parcelable;

public class Workout implements Parcelable {

    private int id;

    private String jumpingJack;
    private String plank;
    private String lunge;
    private String pushup;
    private String situp;
    private String highKnee;
    private String sidePlank;
    private String tricepDip;
    private String squat;

    private String date;

    //Constructor
    public Workout(String jumpingJack, String plank, String lunge, String pushup, String situp, String highKnee, String sidePlank, String tricepDip, String squat, String date) {
        this.jumpingJack = jumpingJack;
        this.plank = plank;
        this.lunge = lunge;
        this.pushup = pushup;
        this.situp = situp;
        this.highKnee = highKnee;
        this.sidePlank = sidePlank;
        this.tricepDip = tricepDip;
        this.squat = squat;
        this.date = date;
    }

    public Workout(int id, String jumpingJack, String plank, String lunge, String pushup, String situp, String highKnee, String sidePlank, String tricepDip, String squat, String date) {
        this.id = id;
        this.jumpingJack = jumpingJack;
        this.plank = plank;
        this.lunge = lunge;
        this.pushup = pushup;
        this.situp = situp;
        this.highKnee = highKnee;
        this.sidePlank = sidePlank;
        this.tricepDip = tricepDip;
        this.squat = squat;
        this.date = date;
    }

    //Getter and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJumpingJack() {
        return jumpingJack;
    }

    public void setJumpingJack(String jumpingJack) {
        this.jumpingJack = jumpingJack;
    }

    public String getPlank() {
        return plank;
    }

    public void setPlank(String plank) {
        this.plank = plank;
    }

    public String getLunge() {
        return lunge;
    }

    public void setLunge(String lunge) {
        this.lunge = lunge;
    }

    public String getPushup() {
        return pushup;
    }

    public void setPushup(String pushup) {
        this.pushup = pushup;
    }

    public String getSitup() {
        return situp;
    }

    public void setSitup(String situp) {
        this.situp = situp;
    }

    public String getHighKnee() {
        return highKnee;
    }

    public void setHighKnee(String highKnee) {
        this.highKnee = highKnee;
    }

    public String getSidePlank() {
        return sidePlank;
    }

    public void setSidePlank(String sidePlank) {
        this.sidePlank = sidePlank;
    }

    public String getTricepDip() {
        return tricepDip;
    }

    public void setTricepDip(String tricepDip) {
        this.tricepDip = tricepDip;
    }

    public String getSquat() {
        return squat;
    }

    public void setSquat(String squat) {
        this.squat = squat;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);

        dest.writeString(this.jumpingJack);
        dest.writeString(this.plank);
        dest.writeString(this.lunge);
        dest.writeString(this.pushup);
        dest.writeString(this.situp);
        dest.writeString(this.highKnee);
        dest.writeString(this.sidePlank);
        dest.writeString(this.tricepDip);
        dest.writeString(this.squat);

        dest.writeString(this.date);
    }

    protected Workout(Parcel in) {
        id = in.readInt();

        this.jumpingJack = in.readString();
        this.plank = in.readString();
        this.lunge = in.readString();
        this.pushup = in.readString();
        this.situp = in.readString();
        this.highKnee = in.readString();
        this.sidePlank = in.readString();
        this.tricepDip = in.readString();
        this.squat = in.readString();

        this.date = in.readString();
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };
}
