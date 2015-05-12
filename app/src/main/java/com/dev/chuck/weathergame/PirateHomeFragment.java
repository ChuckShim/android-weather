package com.dev.chuck.weathergame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.LogRecord;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.dev.chuck.weathergame.PirateHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.dev.chuck.weathergame.PirateHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PirateHomeFragment extends Fragment implements View.OnClickListener{


    private OnFragmentInteractionListener mListener;

    int bombPosition;
    final int bombCount = 24;

    int drawPosition;

    Integer idx_btnPirate[] = {
            R.id.btnPirate00, R.id.btnPirate01, R.id.btnPirate02, R.id.btnPirate03, R.id.btnPirate04,
            R.id.btnPirate05, R.id.btnPirate06, R.id.btnPirate07, R.id.btnPirate08, R.id.btnPirate09,
            R.id.btnPirate10, R.id.btnPirate11, R.id.btnPirate12, R.id.btnPirate13, R.id.btnPirate14,
            R.id.btnPirate15, R.id.btnPirate16, R.id.btnPirate17, R.id.btnPirate18, R.id.btnPirate19,
            R.id.btnPirate20, R.id.btnPirate21, R.id.btnPirate22, R.id.btnPirate23
    };

    Button btnPirate[];

    boolean isBtnPressed[];

    public static PirateHomeFragment newInstance() {
        PirateHomeFragment fragment = new PirateHomeFragment();
        return fragment;
    }

    public PirateHomeFragment() {
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
        View v = inflater.inflate(R.layout.fragment_pirate_home, container, false);

        btnPirate = new Button[bombCount];
        isBtnPressed = new boolean[bombCount];

        for(int i=0; i<bombCount; i++){
            btnPirate[i] = (Button) v.findViewById(idx_btnPirate[i]);
            btnPirate[i].setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Random generator = new Random();
        bombPosition = generator.nextInt(bombCount);
        drawPosition = 0;

        for(int i=0; i<bombCount; i++){
            btnPirate[i].setBackgroundResource(R.mipmap.button_fill);
            isBtnPressed[i] = false;
        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<bombCount; i++) {
                    btnPirate[i].setBackgroundResource(R.mipmap.button_empty);
                }
            }
        }, 500);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionPirateHome(uri);
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
        public void onFragmentInteractionPirateHome(Uri uri);
    }

    @Override
    public void onClick(View v){

        int buttonID = v.getId();
        int idxArray = Arrays.asList(idx_btnPirate).indexOf(buttonID);

        if(idxArray < 0){
            // 예외로직
        }else{
            if(bombPosition == idxArray){
                btnPirate[idxArray].setBackgroundResource(R.mipmap.btn_red);

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        onResume();
                    }
                });
                alert.setMessage("커피 쏴!!! 두번 쏴!!!");
                alert.show();
            }else{
                if(isBtnPressed[idxArray]){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("이미 누른 버튼입니다.");
                    alert.show();
                }else{
                    btnPirate[idxArray].setBackgroundResource(R.mipmap.button_fill);
                    isBtnPressed[idxArray] = true;
                }
            }
        }

    }




}
