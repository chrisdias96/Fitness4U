package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.Fragments.GraphChooserFragment;
import com.example.myapplication.R;
import com.example.myapplication.Helpers.TTSManager;
import com.example.myapplication.Fragments.WorkoutChooserFragment;
import com.example.myapplication.Fragments.WorkoutFragment;
import com.example.myapplication.Workouts.w1d1Fragment;
import com.example.myapplication.Workouts.w1d2Fragment;
import com.example.myapplication.Workouts.w1d3Fragment;
import com.example.myapplication.Workouts.w2d1Fragment;
import com.example.myapplication.Workouts.w2d2Fragment;
import com.example.myapplication.Workouts.w2d3Fragment;
import com.example.myapplication.Workouts.w3d1Fragment;
import com.example.myapplication.Workouts.w3d2Fragment;
import com.example.myapplication.Workouts.w3d3Fragment;
import com.example.myapplication.Workouts.w4d1Fragment;
import com.example.myapplication.Workouts.w4d2Fragment;
import com.example.myapplication.Workouts.w4d3Fragment;

public class WorkoutActivity extends AppCompatActivity
    implements WorkoutFragment.OnFragmentInteractionListener,
        WorkoutChooserFragment.OnFragmentInteractionListener,
        GraphChooserFragment.OnFragmentInteractionListener,
                w1d1Fragment.OnFragmentInteractionListener,
                w1d2Fragment.OnFragmentInteractionListener,
                w1d3Fragment.OnFragmentInteractionListener,
                w2d1Fragment.OnFragmentInteractionListener,
                w2d2Fragment.OnFragmentInteractionListener,
                w2d3Fragment.OnFragmentInteractionListener,
                w3d1Fragment.OnFragmentInteractionListener,
                w3d2Fragment.OnFragmentInteractionListener,
                w3d3Fragment.OnFragmentInteractionListener,
                w4d1Fragment.OnFragmentInteractionListener,
                w4d2Fragment.OnFragmentInteractionListener,
                w4d3Fragment.OnFragmentInteractionListener {

    public static TTSManager ttsManager;
    public static MediaPlayer mediaPlayer;

    SharedPreferences sf;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        //Grab the FragmentManager
        fm = getSupportFragmentManager();

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        sf = PreferenceManager.getDefaultSharedPreferences(this);

        //Initalize the ttsManager and MediaPlayer if it's enabled in the settings
        if (sf.getBoolean("ttsKey", true)) {
            ttsManager = new TTSManager();
            ttsManager.init(this);
        }

        if (sf.getBoolean("soundKey", true)) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.whistle_blow_cc0);
        }

        //Grab the WorkoutFragment intent that loads the location of the viewPager
        //This tells us which workout the user is choosing to perform
        Intent intent = getIntent();
        int location = intent.getIntExtra("locationOfViewPager", 0);

        if (location == 0) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w1d1Fragment());
            transaction.commit();
        } else if (location == 1) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w1d2Fragment());
            transaction.commit();
        } else if (location == 2) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w1d3Fragment());
            transaction.commit();
        } else if (location == 3) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w2d1Fragment());
            transaction.commit();
        } else if (location == 4) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w2d2Fragment());
            transaction.commit();
        } else if (location == 5) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w2d3Fragment());
            transaction.commit();
        } else if (location == 6) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w3d1Fragment());
            transaction.commit();
        } else if (location == 7) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w3d2Fragment());
            transaction.commit();
        } else if (location == 8) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w3d3Fragment());
            transaction.commit();
        } else if (location == 9) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w4d1Fragment());
            transaction.commit();
        } else if (location == 10) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w4d2Fragment());
            transaction.commit();
        } else if (location == 11) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new w4d3Fragment());
            transaction.commit();
        }

    }

    /**
     * soundWhistle method plays the whistle sound
     */
    public void soundWhistle() {
        if (sf.getBoolean("soundKey", true)) {
            mediaPlayer.start();
        }
    }

    /**
     * onDestroy is called when the Workout is complete and the Activity is gone
     * Close the MediaPlayer and ttsManager
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (sf.getBoolean("ttsKey", true))
            ttsManager.shutdown();
        if (sf.getBoolean("soundKey", true))
            mediaPlayer.release();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
