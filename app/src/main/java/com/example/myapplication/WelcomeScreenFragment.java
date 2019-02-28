package com.example.myapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


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

    String gender;

    TextView genderText;
    TextView ageText;
    TextView weightText;
    TextView goalWeightText;
    TextView heightText;

    RadioGroup radioGroup;
    RadioButton maleRadio;
    RadioButton femaleRadio;
    EditText ageEditText;
    EditText weightEditText;
    EditText goalWeightEditText;
    Button submitButton;

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

        genderText = view.findViewById(R.id.genderLabel);
        ageText = view.findViewById(R.id.ageLabel);
        weightText = view.findViewById(R.id.weightLabel);
        goalWeightText = view.findViewById(R.id.goalWeightLabel);
        heightText = view.findViewById(R.id.heightLabel);

        radioGroup = view.findViewById(R.id.genderRadioGroup);
        ageEditText = view.findViewById(R.id.ageEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        goalWeightEditText = view.findViewById(R.id.goalWeightEditText);
        submitButton = view.findViewById(R.id.submitProfileButton);

        final Spinner heightFeet = view.findViewById(R.id.heightFeet);
        final Spinner heightInches = view.findViewById(R.id.heightInches);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.heightDropdown, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this.getContext(),
                R.array.heightDropdown, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        heightFeet.setAdapter(adapter);
        heightFeet.setOnItemSelectedListener(this);
        heightInches.setAdapter(adapter);
        heightInches.setOnItemSelectedListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.maleRadioButton:
                       maleRadio = view.findViewById(R.id.maleRadioButton);
                       maleRadio.getText();
                       gender = "Male";
                       radioGroup.setSelected(true);
                       break;
                    case R.id.femaleRadioButton:
                        femaleRadio = view.findViewById(R.id.femaleRadioButton);
                        gender = "Female";
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

                System.out.println(radioGroup.isSelected());
                System.out.println(gender);
                System.out.println(ageEditText.getText());
                System.out.println(weightEditText.getText());
                System.out.println(goalWeightEditText.getText());
                System.out.println(heightFeet.getSelectedItem().toString() + "'" + heightInches.getSelectedItem().toString() + "\"");

                if (!radioGroup.isSelected()) {
                    genderText.setTextColor(Color.RED);
                }
                if (ageEditText.getText().toString().isEmpty()) {
                    ageText.setTextColor(Color.RED);
                }
                if (weightEditText.getText().toString().isEmpty()) {
                    weightText.setTextColor(Color.RED);
                }
                if (goalWeightEditText.getText().toString().isEmpty()) {
                    goalWeightText.setTextColor(Color.RED);
                }
                if (heightFeet.getSelectedItem().toString().isEmpty() || heightInches.getSelectedItem().toString().isEmpty()) {
                    heightText.setTextColor(Color.RED);
                }

//                if (!radioGroup.isSelected() &&
//                        !ageEditText.getText().toString().isEmpty() &&
//                        !weightEditText.getText().toString().isEmpty() &&
//                        !goalWeightEditText.getText().toString().isEmpty() &&
//                        !heightFeet.getSelectedItem().toString().isEmpty() &&
//                        !heightInches.getSelectedItem().toString().isEmpty()) {
//
//
//                }





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
