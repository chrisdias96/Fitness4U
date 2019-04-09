package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.JavaBean.Profile;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TipFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TipFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences sf;

    private RequestQueue requestQueue;

    JSONObject jsonRequest;
    public static final String TAG = "VolleyPatterns";

    private static TipFragment sInstance;


    private OnFragmentInteractionListener mListener;

    public TipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TipFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TipFragment newInstance(String param1, String param2) {
        TipFragment fragment = new TipFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized TipFragment getInstance() {
        return sInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue  = Volley.newRequestQueue(getContext());
        }

        return requestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the singleton
        sInstance = this;

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tip, container, false);

        //Recover the results in the Profile settings
        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_profile, false);
        sf = PreferenceManager.getDefaultSharedPreferences(getContext());

        final TextView weightLabel = view.findViewById(R.id.bmiWeightLabel);
        final TextView heightFeetLabel = view.findViewById(R.id.bmiHeightFeetLabel);
        final TextView heightInchesLabel = view.findViewById(R.id.bmiHeightInchesLabel);

        final EditText weightEditText = view.findViewById(R.id.bmiWeightEditText);
        final EditText heightInFeetEditText = view.findViewById(R.id.bmiHeightFeet);
        final EditText heightInInchesEditText = view.findViewById(R.id.BMIHeightInches);

        final TextView bmiAnswer = view.findViewById(R.id.bmiAnswer);
        final TextView indicatorAnswer = view.findViewById(R.id.indicatorAnswer);

        Button calculateBMI = view.findViewById(R.id.calculateBmiButton);

        //Populate the editText with what's in the Profile settings
        weightEditText.setText(sf.getString("weight", Profile.getWeight()));
        heightInFeetEditText.setText(sf.getString("heightFeet", Profile.getHeightFeet()));
        heightInInchesEditText.setText(sf.getString("heightInches", Profile.getHeightInches()));

        //When the calculateBMI button is clicked
        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if any of the editTexts are empty
                //If they are change their labels to red
                if (weightEditText.getText().toString().isEmpty()) {
                    weightLabel.setTextColor(Color.RED);
                }
                if (heightInFeetEditText.getText().toString().isEmpty()) {
                    heightFeetLabel.setTextColor(Color.RED);
                }
                if (heightInInchesEditText.getText().toString().isEmpty()) {
                    heightInchesLabel.setTextColor(Color.RED);
                }

                //If all of the EditTexts are populated
                if(!weightEditText.getText().toString().isEmpty() &&
                        !heightInFeetEditText.getText().toString().isEmpty() &&
                        !heightInInchesEditText.getText().toString().isEmpty() ) {

                    //Change the labels back to black and carry on
                    weightLabel.setTextColor(Color.BLACK);
                    heightFeetLabel.setTextColor(Color.BLACK);
                    heightInchesLabel.setTextColor(Color.BLACK);

                    String height = heightInFeetEditText.getText() + "-" + heightInInchesEditText.getText();

                    requestQueue = Volley.newRequestQueue(getContext());
                    String url =   "https://bmi.p.rapidapi.com/";

                    HashMap<String, String> params = new HashMap<>();
                    params.put("weight", weightEditText.getText().toString());
                    params.put("height", height);
                    params.put("sex", "m");
                    params.put("age", "24");

                    //jsonObjectRequest request = new JSONObject("{\"name\":\"value\":}");

                    JsonObjectRequest request = new JsonObjectRequest(URL, new JSONObject(params), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("JSON RESPONSE", response.toString());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error:", error.getMessage());
                        }
                    });

                    TipFragment.getInstance().addToRequestQueue(request);

                }

            }
        });



//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONObject object = response.getJSONObject("main");
//                    System.out.println(response);
//                    String text = "Current Temp: " + object.getDouble("temp")+ "\u2103";
//                    viewHolder.temp.setText(text);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error.getLocalizedMessage());
//            }
//        });
//        requestQueue.add(request);

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
