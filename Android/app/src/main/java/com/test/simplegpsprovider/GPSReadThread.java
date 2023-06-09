package com.test.simplegpsprovider;

import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class GPSReadThread extends Thread {

    private final EditText text;
    private final Socket socket;

    private final NmeaParser nmeaParser;

    public GPSReadThread(Socket socket, NmeaParser nmeaParser, EditText text) {
        this.socket = socket;
        this.nmeaParser = nmeaParser;
        this.text = text;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String data = reader.readLine();
                if (data != null && !data.isEmpty()) {
                    nmeaParser.parseNmeaSentence(data);
                    sleep(10);
                }
            }
            /*String serialData;
            while ((serialData = reader.readLine()) != null) {
                if (!serialData.isEmpty()) {
                    text.append(serialData);
                    text.append("\n");
                }
            }

            /* Parse the received serial data to extract GPS information (latitude, longitude, etc.)
            double latitude = 0.0;
            double longitude = 0.0;

            // Example parsing code for NMEA sentence
            String[] parts = serialData.split(",");
            if (parts.length >= 4 && parts[0].equals("$GPGGA")) {
                try {
                    latitude = Double.parseDouble(parts[2]);
                    longitude = Double.parseDouble(parts[4]);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error parsing latitude or longitude: " + e.getMessage());
                }
            }

            // Create a Location object from the parsed latitude and longitude
            Location location = new Location("SerialGPSProvider");
            location.setLatitude(latitude);
            location.setLongitude(longitude);/
            }*/
        } catch (Exception e) {
            text.setText("Error read: " + e + " " + e.getMessage() + "\n");
        }
    }
}
