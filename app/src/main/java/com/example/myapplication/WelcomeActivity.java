package com.example.myapplication;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener,
        WelcomeScreenFragment.OnFragmentInteractionListener,
        WorkoutFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        TipFragment.OnFragmentInteractionListener,
        SettingFragment.OnFragmentInteractionListener  {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        fm = getSupportFragmentManager();

        if(savedInstanceState == null){
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new WelcomeScreenFragment());
            transaction.commit();
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
