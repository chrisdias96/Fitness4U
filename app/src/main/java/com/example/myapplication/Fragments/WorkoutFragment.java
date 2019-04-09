package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Activities.WorkoutActivity;
import com.example.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkoutChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sf;


    ImageButton workoutButton;

    FragmentManager fm;

    private OnFragmentInteractionListener mListener;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutChooserFragment newInstance(int param1, String param2) {
        WorkoutChooserFragment fragment = new WorkoutChooserFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
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
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        MainActivity.fab.hide();

        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_exercises, false);
        sf = PreferenceManager.getDefaultSharedPreferences(getContext());

        fm = getActivity().getSupportFragmentManager();

        /**
         * Here goes the ViewPager
         */
        final ViewPager workoutViewPager = view.findViewById(R.id.workoutViewPager);
//        workoutViewPager.setClipToPadding(false);
//        workoutViewPager.setPageMargin(12);
        final ViewPager graphViewPager = view.findViewById(R.id.graphViewPager);
        graphViewPager.setEnabled(false);

        final WorkoutViewPagerAdapter workoutAdapter = new WorkoutViewPagerAdapter(getChildFragmentManager());
        final GraphViewPagerAdapter graphAdapter = new GraphViewPagerAdapter(getChildFragmentManager());
        workoutViewPager.setAdapter(workoutAdapter);
        graphViewPager.setAdapter(graphAdapter);

        workoutViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        workoutViewPager.setCurrentItem(0);
        graphViewPager.setCurrentItem(0);

        ImageButton rightButton = view.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int workoutLocation = workoutViewPager.getCurrentItem();
                int graphLocation = graphViewPager.getCurrentItem();
                workoutLocation++;
                graphLocation++;
                if(workoutLocation >= workoutAdapter.getCount() &&
                        graphLocation >= graphAdapter.getCount()){
                    workoutViewPager.setCurrentItem(0);
                    graphViewPager.setCurrentItem(0);
                }
                else{
                    workoutViewPager.setCurrentItem(workoutLocation);
                    graphViewPager.setCurrentItem(graphLocation);
                }
            }
        });

        ImageButton leftButton = view.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int workoutLocation = workoutViewPager.getCurrentItem();
                int graphLocation = graphViewPager.getCurrentItem();
                workoutLocation--;
                graphLocation--;
                if(workoutLocation >= 0 && graphLocation >= 0){
                    workoutViewPager.setCurrentItem(workoutLocation);
                    graphViewPager.setCurrentItem(graphLocation);
                }
                else{
                    workoutViewPager.setCurrentItem(workoutAdapter.getCount());
                    graphViewPager.setCurrentItem(graphAdapter.getCount());
                }
            }
        });

        workoutButton = view.findViewById(R.id.workoutButton);

        workoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int location = workoutViewPager.getCurrentItem();

                Intent intent = new Intent(getActivity(), WorkoutActivity.class);

                intent.putExtra("locationOfViewPager", location);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        return view;
    }

    class WorkoutViewPagerAdapter extends FragmentPagerAdapter {

        public WorkoutViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    // image of workout unchecked or checked, followed by workout week/day
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W1/D1");
                case 1:
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W1/D2");
                case 2:
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W1/D3");
                case 3:
                    // week 2
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W2/D1");
                case 4:
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W2/D2");
                case 5:
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W2/D3");
                case 6:
                    // Week 3
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W3/D1");
                case 7:
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W3/D2");
                case 8:
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W3/D3");
                case 9:
                    // Week 4
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W4/D1");
                case 10:
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W4/D2");
                case 11:
                    return WorkoutChooserFragment.newInstance(R.raw.buttonunchecked, "W4/D3");
                default:
                    return WorkoutChooserFragment.newInstance(0, "this should not be seen");
            }
        }

        @Override
        public int getCount() {
            return 12;
        }

    }

    class GraphViewPagerAdapter extends FragmentPagerAdapter {

        public GraphViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    // image of workout unchecked or checked, followed by workout week/day
                    return GraphChooserFragment.newInstance(R.raw.w1d1);
                case 1:
                    return GraphChooserFragment.newInstance(R.raw.w1d2);
                case 2:
                    return GraphChooserFragment.newInstance(R.raw.w1d3);
                case 3:
                    // week 2
                    return GraphChooserFragment.newInstance(R.raw.w2d1);
                case 4:
                    return GraphChooserFragment.newInstance(R.raw.w2d2);
                case 5:
                    return GraphChooserFragment.newInstance(R.raw.w2d3);
                case 6:
                    // Week 3
                    return GraphChooserFragment.newInstance(R.raw.w3d1);
                case 7:
                    return GraphChooserFragment.newInstance(R.raw.w3d2);
                case 8:
                    return GraphChooserFragment.newInstance(R.raw.w3d3);
                case 9:
                    // Week 4
                    return GraphChooserFragment.newInstance(R.raw.w4d1);
                case 10:
                    return GraphChooserFragment.newInstance(R.raw.w4d2);
                case 11:
                    return GraphChooserFragment.newInstance(R.raw.w4d3);
                default:
                    return GraphChooserFragment.newInstance(R.raw.w4d3);
            }
        }

        @Override
        public int getCount() {
            return 12;
        }

    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
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
