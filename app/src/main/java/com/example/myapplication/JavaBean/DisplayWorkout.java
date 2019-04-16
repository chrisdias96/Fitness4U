package com.example.myapplication.JavaBean;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;

public class DisplayWorkout implements Parcelable {

    private ImageView exerciseImage;
    private String exerciseName;
    private String timerText;

    private ImageView backButton;
    private ImageView pauseButton;

    public DisplayWorkout() {
    }

    public DisplayWorkout(ImageView exerciseImage, String exerciseName, String timerText, ImageView backButton, ImageView pauseButton) {
        this.exerciseImage = exerciseImage;
        this.exerciseName = exerciseName;
        this.timerText = timerText;
        this.backButton = backButton;
        this.pauseButton = pauseButton;
    }

    public ImageView getExerciseImage() {
        return exerciseImage;
    }

    public void setExerciseImage(ImageView exerciseImage) {
        this.exerciseImage = exerciseImage;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getTimerText() {
        return timerText;
    }

    public void setTimerText(String timerText) {
        this.timerText = timerText;
    }

    public ImageView getBackButton() {
        return backButton;
    }

    public void setBackButton(ImageView backButton) {
        this.backButton = backButton;
    }

    public ImageView getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(ImageView pauseButton) {
        this.pauseButton = pauseButton;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.exerciseName);
        dest.writeString(this.timerText);
    }

    protected DisplayWorkout(Parcel in) {
        exerciseName = in.readString();
        timerText = in.readString();
    }

    public static final Creator<DisplayWorkout> CREATOR = new Creator<DisplayWorkout>() {
        @Override
        public DisplayWorkout createFromParcel(Parcel in) {
            return new DisplayWorkout(in);
        }

        @Override
        public DisplayWorkout[] newArray(int size) {
            return new DisplayWorkout[size];
        }
    };


}
