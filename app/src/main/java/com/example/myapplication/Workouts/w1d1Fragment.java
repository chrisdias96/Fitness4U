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

import com.example.myapplication.Database.DatabaseHandler;
import com.example.myapplication.JavaBean.DisplayWorkout;
import com.example.myapplication.JavaBean.Workout;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.WorkoutActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link w1d1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link w1d1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class w1d1Fragment extends Fragment {
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

    public w1d1Fragment() {
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
    public static w1d1Fragment newInstance(Parcelable param1) {
        w1d1Fragment fragment = new w1d1Fragment();
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

        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_exercises, false);
        sf = PreferenceManager.getDefaultSharedPreferences(getContext());

        fm = getActivity().getSupportFragmentManager();

        exerciseImage = view.findViewById(R.id.exerciseImage);
        exerciseTitle = view.findViewById(R.id.exerciseTitle);
        timerCount = view.findViewById(R.id.timerText);
        nextWorkoutButton = view.findViewById(R.id.nextExerciseButton);

        exitButton = view.findViewById(R.id.backWorkoutButton);

        WorkoutActivity.mediaPlayer.start();
        exerciseImage.setImageResource(R.drawable.highknees);
        exerciseTitle.setText(R.string.warmup_exercise_title);

        stretchTimer = new CountDownTimer(180000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerCount.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if (!isDoneStretching) {
                    nextSegmentOfWorkout();
                    WorkoutActivity.mediaPlayer.start();
                    isDoneStretching = true;
                }
            }
        }.start();

        plankTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerCount.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                WorkoutActivity.mediaPlayer.start();
                nextSegmentOfWorkout();
            }
        };

        nextWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stretchTimer.cancel();
                plankTimer.cancel();

                nextSegmentOfWorkout();

            }
        });

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

    public void jumpingJacks() {

        if (sf.getBoolean("ex1", false)) {
            exerciseImage.setImageResource(R.drawable.jumpingjack);
            exerciseTitle.setText(R.string.exercise_title_jumpingjacks);
            timerCount.setText("15");
            String text = "Do 15 jumping jacks";
            WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneJumpingJacks = true;
        jumpingJacksCount = "15";
    }

    public void planks() {

        if (sf.getBoolean("ex2", false)) {
            exerciseImage.setImageResource(R.drawable.plank);
            exerciseTitle.setText("Plank");
            String text = "Plank for 60 seconds";
            WorkoutActivity.ttsManager.initQueue(text);

            plankTimer.start();
        }

        isDonePlank = true;
        plankCount = "15";
    }

    public void lunges() {

        if(sf.getBoolean("ex3", false)) {
            exerciseImage.setImageResource(R.drawable.lunges);
            exerciseTitle.setText(R.string.exercise_title_lunges);
            timerCount.setText("15");
            String text = "Do 15 lunges for each leg";
            WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneLunges = true;
        lungesCount = "15";
    }

    public void pushups() {

        if(sf.getBoolean("ex4", false)) {
            exerciseImage.setImageResource(R.drawable.pushups);
            exerciseTitle.setText(R.string.exercise_title_pushups);
            timerCount.setText("15");
            String text = "Do 15 pushups";
            WorkoutActivity.ttsManager.initQueue(text);
        }

        isDonePushups = true;
        pushupsCount = "15";
    }

    public void situps() {

        if(sf.getBoolean("ex5", false)) {
            exerciseImage.setImageResource(R.drawable.situps);
            exerciseTitle.setText("Situps");
            timerCount.setText("15");
            String text = "Do 15 situps";
            WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneSitups = true;
        situpsCount = "15";
    }

    public void highKnees() {

        if(sf.getBoolean("ex6", false)) {
            exerciseImage.setImageResource(R.drawable.highknees);
            exerciseTitle.setText("High Knees");
            timerCount.setText("15");
            String text = "Do 15 high knees for each leg";
            WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneHighKnees = true;
        highKneesCount = "15";
    }

    public void sideplank() {

        if(sf.getBoolean("ex7", false)) {
            exerciseImage.setImageResource(R.drawable.sideplank);
            exerciseTitle.setText("Side Plank");
            timerCount.setText("15");
            String text = "Do 15 side planks for each side";
            WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneSidePlank = true;
        sidePlankCount = "15";
    }

    public void tricepDip() {

        if(sf.getBoolean("ex8", false)) {
            exerciseImage.setImageResource(R.drawable.tripcepdips);
            exerciseTitle.setText("Tricep Dips");
            timerCount.setText("15");
            String text = "Do 15 tricep dips";
            WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneTricepDips = true;
        tricepDipCount = "15";
    }

    public void squats() {

        if(sf.getBoolean("ex9", false)) {
            exerciseImage.setImageResource(R.drawable.squats);
            exerciseTitle.setText("Squats");
            timerCount.setText("15");
            String text = "Do 15 squats";
            WorkoutActivity.ttsManager.initQueue(text);
        }

        isDoneSquats = true;
        squatCount = "15";
    }

    public void nextSegmentOfWorkout() {
        if (sf.getBoolean("ex1", false) && !isDoneJumpingJacks) {
            jumpingJacks();
            WorkoutActivity.mediaPlayer.start();
        } else if (sf.getBoolean("ex2", false) && !isDonePlank) {
            planks();
            WorkoutActivity.mediaPlayer.start();
        } else if (sf.getBoolean("ex3", false) && !isDoneLunges) {
            lunges();
            WorkoutActivity.mediaPlayer.start();
        } else if (sf.getBoolean("ex4", false) && !isDonePushups) {
            pushups();
            WorkoutActivity.mediaPlayer.start();
        } else if (sf.getBoolean("ex5", false) && !isDoneSitups) {
            situps();
            WorkoutActivity.mediaPlayer.start();
        } else if (sf.getBoolean("ex6", false) && !isDoneHighKnees) {
            highKnees();
            WorkoutActivity.mediaPlayer.start();
        } else if (sf.getBoolean("ex7", false) && !isDoneSidePlank) {
            sideplank();
            WorkoutActivity.mediaPlayer.start();
        } else if (sf.getBoolean("ex8", false) && !isDoneTricepDips) {
            tricepDip();
            WorkoutActivity.mediaPlayer.start();
        } else if (sf.getBoolean("ex9", false) && !isDoneSquats) {
            squats();
            WorkoutActivity.mediaPlayer.start();
        } else {
            if (isDoneWorkout) {

                String text = "Done Workout! great job!";
                WorkoutActivity.ttsManager.initQueue(text);

                SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
                String date = sdf.format(new Date());

                //Add the workout details into the database
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

                WorkoutActivity.mediaPlayer.start();
                exerciseImage.setImageResource(R.drawable.highknees);
                exerciseTitle.setText(R.string.warmup_exercise_title);
                stretchTimer.start();
                String text = "Cool down for 3 minutes";
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

        stretchTimer.cancel();
        plankTimer.cancel();

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
