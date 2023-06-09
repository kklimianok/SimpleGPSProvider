package com.test.simplegpsprovider;

import static android.location.provider.ProviderProperties.ACCURACY_FINE;
import static android.location.provider.ProviderProperties.POWER_USAGE_LOW;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import androidx.activity.ComponentActivity;

import com.test.simplegpsprovider.NmeaParser;

public class MainActivity extends ComponentActivity {

    private LocationManager locationManager;

    private NmeaParser nmeaParser;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainHandler = new Handler(getMainLooper());
        EditText text = new EditText(this);
        setContentView(text);
        nmeaParser = new NmeaParser(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.addTestProvider("SimpleGPSProvider", false, false, false, false, true, true, true, POWER_USAGE_LOW, ACCURACY_FINE);
        locationManager.setTestProviderEnabled("SimpleGPSProvider", true);
        new ServerThread(nmeaParser, text).start();
    }

    public void notifyNewLocation(final Location location) {
        mainHandler.post(() -> locationManager.setTestProviderLocation("SimpleGPSProvider", location));
    }
}