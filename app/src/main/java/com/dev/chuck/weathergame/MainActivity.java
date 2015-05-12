package com.dev.chuck.weathergame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.List;
import java.util.Random;


public class MainActivity extends Activity implements View.OnClickListener{

    Button btnRandom, btnForecast;

    TextView textCity, textCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String isJsonLoaded = mPref.getString("isJsonLoaded", "N");

        if("N".equals(isJsonLoaded)){
            try{

                InputStream isF = getApplicationContext().getAssets().open("friends.json");
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
                    FriendsManager.getInstance(getApplicationContext()).save(itemF.getString("name"));
                }

                CityManager.getInstance(getApplicationContext()).deleteAll();

                InputStream is = getApplicationContext().getAssets().open("cities.json");
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
                    CityManager.getInstance(getApplicationContext()).save(item.getString("name"), item.getString("country"), item.getLong("latitude"), item.getLong("longitude"));
                }

                SharedPreferences.Editor editor = mPref.edit();
                editor.putString("isJsonLoaded", "Y");
                editor.commit();

                Toast.makeText(getApplicationContext(), "도시정보 데이터가 로드되었습니다.", Toast.LENGTH_SHORT).show();

            }catch(Exception e){

            }

        }



        btnRandom = (Button)findViewById(R.id.btnRandom);
        btnRandom.setOnClickListener(this);

        btnForecast = (Button)findViewById(R.id.btnForecast);
        btnForecast.setOnClickListener(this);

        textCity = (TextView)findViewById(R.id.textCity);
        textCountry = (TextView)findViewById(R.id.textCountry);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()) {
            case R.id.btnRandom:
                List<City> cityList = CityManager.getInstance(getApplicationContext()).selectAll();

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

                    Intent intent = new Intent(this, UserPlusActivity.class);
                    intent.putExtra("name", displayCity);
                    intent.putExtra("country", displayCountry);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "랜덤 버튼을 눌러 도시를 선택하세요.", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }

    }
}
