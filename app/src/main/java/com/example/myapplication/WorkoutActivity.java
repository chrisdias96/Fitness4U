package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.Workouts.w1d1Fragment;

public class WorkoutActivity extends AppCompatActivity
    implements WorkoutFragment.OnFragmentInteractionListener,
                WorkoutChooserFragment.OnFragmentInteractionListener,
                GraphChooserFragment.OnFragmentInteractionListener,
                w1d1Fragment.OnFragmentInteractionListener {

    public static TTSManager ttsManager = new TTSManager();
    public static MediaPlayer mediaPlayer;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        fm = getSupportFragmentManager();

        ttsManager.init(this);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.whistle_blow_cc0);

        Intent intent = getIntent();
        int location = intent.getIntExtra("locationOfViewPager", 0);

        if (location == 0) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w1d1Fragment());
            transaction.commit();
        } else if (location == 1) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w1d2Fragment());
//            transaction.commit();
        } else if (location == 2) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w1d3Fragment());
//            transaction.commit();
        } else if (location == 3) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w2d1Fragment());
//            transaction.commit();
        } else if (location == 4) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w2d2Fragment());
//            transaction.commit();
        } else if (location == 5) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w2d3Fragment());
//            transaction.commit();
        } else if (location == 6) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w3d1Fragment());
//            transaction.commit();
        } else if (location == 7) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w3d2Fragment());
//            transaction.commit();
        } else if (location == 8) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w3d3Fragment());
//            transaction.commit();
        } else if (location == 9) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w4d1Fragment());
//            transaction.commit();
        } else if (location == 10) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w4d2Fragment());
//            transaction.commit();
        } else if (location == 11) {
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.content, new w4d3Fragment());
//            transaction.commit();
        }

    }

    public void soundWhistle() {
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ttsManager.shutdown();
        mediaPlayer.release();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
