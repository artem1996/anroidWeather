package com.example.admin.weatherj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ru.mail.weather.lib.City;

public class CityChangeActivity extends AppCompatActivity {

    private void sendData(String city) {
        Intent intent = new Intent(CityChangeActivity.this, MainActivity.class);

        intent.putExtra("city", city);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_change);

        final City[] allcity = City.values();

        Button btn1 = (Button) findViewById(R.id.btn_city1);
        btn1.setText(allcity[0].toString());
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(allcity[0].toString());
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn_city2);
        btn2.setText(allcity[1].toString());
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(allcity[1].toString());
            }
        });

        Button btn3 = (Button) findViewById(R.id.btn_city3);
        btn3.setText(allcity[2].toString());
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(allcity[2].toString());
            }
        });

        Button btn4 = (Button) findViewById(R.id.btn_city4);
        btn4.setText(allcity[3].toString());
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(allcity[3].toString());
            }
        });

        Button btn5 = (Button) findViewById(R.id.btn_city5);
        btn5.setText(allcity[4].toString());
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(allcity[4].toString());
            }
        });

        }

}
