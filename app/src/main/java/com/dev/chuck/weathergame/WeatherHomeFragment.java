package com.dev.chuck.weathergame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * Created by Chuck on 2015. 4. 24..
 */
public class WeatherHomeFragment extends Fragment implements View.OnClickListener{

    Button btnRandom, btnForecast;

    TextView textCity, textCountry;

    private OnFragmentInteractionListener mListener;

    public static WeatherHomeFragment newInstance() {
        WeatherHomeFragment fragment = new WeatherHomeFragment();
        return fragment;
    }

    public WeatherHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteractionWeatherHome(Uri uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_main, container, false);

        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        String isJsonLoaded = mPref.getString("isJsonLoaded", "N");

        if("N".equals(isJsonLoaded)){
            try{

                InputStream isF = getActivity().getApplicationContext().getAssets().open("friends.json");
                int sizeF = isF.available();
                byte[] bufferF = new byte[sizeF];
                isF.read(bufferF);
                isF.close();

                String bufferStringF = new String(bufferF);
                JSONObject jsonObjectF = new JSONObject(bufferStringF);
                JSONArray jsonArrayF = jsonObjectF.getJSONArray("friends");

                int jsonSizeF = jsonArrayF.length();
                JSONObject itemF;
                for(int i=0;i<jsonSizeF;i++){
                    itemF = jsonArrayF.getJSONObject(i);
                    FriendsManager.getInstance(getActivity().getApplicationContext()).save(itemF.getString("name"));
                }

                CityManager.getInstance(getActivity().getApplicationContext()).deleteAll();

                InputStream is = getActivity().getApplicationContext().getAssets().open("cities.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                String bufferString = new String(buffer);
                JSONObject jsonObject = new JSONObject(bufferString);
                JSONArray jsonArray = jsonObject.getJSONArray("cities");

                int jsonSize = jsonArray.length();
                JSONObject item;
                for(int i=0;i<jsonSize;i++){
                    item = jsonArray.getJSONObject(i);
                    CityManager.getInstance(getActivity().getApplicationContext()).save(item.getString("name"), item.getString("country"), item.getLong("latitude"), item.getLong("longitude"));
                }

                SharedPreferences.Editor editor = mPref.edit();
                editor.putString("isJsonLoaded", "Y");
                editor.commit();

                Toast.makeText(getActivity().getApplicationContext(), "도시정보 데이터가 로드되었습니다.", Toast.LENGTH_SHORT).show();

            }catch(Exception e){

            }

        }



        btnRandom = (Button) v.findViewById(R.id.btnRandom);
        btnRandom.setOnClickListener(this);

        btnForecast = (Button) v.findViewById(R.id.btnForecast);
        btnForecast.setOnClickListener(this);

        textCity = (TextView) v.findViewById(R.id.textCity);
        textCountry = (TextView) v.findViewById(R.id.textCountry);


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionWeatherHome(uri);
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



    @Override
    public void onClick(View v){

        switch (v.getId()) {
            case R.id.btnRandom:
                List<City> cityList = CityManager.getInstance(getActivity().getApplicationContext()).selectAll();

                if(cityList != null){
                    int listSize = cityList.size();

                    Random generator = new Random();
                    int number = generator.nextInt(listSize);

                    City mCity = cityList.get(number);
                    textCity.setText(mCity.getName());
                    textCountry.setText(mCity.getCountry());
                }

                break;
            case R.id.btnForecast:
                String displayCity = textCity.getText().toString();
                String displayCountry = textCountry.getText().toString();

                if(displayCity != null
                        && displayCity.length() > 0
                        && displayCountry != null
                        && displayCountry.length() > 0){

                    Intent intent = new Intent(getActivity(), UserPlusActivity.class);
                    intent.putExtra("name", displayCity);
                    intent.putExtra("country", displayCountry);
                    startActivity(intent);

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "랜덤 버튼을 눌러 도시를 선택하세요.", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }

    }
}
