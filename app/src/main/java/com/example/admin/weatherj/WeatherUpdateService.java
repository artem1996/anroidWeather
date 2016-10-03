package com.example.admin.weatherj;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class WeatherUpdateService extends IntentService {

    WeatherStorage storage;

    public WeatherUpdateService() {
        super("WeatherUpdateService");
        storage = WeatherStorage.getInstance(this);
    }

    protected void sendWether(City city) {
        Intent intentServ = new Intent(MainActivity.BROADCAST_ACTION);
        Weather weat;
        try {
            weat = WeatherUtils.getInstance().loadWeather(city);
            storage.saveWeather(city, weat);
            intentServ.putExtra(MainActivity.PARAM_STATUS, true);
        } catch (IOException e) {
            intentServ.putExtra(MainActivity.PARAM_STATUS, false);
        } finally {
            sendBroadcast(intentServ);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            City city = City.valueOf(intent.getStringExtra("city"));
            sendWether(city);
        }
    }
}
