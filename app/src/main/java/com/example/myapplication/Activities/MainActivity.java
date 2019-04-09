package com.example.myapplication.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.Fragments.GraphChooserFragment;
import com.example.myapplication.Fragments.HistoryFragment;
import com.example.myapplication.Fragments.MainFragment;
import com.example.myapplication.R;
import com.example.myapplication.Fragments.TipFragment;
import com.example.myapplication.Fragments.UpdateWorkoutFragment;
import com.example.myapplication.Fragments.WelcomeScreenFragment;
import com.example.myapplication.Fragments.WorkoutChooserFragment;
import com.example.myapplication.Fragments.WorkoutFragment;
import com.example.myapplication.Workouts.w1d1Fragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener,
        WelcomeScreenFragment.OnFragmentInteractionListener,
        WorkoutFragment.OnFragmentInteractionListener,
        WorkoutChooserFragment.OnFragmentInteractionListener,
        GraphChooserFragment.OnFragmentInteractionListener,
                    w1d1Fragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        UpdateWorkoutFragment.OnFragmentInteractionListener,
        TipFragment.OnFragmentInteractionListener {

    public static FloatingActionButton fab;
    FragmentManager fm;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm = getSupportFragmentManager();

        if(savedInstanceState == null){
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content, new WorkoutFragment());
            transaction.commit();
        }


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_workouts) {
            fm.beginTransaction()
            .replace(R.id.content, new WorkoutFragment())
            .commit();
        } else if (id == R.id.navigation_history) {
            fm.beginTransaction()
            .replace(R.id.content, new HistoryFragment())
            .commit();
        } else if (id == R.id.navigation_tips) {
            fm.beginTransaction()
            .replace(R.id.content, new TipFragment())
            .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
