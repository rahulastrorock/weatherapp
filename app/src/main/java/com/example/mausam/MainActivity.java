package com.example.mausam;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText et;
TextView tv;
String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}";
String apikey = "926e83457272f61fa39360f51920cff1";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
    }
    public void getweather(View v){
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi =retrofit.create(weatherapi.class);
        final Call<example> examplecall=myapi.getweather(et.getText().toString().trim(),apikey);
        examplecall.enqueue(new Callback<com.example.mausam.example>() {
            @Override
            public void onResponse(Call<com.example.mausam.example> call, Response<com.example.mausam.example> response) {
                if(response.code() == 404){
                    Toast.makeText(MainActivity.this,"please enter a valid city", Toast.LENGTH_LONG).show();
                }
                else if(!(response.isSuccessful())){
                    Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_LONG).show();

                }
                example mydata=response.body();
                Main main = mydata.getMain();
                Double temp = main.getTemp();
                Integer temprature = (int)(temp - 273.15);
                tv.setText("TEMPRATURE--   "+String.valueOf(temprature)+"c");
                Integer humidity = main.getHumidity();
                tv.append("\n"+"HUMIDITY--  "+String.valueOf(humidity)+"g.m-3");
                Integer pres = main.getPressure();
                tv.append("\n"+"PRESSURE--  "+String.valueOf(pres)+"Pa");
            }

            @Override
            public void onFailure(Call<com.example.mausam.example> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}