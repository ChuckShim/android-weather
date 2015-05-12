package com.dev.chuck.weathergame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    int[] guksu;

    int dotsCount;
    LinearLayout dotsLayout;
    static TextView dotsText[];

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        guksu = new int[]{
                R.mipmap.guksu01,
                R.mipmap.guksu02,
                R.mipmap.guksu03,
                R.mipmap.guksu04
        };
        viewPager = (ViewPager) v.findViewById(R.id.pager);
        pagerAdapter = new ViewPagerAdapter(getActivity(), guksu);

        viewPager.setAdapter(pagerAdapter);

        dotsLayout = (LinearLayout) v.findViewById(R.id.imageCount);
        dotsCount = viewPager.getAdapter().getCount();

        dotsText = new TextView[dotsCount];

        for(int i=0; i<dotsCount; i++){
            dotsText[i] = new TextView(getActivity());
            dotsText[i].setText(".");
            dotsText[i].setTextSize(100);
            dotsText[i].setTypeface(null, Typeface.BOLD);
            dotsText[i].setTextColor(Color.GRAY);
            dotsLayout.addView(dotsText[i]);
        }
        dotsText[0].setTextColor(Color.WHITE);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i < dotsCount ; i++){
                    dotsText[i].setTextColor(Color.GRAY);
                }
                dotsText[position].setTextColor(Color.WHITE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionHome(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteractionHome(Uri uri);
    }

}
