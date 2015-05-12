package com.dev.chuck.weathergame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ForecastActivity extends Activity implements View.OnClickListener{

    Button btnClose;
    TextView textCity, textTemperature, textWeather, textLoser;
    ImageView imgWeather;

    long timestamp;

    LinearLayout playerListLayout;

    ProgressBar progressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        btnClose = (Button)findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        textCity = (TextView)findViewById(R.id.textCity);
        textTemperature = (TextView)findViewById(R.id.textTemperature);
        textWeather = (TextView)findViewById(R.id.textWeather);
        textLoser = (TextView)findViewById(R.id.textLoser);

        imgWeather = (ImageView) findViewById(R.id.imgWeather);

        playerListLayout = (LinearLayout) findViewById(R.id.playerListLayout);

        progressLoading = (ProgressBar) findViewById(R.id.progressLoading);
        progressLoading.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String country = intent.getExtras().getString("country");
        timestamp = intent.getExtras().getLong("timestamp");
        textCity.setText(name+", "+country);

        City city = CityManager.getInstance(getApplicationContext()).select(name, country);

        String urlString = "http://api.openweathermap.org/data/2.5/weather";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("lat",Double.toString(city.getLatitude()));
        params.put("lon",Double.toString(city.getLongitude()));
        params.put("units","metric");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams(params);
        client.get(urlString, requestParams, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String temperature = "";
                String desc = "";
                String icon = "";
                int imgIcon = 0;

                try{
                    String strResponse = new String(responseBody);
                    JSONObject jsonResponse = new JSONObject(strResponse);

                    JSONArray jsonArrayWeather = jsonResponse.getJSONArray("weather");
                    JSONObject jsonWeather = jsonArrayWeather.getJSONObject(0);
                    JSONObject jsonMain = jsonResponse.getJSONObject("main");

                    double doubleTemp = Double.parseDouble(jsonMain.getString("temp"));
                    temperature = jsonMain.getString("temp") + "°C";
                    desc = jsonWeather.getString("description");
                    icon = jsonWeather.getString("icon");

                    progressLoading.setVisibility(View.GONE);

                    calculateResult(doubleTemp);

                    switch(icon){
                        case("01d"):
                            imgIcon = R.mipmap.icon_01d;
                            break;
                        case("01n"):
                            imgIcon = R.mipmap.icon_01n;
                            break;
                        case("02d"):
                            imgIcon = R.mipmap.icon_02d;
                            break;
                        case("02n"):
                            imgIcon = R.mipmap.icon_02n;
                            break;
                        case("03d"):
                            imgIcon = R.mipmap.icon_03d;
                            break;
                        case("03n"):
                            imgIcon = R.mipmap.icon_03n;
                            break;
                        case("04d"):
                            imgIcon = R.mipmap.icon_04d;
                            break;
                        case("04n"):
                            imgIcon = R.mipmap.icon_04n;
                            break;
                        case("09d"):
                            imgIcon = R.mipmap.icon_09d;
                            break;
                        case("09n"):
                            imgIcon = R.mipmap.icon_09n;
                            break;
                        case("10d"):
                            imgIcon = R.mipmap.icon_10d;
                            break;
                        case("10n"):
                            imgIcon = R.mipmap.icon_10n;
                            break;
                        case("11d"):
                            imgIcon = R.mipmap.icon_11d;
                            break;
                        case("11n"):
                            imgIcon = R.mipmap.icon_11n;
                            break;
                        case("13d"):
                            imgIcon = R.mipmap.icon_13d;
                            break;
                        case("13n"):
                            imgIcon = R.mipmap.icon_13n;
                            break;
                        case("50d"):
                            imgIcon = R.mipmap.icon_50d;
                            break;
                        case("50n"):
                            imgIcon = R.mipmap.icon_50n;
                            break;
                        default:
                            imgIcon = R.mipmap.icon_50d;
                            break;
                    }

                }catch(Exception ex){
                    temperature = "ERROR";
                    desc = "Weather API does not respond.";
                    imgIcon = R.mipmap.icon_50d;
                }finally {
                    textTemperature.setText(temperature);
                    textWeather.setText(desc);
                    imgWeather.setImageResource(imgIcon);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });



        //textBottom.setText(object.toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forecast, menu);
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
            case R.id.btnClose:
                finish();
                break;
            default:
                break;
        }

    }

    private void calculateResult(double doubleTemp){

        List<String> loserName = new ArrayList<>();
        double largestDifference = 0.0;
        List<Player> playerList = UserManager.getInstance(getApplicationContext()).selectList(timestamp);

        for(Player player : playerList){
            double userTemperature = player.getTemperature();
            if(Math.abs(doubleTemp - userTemperature) > largestDifference){
                largestDifference = Math.abs(doubleTemp - userTemperature);
                loserName.clear();
                loserName.add(player.getName());
            }else if(Math.abs(doubleTemp - userTemperature) == largestDifference){
                loserName.add(player.getName());
            }
            final TextView rowTextView = new TextView(this);
            rowTextView.setText(player.getName() + " : " + player.getTemperature() + "°C");
            rowTextView.setGravity(Gravity.CENTER);
            playerListLayout.addView(rowTextView);
        }
        StringBuffer loserText = new StringBuffer();
        loserText.append("Loser : ");
        for(String loser : loserName){
            loserText.append(loser + " ");
        }
        textLoser.setText(loserText.toString());

    }

}
