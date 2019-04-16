package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.Fragments.HistoryFragment;
import com.example.myapplication.Fragments.MainFragment;
import com.example.myapplication.Fragments.getInspiredFragment;
import com.example.myapplication.R;
import com.example.myapplication.Fragments.UpdateWorkoutFragment;
import com.example.myapplication.Fragments.WelcomeScreenFragment;
import com.example.myapplication.Fragments.WorkoutFragment;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener,
        WelcomeScreenFragment.OnFragmentInteractionListener,
        WorkoutFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        UpdateWorkoutFragment.OnFragmentInteractionListener,
        getInspiredFragment.OnFragmentInteractionListener {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_welcome);

        fm = getSupportFragmentManager();

        if(savedInstanceState == null){
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new WelcomeScreenFragment());
            transaction.commit();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
