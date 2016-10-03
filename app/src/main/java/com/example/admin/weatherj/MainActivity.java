package com.example.admin.weatherj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class MainActivity extends AppCompatActivity {
    public final static String BROADCAST_ACTION = "com.example.admin.weatherj.putWether";
    public final static String PARAM_STATUS = "status";
    private final static String TAG = MainActivity.class.getSimpleName();

    City city;
    WeatherStorage storage;
    TextView wetherInfo;

    private void startCityChangeActivity() {
        Intent intent = new Intent(this, CityChangeActivity.class);
        startActivity(intent);
    }

    private BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = WeatherStorage.getInstance(this);
        Button changeCityActivity = (Button) findViewById(R.id.btn_city);
        wetherInfo = (TextView) findViewById(R.id.wether_info);
        final CheckBox cb_autoUpdate = (CheckBox) findViewById(R.id.checkbox_update);


        if(getIntent().hasExtra("city")) {
            city = City.valueOf(getIntent().getStringExtra("city"));
            storage.setCurrentCity(city);
        }

        Weather tempWeather = storage.getLastSavedWeather(storage.getCurrentCity());

        if(tempWeather != null && (city == null || city == storage.getCurrentCity())) {
            city = storage.getCurrentCity();
            changeCityActivity.setText(city.toString());
            cb_autoUpdate.setEnabled(true);
            wetherInfo.setText("" + tempWeather.getTemperature() + "C\n" + tempWeather.getDescription());
        } else if(city != null) {
            changeCityActivity.setText(city.toString());
            cb_autoUpdate.setEnabled(true);
        }

        changeCityActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCityChangeActivity();
            }
        });

        cb_autoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WeatherUpdateService.class);
                intent.putExtra("city", city.toString());
                if(cb_autoUpdate.isChecked()) {
                    WeatherUtils.getInstance().schedule(MainActivity.this, intent);
                } else {
                    WeatherUtils.getInstance().unschedule(MainActivity.this, intent);
                }
            }
        });

        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if(intent.getBooleanExtra(PARAM_STATUS, false) && city != null) {
                    Weather tempWeather = storage.getLastSavedWeather(city);
                    wetherInfo.setText("" + tempWeather.getTemperature() + "C\n" + tempWeather.getDescription());
                }

            }
        };
        registerReceiver(br, new IntentFilter(BROADCAST_ACTION));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(city != null) {
            Intent intent = new Intent(this, WeatherUpdateService.class);
            intent.putExtra("city", city.toString());
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
}
