package com.example.myapplication.Workouts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Activities.WorkoutActivity;
import com.example.myapplication.Database.DatabaseHandler;
import com.example.myapplication.JavaBean.DisplayWorkout;
import com.example.myapplication.JavaBean.Workout;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link w4d3Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link w4d3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class w4d3Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private DisplayWorkout mParam1;

    SharedPreferences sf;
    FragmentManager fm;

    private ImageView exerciseImage;
    private TextView exerciseTitle;
    private TextView timerCount;
    private Button nextWorkoutButton;

    private ImageView exitButton;

    private boolean isDoneStretching = false;
    private boolean isDoneWorkout = false;

    private boolean isDoneJumpingJacks = false;
    private boolean isDonePlank = false;
    private boolean isDoneLunges = false;
    private boolean isDonePushups = false;
    private boolean isDoneSitups = false;
    private boolean isDoneHighKnees = false;
    private boolean isDoneSidePlank = false;
    private boolean isDoneTricepDips = false;
    private boolean isDoneSquats = false;

    private String jumpingJacksCount = "";
    private String plankCount = "";
    private String lungesCount = "";
    private String pushupsCount = "";
    private String situpsCount = "";
    private String highKneesCount = "";
    private String sidePlankCount = "";
    private String tricepDipCount = "";
    private String squatCount = "";

    private OnFragmentInteractionListener mListener;

    private CountDownTimer stretchTimer;
    private CountDownTimer plankTimer;

    public w4d3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1
     * @return A new instance of fragment w1d1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static w4d3Fragment newInstance(Parcelable param1) {
        w4d3Fragment fragment = new w4d3Fragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_workout, container, false);

        //Call the exercise preferences and fins out which exercises the user wants to perform
        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_exercises, false);
        sf = PreferenceManager.getDefaultSharedPreferences(getContext());

        fm = getActivity().getSupportFragmentManager();

        exerciseImage = view.findViewById(R.id.exerciseImage);
        exerciseTitle = view.findViewById(R.id.exerciseTitle);
        timerCount = view.findViewById(R.id.timerText);
        nextWorkoutButton = view.findViewById(R.id.nextExerciseButton);

        exitButton = view.findViewById(R.id.backWorkoutButton);

        //If the user has the checkbox to listen to sounds, then play the whistle to begin the workout
        if (sf.getBoolean("soundKey", true)) {
            WorkoutActivity.mediaPlayer.start();
        }
        exerciseImage.setImageResource(R.drawable.highknees);
        exerciseTitle.setText(R.string.warmup_exercise_title);

        //StretchTimer is called whenever the workout is starting and ending
        //Stretches/Cooldowns are 3 minutes long
        stretchTimer = new CountDownTimer(180000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //Every second display the timer ticking down
                timerCount.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                //If the user isn't done stretching, skip this and go back to the onTick
                if (!isDoneStretching) {
                    //Stretch is complete, proceed with the workout
                    nextSegmentOfWorkout();
                    WorkoutActivity.mediaPlayer.start();
                    isDoneStretching = true;
                }
            }
        }.start();

        //PlankTimer is a countdown for when the user is planking
        plankTimer = new CountDownTimer(180000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //Decrease every second
                timerCount.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                //Continue the workout when finished
                WorkoutActivity.mediaPlayer.start();
                nextSegmentOfWorkout();
            }
        };

        //When the 'DONE' workout button is clicked,
        //Cancel any timers and continue the workout
        nextWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stretchTimer.cancel();
                plankTimer.cancel();

                nextSegmentOfWorkout();

            }
        });

        //When the exit workout button is clicked
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an AlertDialog that will pop when the user quits the exit Button
                //This is to make sure the user really wants to end the workout early
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

                alertDialog.setTitle("Quit Workout?");
                alertDialog.setMessage("Are you sure you want to quit the workout?");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //The user choose yes to quit,
                        // send them back to the MainActivity and WorkoutFragment
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        alertDialog.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No, continue on!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //The user chose no,
                        // continue the workout
                        alertDialog.dismiss();

                    }
                });
                alertDialog.show();

            }
        });

        return view;
    }

    /**
     * This is called when the user is to perform jumping jacks
     */
    public void jumpingJacks() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if (sf.getBoolean("ex1", false)) {
            exerciseImage.setImageResource(R.drawable.jumpingjack);
            exerciseTitle.setText(R.string.exercise_title_jumpingjacks);
            timerCount.setText(R.string.reps_w4d3);
            String text = getResources().getString(R.string.voice_coach_jumping_jacks_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneJumpingJacks = true;
        jumpingJacksCount = getResources().getString(R.string.reps_w4d3);
    }

    public void planks() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if (sf.getBoolean("ex2", false)) {
            exerciseImage.setImageResource(R.drawable.plank);
            exerciseTitle.setText(R.string.exercise_title_plank);
            String text = getResources().getString(R.string.voice_coach_plank_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);

            plankTimer.start();
        }

        isDonePlank = true;
        plankCount = getResources().getString(R.string.plank_count_w4d3);
    }

    public void lunges() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if(sf.getBoolean("ex3", false)) {
            exerciseImage.setImageResource(R.drawable.lunges);
            exerciseTitle.setText(R.string.exercise_title_lunges);
            timerCount.setText(R.string.reps_w4d3);
            String text = getResources().getString(R.string.voice_coach_lunge_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneLunges = true;
        lungesCount = getResources().getString(R.string.reps_w4d3);
    }

    public void pushups() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if(sf.getBoolean("ex4", false)) {
            exerciseImage.setImageResource(R.drawable.pushups);
            exerciseTitle.setText(R.string.exercise_title_pushups);
            timerCount.setText(R.string.reps_w4d3);
            String text = getResources().getString(R.string.voice_coach_pushup_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);
        }

        isDonePushups = true;
        pushupsCount = getResources().getString(R.string.reps_w4d3);
    }

    public void situps() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if(sf.getBoolean("ex5", false)) {
            exerciseImage.setImageResource(R.drawable.situps);
            exerciseTitle.setText(R.string.exercise_title_situps);
            timerCount.setText(R.string.reps_w4d3);
            String text = getResources().getString(R.string.voice_coach_situp_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneSitups = true;
        situpsCount = getResources().getString(R.string.reps_w4d3);
    }

    public void highKnees() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if(sf.getBoolean("ex6", false)) {
            exerciseImage.setImageResource(R.drawable.highknees);
            exerciseTitle.setText(R.string.exercise_title_high_knees);
            timerCount.setText(R.string.reps_w4d3);
            String text = getResources().getString(R.string.voice_coach_high_knees_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneHighKnees = true;
        highKneesCount = getResources().getString(R.string.reps_w4d3);
    }

    public void sideplank() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if(sf.getBoolean("ex7", false)) {
            exerciseImage.setImageResource(R.drawable.sideplank);
            exerciseTitle.setText(R.string.exercise_title_side_plank);
            timerCount.setText(R.string.reps_w4d3);
            String text = getResources().getString(R.string.voice_coach_side_plank_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneSidePlank = true;
        sidePlankCount = getResources().getString(R.string.reps_w4d3);
    }

    public void tricepDip() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if(sf.getBoolean("ex8", false)) {
            exerciseImage.setImageResource(R.drawable.tripcepdips);
            exerciseTitle.setText(R.string.exercise_title_tricepdip);
            timerCount.setText(R.string.reps_w4d3);
            String text = getResources().getString(R.string.voice_coach_tricep_dip_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneTricepDips = true;
        tricepDipCount = getResources().getString(R.string.reps_w4d3);
    }

    public void squats() {

        //If the sharedPreferences is set to true, show the workout details on screen
        if(sf.getBoolean("ex9", false)) {
            exerciseImage.setImageResource(R.drawable.squats);
            exerciseTitle.setText(R.string.exercise_title_squats);
            timerCount.setText(R.string.reps_w4d3);
            String text = getResources().getString(R.string.voice_coach_squats_w4d3);
            if (sf.getBoolean("ttsKey", true))
                WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneSquats = true;
        squatCount = getResources().getString(R.string.reps_w4d3);
    }

    /**
     * nextSegmentOfWorkout checks to see if the workout has been performed
     * If it has not, perform the next workout and call their specific exercise method
     * If it has, continue until all workouts have been completed
     */
    public void nextSegmentOfWorkout() {
        if (sf.getBoolean("ex1", false) && !isDoneJumpingJacks) {
            jumpingJacks();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else if (sf.getBoolean("ex2", false) && !isDonePlank) {
            planks();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else if (sf.getBoolean("ex3", false) && !isDoneLunges) {
            lunges();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else if (sf.getBoolean("ex4", false) && !isDonePushups) {
            pushups();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else if (sf.getBoolean("ex5", false) && !isDoneSitups) {
            situps();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else if (sf.getBoolean("ex6", false) && !isDoneHighKnees) {
            highKnees();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else if (sf.getBoolean("ex7", false) && !isDoneSidePlank) {
            sideplank();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else if (sf.getBoolean("ex8", false) && !isDoneTricepDips) {
            tricepDip();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else if (sf.getBoolean("ex9", false) && !isDoneSquats) {
            squats();
            if (sf.getBoolean("soundKey", true)) {
                WorkoutActivity.mediaPlayer.start();
            }
        } else {
            if (isDoneWorkout) {
                //Once all the workouts are completed

                if (sf.getBoolean("ttsKey", true)) {
                    String text = "Done Workout!";
                    WorkoutActivity.ttsManager.initQueue(text);
                }

                //Create a date String thats populated with the date the user completed the workout
                SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
                String date = sdf.format(new Date());

                //Populate the workout details into the database
                Workout workout = new Workout(jumpingJacksCount,
                        plankCount,
                        lungesCount,
                        pushupsCount,
                        situpsCount,
                        highKneesCount,
                        sidePlankCount,
                        tricepDipCount,
                        squatCount,
                        date);

                //Get access to the db
                DatabaseHandler db = new DatabaseHandler(getContext());
                db.addWorkout(workout);
                db.close();

                //leave the workout Fragment
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            } else {
                //User has completed the workout but not finished the cooldown

                if (sf.getBoolean("soundKey", true)) {
                    WorkoutActivity.mediaPlayer.start();
                }
                exerciseImage.setImageResource(R.drawable.highknees);
                exerciseTitle.setText(R.string.warmup_exercise_title);
                stretchTimer.start();
                String text = "Cool down for 3 minutes";
                if (sf.getBoolean("ttsKey", true))
                    WorkoutActivity.ttsManager.initQueue(text);

                isDoneWorkout = true;
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Cancel all timers and close the ttsManager when the fragment is destroyed
        stretchTimer.cancel();
        plankTimer.cancel();

        if (sf.getBoolean("ttsKey", true))
            WorkoutActivity.ttsManager.shutdown();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
