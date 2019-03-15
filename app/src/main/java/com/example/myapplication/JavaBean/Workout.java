package com.example.myapplication.JavaBean;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.data.PieEntry;

public class Workout implements Parcelable {

    private int id;

    private int jumpingJack;
    private int plank;
    private int burpee;
    private int lunge;
    private int pushup;
    private int situp;
    private int highKnee;
    private int wallSit;
    private int sidePlank;
    private int tricepDip;
    private int legLift;
    private int russianTwist;
    private int squat;

    private int complete;
    private String date;

    //Constructor
    public Workout(int i, int parseInt, int anInt, int i1, int parseInt1, int anInt1, int i2, int parseInt2, int anInt2, int i3, int parseInt3, int anInt3, int i4, int parseInt4, int anInt4, String string) {

    }

    //Getter and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJumpingJack() {
        return jumpingJack;
    }

    public void setJumpingJack(int jumpingJack) {
        this.jumpingJack = jumpingJack;
    }

    public int getPlank() {
        return plank;
    }

    public void setPlank(int plank) {
        this.plank = plank;
    }

    public int getBurpee() {
        return burpee;
    }

    public void setBurpee(int burpee) {
        this.burpee = burpee;
    }

    public int getLunge() {
        return lunge;
    }

    public void setLunge(int lunge) {
        this.lunge = lunge;
    }

    public int getPushup() {
        return pushup;
    }

    public void setPushup(int pushup) {
        this.pushup = pushup;
    }

    public int getSitup() {
        return situp;
    }

    public void setSitup(int situp) {
        this.situp = situp;
    }

    public int getHighKnee() {
        return highKnee;
    }

    public void setHighKnee(int highKnee) {
        this.highKnee = highKnee;
    }

    public int getWallSit() {
        return wallSit;
    }

    public void setWallSit(int wallSit) {
        this.wallSit = wallSit;
    }

    public int getSidePlank() {
        return sidePlank;
    }

    public void setSidePlank(int sidePlank) {
        this.sidePlank = sidePlank;
    }

    public int getTricepDip() {
        return tricepDip;
    }

    public void setTricepDip(int tricepDip) {
        this.tricepDip = tricepDip;
    }

    public int getLegLift() {
        return legLift;
    }

    public void setLegLift(int legLift) {
        this.legLift = legLift;
    }

    public int getRussianTwist() {
        return russianTwist;
    }

    public void setRussianTwist(int russianTwist) {
        this.russianTwist = russianTwist;
    }

    public int getSquat() {
        return squat;
    }

    public void setSquat(int squat) {
        this.squat = squat;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.jumpingJack);
        dest.writeInt(this.plank);
        dest.writeInt(this.burpee);
        dest.writeInt(this.lunge);
        dest.writeInt(this.pushup);
        dest.writeInt(this.situp);
        dest.writeInt(this.highKnee);
        dest.writeInt(this.wallSit);
        dest.writeInt(this.sidePlank);
        dest.writeInt(this.tricepDip);
        dest.writeInt(this.legLift);
        dest.writeInt(this.russianTwist);
        dest.writeInt(this.squat);

        dest.writeInt(this.complete);
        dest.writeString(this.date);
    }

    protected Workout(Parcel in) {
        id = in.readInt();
        this.jumpingJack = in.readInt();
        this.plank = in.readInt();
        this.burpee = in.readInt();
        this.lunge = in.readInt();
        this.pushup = in.readInt();
        this.situp = in.readInt();
        this.highKnee = in.readInt();
        this.wallSit = in.readInt();
        this.sidePlank = in.readInt();
        this.tricepDip = in.readInt();
        this.legLift = in.readInt();
        this.russianTwist = in.readInt();
        this.squat = in.readInt();

        this.complete = in.readInt();
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
