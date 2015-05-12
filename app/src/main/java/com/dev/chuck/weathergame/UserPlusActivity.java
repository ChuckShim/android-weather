package com.dev.chuck.weathergame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class UserPlusActivity extends Activity implements View.OnClickListener{

    String name="";
    String country="";

    AutoCompleteTextView textUserName;
    EditText textUserTemperature;

    TextView textUserNumber;

    int userNumber;

    Button btnUserAdd, btnUserResult;

    long timestamp;

    String names[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_plus);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        country = intent.getExtras().getString("country");

        List<Friends> friendsList = FriendsManager.getInstance(getApplicationContext()).selectAll();
        int sizeFriendsList = friendsList.size();
        names = new String[sizeFriendsList];
        for(int i=0; i<sizeFriendsList; i++){
            names[i] = friendsList.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, names);

        textUserName = (AutoCompleteTextView) findViewById(R.id.textUserName);
        textUserName.setAdapter(adapter);

        textUserTemperature = (EditText) findViewById(R.id.textUserTemperature);

        textUserNumber = (TextView)  findViewById(R.id.textUserNumber);

        btnUserAdd = (Button) findViewById(R.id.btnUserAdd);
        btnUserResult = (Button) findViewById(R.id.btnUserResult);
        btnUserAdd.setOnClickListener(this);
        btnUserResult.setOnClickListener(this);

        userNumber = 1;

        timestamp = System.currentTimeMillis();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_plus, menu);
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

        String userName, userTemperature;

        switch (v.getId()) {
            case R.id.btnUserAdd:

                userName = textUserName.getText().toString().trim();
                userTemperature = textUserTemperature.getText().toString().trim();

                if(userName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(userTemperature.isEmpty()){
                    Toast.makeText(getApplicationContext(), "온도를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                try{
                    Double.parseDouble(userTemperature);
                }catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "온도값이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

                UserManager.getInstance(getApplicationContext()).save(timestamp, userName, Double.parseDouble(userTemperature));
                userNumber++;

                textUserName.setText("");
                textUserTemperature.setText("");
                textUserNumber.setText(Integer.toString(userNumber));

                break;
            case R.id.btnUserResult:

                if(userNumber < 3){
                    Toast.makeText(getApplicationContext(), "플레이어수가 부족합니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

                Intent intent = new Intent(this, ForecastActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("country", country);
                intent.putExtra("timestamp", timestamp);

                startActivity(intent);
                finish();

                break;
            default:
                break;
        }

    }
}
