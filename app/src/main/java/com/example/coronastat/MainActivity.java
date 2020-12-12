package com.example.coronastat;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    StringBuffer sb =new StringBuffer();
    Spinner mSpinner;
    TextView mTextView;
    String [] mStates = {"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal","Chandigarh","Delhi","Puducherry"};
    TextView dateTimeDisplay;
    Toast mToast;
    State state = new State();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    TextView count;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        count =  findViewById(R.id.count);
        dateTimeDisplay = (TextView)findViewById(R.id.date);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);
        mSpinner = findViewById(R.id.stateSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                state.setStateName(adapterView.getItemAtPosition(i).toString());
                Log.d("msg",state.getStateName().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }

        );
        Button mButton = findViewById(R.id.submitButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Log.d("msg",state.getStateName().toString());
                try {
                    fetchData(state);
                   // count.setText(sbResult);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        );

        }

    private void fetchData(State state1) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://corona-virus-world-and-india-data.p.rapidapi.com/api_india")
                .get()
                .addHeader("x-rapidapi-key", "014fdd46a9msh40e01402ae728d1p179b66jsnf43a77de8d9e")
                .addHeader("x-rapidapi-host", "corona-virus-world-and-india-data.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
       // Log.d("see here", response.body().string());
        String responseBody = response.body().string();
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject stateWise = jsonObject.getJSONObject("state_wise");
        JSONObject stateJson;
        String active;
        String deltaConfirmed;
        if(Arrays.asList(mStates).contains(state.getStateName()) ){
            stateJson = stateWise.getJSONObject(state.getStateName());
            active = stateJson.getString("active");
            deltaConfirmed = stateJson.getString("deltaconfirmed");
            Log.d("msg",active);
            count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
        }
        /*switch(state.getStateName()){
            case "Maharashtra" :
                stateJson = stateWise.getJSONObject("Maharashtra");
                active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Andhra Pradesh" :
                stateJson = stateWise.getJSONObject("Andhra Pradesh");
                active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Arunachal Pradesh" :
                stateJson= stateWise.getJSONObject("Arunachal Pradesh");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Assam" :
                 stateJson = stateWise.getJSONObject("Assam");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Bihar" :
                 stateJson = stateWise.getJSONObject("Bihar");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Chhattisgarh" :
                 stateJson = stateWise.getJSONObject("Chhattisgarh");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Goa" :
                stateJson = stateWise.getJSONObject("Goa");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Gujarat" :
                 stateJson = stateWise.getJSONObject("Gujarat");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Haryana" :
                 stateJson = stateWise.getJSONObject("Haryana");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Himachal Pradesh" :
                 stateJson = stateWise.getJSONObject("Himachal Pradesh");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Jammu and Kashmir" :
                 stateJson = stateWise.getJSONObject("Jammu and Kashmir");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Jharkhand" :
                 stateJson = stateWise.getJSONObject("Jharkhand");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Karnataka" :
                 stateJson = stateWise.getJSONObject("Karnataka");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Kerala" :
                 stateJson = stateWise.getJSONObject("Kerala");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Madhya Pradesh" :
                 stateJson = stateWise.getJSONObject("Madhya Pradesh");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Manipur" :
                stateJson = stateWise.getJSONObject("Manipur");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Meghalaya" :
                stateJson = stateWise.getJSONObject("Meghalaya");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Mizoram" :
                stateJson = stateWise.getJSONObject("Mizoram");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Nagaland" :
                 stateJson = stateWise.getJSONObject("Nagaland");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Odisha" :
                 stateJson = stateWise.getJSONObject("Odisha");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Punjab" :
                 stateJson = stateWise.getJSONObject("Punjab");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Rajasthan" :
                 stateJson = stateWise.getJSONObject("Rajasthan");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Sikkim" :
                 stateJson = stateWise.getJSONObject("Sikkim");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Tamil Nadu" :
                 stateJson = stateWise.getJSONObject("Tamil Nadu");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Telangana" :
                 stateJson = stateWise.getJSONObject("Telangana");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Tripura" :
                 stateJson = stateWise.getJSONObject("Tripura");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active);
                break;
            case "Uttar Pradesh" :
                 stateJson = stateWise.getJSONObject("Uttar Pradesh");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Uttarakhand" :
                 stateJson = stateWise.getJSONObject("Uttarakhand");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "West Bengal" :
                 stateJson = stateWise.getJSONObject("West Bengal");
                 active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");

                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Chandigarh" :
                stateJson = stateWise.getJSONObject("Chandigarh");
                active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Delhi" :
                stateJson = stateWise.getJSONObject("Delhi");
                active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;
            case "Puducherry" :
                stateJson = stateWise.getJSONObject("Puducherry");
                active = stateJson.getString("active");
                deltaConfirmed = stateJson.getString("deltaconfirmed");
                Log.d("msg",active);
                count.setText("Total cases active: "+ active +"\n" + "Total cases today: " + deltaConfirmed);
                break;


            default:
                count.setText("Please select a State");
        }*/


    }

}


