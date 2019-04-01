package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Database.DatabaseHandler;
import com.example.myapplication.JavaBean.Workout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateWorkoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateWorkoutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Workout mParam1;
    FragmentManager fm;
    private OnFragmentInteractionListener mListener;

    public UpdateWorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment UpdateWorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateWorkoutFragment newInstance(Parcelable param1) {
        UpdateWorkoutFragment fragment = new UpdateWorkoutFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_workout, container, false);
        MainActivity.fab.hide();
        final EditText jumpingJacksEditText = view.findViewById(R.id.jumpingJacksEditText);
        final EditText plankEditText = view.findViewById(R.id.plankEditText);
        final EditText lungesEditText = view.findViewById(R.id.lungesEditText);
        final EditText pushupsEditText = view.findViewById(R.id.pushupsEditText);
        final EditText situpsEditText = view.findViewById(R.id.situpsEditText);
        final EditText highKneesEditText = view.findViewById(R.id.highKneesEditText);
        final EditText sidePlankEditText = view.findViewById(R.id.sidePlankEditText);
        final EditText tricepDipEditText = view.findViewById(R.id.tricepDipEditText);
        final EditText squatsEditText = view.findViewById(R.id.squatsEditText);

        Button submitButton = view.findViewById(R.id.submitChanges);
        if(mParam1 != null){
            jumpingJacksEditText.setText(mParam1.getJumpingJack());
            plankEditText.setText(mParam1.getPlank());
            lungesEditText.setText(mParam1.getLunge());
            pushupsEditText.setText(mParam1.getPushup());
            situpsEditText.setText(mParam1.getSitup());
            highKneesEditText.setText(mParam1.getHighKnee());
            sidePlankEditText.setText(mParam1.getSidePlank());
            tricepDipEditText.setText(mParam1.getTricepDip());
            squatsEditText.setText(mParam1.getSquat());
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Build the location object to add to the database
                mParam1.setJumpingJack(jumpingJacksEditText.getText().toString());
                mParam1.setPlank(plankEditText.getText().toString());
                mParam1.setLunge(lungesEditText.getText().toString());
                mParam1.setPushup(pushupsEditText.getText().toString());
                mParam1.setSitup(situpsEditText.getText().toString());
                mParam1.setHighKnee(highKneesEditText.getText().toString());
                mParam1.setSidePlank(sidePlankEditText.getText().toString());
                mParam1.setTricepDip(tricepDipEditText.getText().toString());
                mParam1.setSquat(squatsEditText.getText().toString());

                //Get access to the database
                DatabaseHandler db = new DatabaseHandler(getContext());
                db.updateWorkout(mParam1);
                db.close();
                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
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
