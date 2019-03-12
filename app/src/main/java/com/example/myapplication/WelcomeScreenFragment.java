package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.JavaBean.Profile;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WelcomeScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WelcomeScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeScreenFragment extends Fragment implements AdapterView.OnItemSelectedListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentManager fm;

    String gender;
    String height;

    SharedPreferences sf;
    SharedPreferences.Editor editor;


    private OnFragmentInteractionListener mListener;

    public WelcomeScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WelcomeScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WelcomeScreenFragment newInstance(String param1, String param2) {
        WelcomeScreenFragment fragment = new WelcomeScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sf = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sf.edit();

        boolean isProfileFormCompleted = sf.getBoolean("form_not_completed", false);

        if (isProfileFormCompleted) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_welcome_screen, container, false);

        sf = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sf.edit();

        fm = getActivity().getSupportFragmentManager();

        final EditText firstNameEditText = view.findViewById(R.id.firstNameEditText);
        firstNameEditText.setText(sf.getString("fName", ""));
        final EditText lastNameEditText = view.findViewById(R.id.lastNameEditText);
        lastNameEditText.setText(sf.getString("lName", ""));
        final RadioGroup radioGroup = view.findViewById(R.id.genderRadioGroup);

        final EditText ageEditText = view.findViewById(R.id.ageEditText);
        ageEditText.setText(sf.getString("age", ""));
        final EditText weightEditText = view.findViewById(R.id.weightEditText);
        weightEditText.setText(sf.getString("weight", ""));
        final EditText goalWeightEditText = view.findViewById(R.id.goalWeightEditText);
        goalWeightEditText.setText(sf.getString("goalWeight", ""));
        final Button submitButton = view.findViewById(R.id.submitProfileButton);
        final EditText heightFeetEditText = view.findViewById(R.id.heightFeet);
        heightFeetEditText.setText(sf.getString("heightFeet", ""));
        final EditText heightInchesEditText = view.findViewById(R.id.heightInches);
        heightInchesEditText.setText(sf.getString("heightInches", ""));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.maleRadioButton:
                       final RadioButton maleRadio = view.findViewById(R.id.maleRadioButton);
                        sf.edit().putBoolean("gender", maleRadio.isChecked()).apply();
                       gender = "Male";
                       radioGroup.setSelected(true);
                       break;
                    case R.id.femaleRadioButton:
                        final RadioButton femaleRadio = view.findViewById(R.id.femaleRadioButton);
                        sf.edit().putBoolean("gender", femaleRadio.isChecked()).apply();
                        gender = "Female";
                        radioGroup.setSelected(true);
                        break;
                    case R.id.otherRadioButton:
                        final RadioButton otherRadio = view.findViewById(R.id.otherRadioButton);
                        sf.edit().putBoolean("gender", otherRadio.isChecked()).apply();
                        gender = "Other";
                        radioGroup.setSelected(true);
                        break;
                }
            }
        });

        ageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        goalWeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (firstNameEditText.getText().toString().isEmpty()) {
                    final TextView firstNameText = view.findViewById(R.id.firstNameLabel);
                    firstNameText.setTextColor(Color.RED);
                }
                if(lastNameEditText.getText().toString().isEmpty()) {
                    final TextView lastNameText = view.findViewById(R.id.lastNameLabel);
                    lastNameText.setTextColor(Color.RED);
                }
                if (!radioGroup.isSelected()) {
                    final TextView genderText = view.findViewById(R.id.genderLabel);
                    genderText.setTextColor(Color.RED);
                }
                if (ageEditText.getText().toString().isEmpty()) {
                    final TextView ageText = view.findViewById(R.id.ageLabel);
                    ageText.setTextColor(Color.RED);
                }
                if (weightEditText.getText().toString().isEmpty()) {
                    final TextView weightText = view.findViewById(R.id.weightLabel);
                    weightText.setTextColor(Color.RED);
                }
                if (goalWeightEditText.getText().toString().isEmpty()) {
                    final TextView goalWeightText = view.findViewById(R.id.goalWeightLabel);
                    goalWeightText.setTextColor(Color.RED);
                }
                if (heightFeetEditText.getText().toString().isEmpty()) {
                    final TextView heightText = view.findViewById(R.id.heightFeetLabel);
                    heightText.setTextColor(Color.RED);
                }
                if (heightInchesEditText.getText().toString().isEmpty()) {
                    final TextView inchesText = view.findViewById(R.id.heightInchesLabel);
                    inchesText.setTextColor(Color.RED);
                }

                if(radioGroup.isSelected() &&
                        !firstNameEditText.getText().toString().isEmpty() &&
                        !lastNameEditText.getText().toString().isEmpty() &&
                        !ageEditText.getText().toString().isEmpty() &&
                        !weightEditText.getText().toString().isEmpty() &&
                        !goalWeightEditText.getText().toString().isEmpty() &&
                        !heightFeetEditText.getText().toString().isEmpty() &&
                        !heightInchesEditText.getText().toString().isEmpty() ) {

                    height = heightFeetEditText.getText().toString() + "'" + heightInchesEditText.getText().toString() + "\"";

                    Profile.setFirstName(firstNameEditText.getText().toString());
                    Profile.setLastName(lastNameEditText.getText().toString());
                    Profile.setGender(gender);
                    Profile.setAge(ageEditText.getText().toString());
                    Profile.setWeight(weightEditText.getText().toString());
                    Profile.setGoalWeight(goalWeightEditText.getText().toString());
                    Profile.setHeightFeet(heightFeetEditText.getText().toString());
                    Profile.setHeightInches(heightInchesEditText.getText().toString());

                    FragmentTransaction transaction = fm.beginTransaction();
                    //transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_back_in, R.anim.slide_back_out);

                    editor.putString("fName", Profile.getFirstName());
                    editor.putString("lName", Profile.getLastName());
                    editor.putString("gender", Profile.getGender());
                    editor.putString("age", Profile.getAge());
                    editor.putString("weight", Profile.getWeight());
                    editor.putString("goalWeight", Profile.getGoalWeight());
                    editor.putString("heightFeet", Profile.getHeightFeet());
                    editor.putString("heightInches", Profile.getHeightInches());
                    editor.putBoolean("form_not_completed", true);

                    editor.apply();


                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        });

        return view;
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String heightFeetEditText = parent.getItemAtPosition(position).toString();
        String heightInchesEditText = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
