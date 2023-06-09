package com.test.simplegpsprovider;

import android.os.Looper;
import android.widget.EditText;

import java.net.ServerSocket;

public class ServerThread extends Thread {

    private NmeaParser nmeaParser;
    private final EditText text;

    public ServerThread(NmeaParser nmeaParser, EditText text) {
        this.nmeaParser = nmeaParser;
        this.text = text;
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(5897)) {
            while (true) {
                Looper.prepare();
                new GPSReadThread(server.accept(), nmeaParser, text).start();
                text.append("Connected\n");
                Looper.loop();
            }
        } catch (Exception e) {
            text.setText("Error read: " + e + " " + e.getMessage() + "\n");
        }
    }
}
