package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Database.DatabaseHandler;
import com.example.myapplication.JavaBean.Workout;

import java.util.ArrayList;

public class CustomWorkoutAdapter extends RecyclerView.Adapter<CustomWorkoutAdapter.CustomViewHolder>{
    private ArrayList<Workout> workouts;
    private Context context;
    //RequestQueue requestQueue;

    private static final int SEND_SMS_LABEL = 1;

    public CustomWorkoutAdapter(@NonNull ArrayList<Workout> workouts, Context context){
        this.workouts = workouts;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomWorkoutAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.workout_view, viewGroup, false);
        final CustomViewHolder customViewHolder = new CustomViewHolder(view);
        customViewHolder.galleryLayout.setVisibility(View.GONE);

        /**
         * Make the entire CardView Clickable
         */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewHolder.galleryLayout.setVisibility(
                        (customViewHolder.galleryLayout.getVisibility() == View.VISIBLE)
                                ? View.GONE : View.VISIBLE);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Grab the current location in the list
                                int location = customViewHolder.getAdapterPosition();
                                //Open the database
                                DatabaseHandler db = new DatabaseHandler(context);
                                /**
                                 * look in the workouts list and grab the item at 'location'
                                 * Grab that items id
                                 * remove from the database the location at that items id
                                 */
                                db.deleteWorkout(workouts.get(location).getId());
                                //We also remove the location from the ArrayList
                                workouts.remove(location);
                                //Refresh the ArrayList
                                notifyItemRemoved(location);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });



        //Make the location pindrop clickable
        customViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Grab the location
                int location = customViewHolder.getAdapterPosition();

                //Share intent goes here
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    //Ask the user for permission
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.SEND_SMS)) {
                        //Show an explanation to the user why we need it
                        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Message Permission");
                        alertDialog.setMessage("We need access to send an SMS to your contacts about your successful workouts!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                //Request for the permission again
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_LABEL);
                            }
                        });
                        alertDialog.show();
                    } else {
                        //Request for permission
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_LABEL);
                    }
                } else {
                    //Permission was granted
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.shareIntentSubject);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, R.string.shareIntentText);
                    if (shareIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(Intent.createChooser(shareIntent, "Share using"));
                    }
                }

            }
        });

        customViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Grab the location
                MainActivity mainActivity = (MainActivity) context;
                int location = customViewHolder.getAdapterPosition();

                //Send the location to the UpdateWorkoutFragment
                FragmentManager fm = mainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content,
                        UpdateWorkoutFragment.newInstance(workouts.get(location)));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewHolder, int i) {
        Workout workout = workouts.get(i);
        viewHolder.name.setText("Workout " + workout.getId());
        viewHolder.date.setText(workout.getDate());

        viewHolder.jumpingJacks.setText(String.format("Jumping Jacks: %s", workout.getJumpingJack()));
        viewHolder.plank.setText(String.format("Plank: %s seconds", workout.getPlank()));
        viewHolder.lunge.setText(String.format("Lunges: %s", workout.getLunge()));
        viewHolder.pushup.setText(String.format("Pushups: %s", workout.getPushup()));
        viewHolder.situp.setText(String.format("Situps: %s", workout.getSitup()));
        viewHolder.highKnee.setText(String.format("High Knees: %s", workout.getHighKnee()));
        viewHolder.sidePlank.setText(String.format("Side Planks: %s", workout.getSidePlank()));
        viewHolder.tricepDip.setText(String.format("Tricep Dips: %s", workout.getTricepDip()));
        viewHolder.squat.setText(String.format("Squats: %s", workout.getSquat()));

        viewHolder.date.setText(workout.getDate());

    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView date;

        protected TextView jumpingJacks;
        protected TextView plank;
        protected TextView lunge;
        protected TextView pushup;
        protected TextView situp;
        protected TextView highKnee;
        protected TextView sidePlank;
        protected TextView tricepDip;
        protected TextView squat;

        protected ImageView share;
        protected ImageView edit;

        protected LinearLayout galleryLayout;

        public CustomViewHolder(View view){
            super(view);
            this.name  = view.findViewById(R.id.workoutViewTitle);
            this.date = view.findViewById(R.id.workoutViewDate);

            this.jumpingJacks = view.findViewById(R.id.workoutViewJumpingJack);
            this.plank = view.findViewById(R.id.workoutViewPlank);
            this.lunge = view.findViewById(R.id.workoutViewLunge);
            this.pushup = view.findViewById(R.id.workoutViewPushup);
            this.situp = view.findViewById(R.id.workoutViewSitup);
            this.highKnee = view.findViewById(R.id.workoutViewHighKnee);
            this.sidePlank = view.findViewById(R.id.workoutViewSidePlank);
            this.tricepDip = view.findViewById(R.id.workoutViewTricepDip);
            this.squat = view.findViewById(R.id.workoutViewSquats);

            this.share = view.findViewById(R.id.workoutViewShare);
            this.edit = view.findViewById(R.id.workoutViewEdit);

            this.galleryLayout = view.findViewById(R.id.galleryLayout);
        }
    }

}
