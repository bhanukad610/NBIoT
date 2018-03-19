package com.gk.hasitha.slasscom.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import aluth.solutions.etwinkle.aluth.R;

public class WaterTapActivity extends AppCompatActivity {

    private Button onButton;
    private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tap);

        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.SEND_SMS") == PackageManager.PERMISSION_GRANTED) {
        }else{
            ActivityCompat.requestPermissions(WaterTapActivity.this, new String[]{"android.permission.SEND_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        onButton = (Button) findViewById(R.id.onButton);

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.SEND_SMS") == PackageManager.PERMISSION_GRANTED) {
                    sendSMS("+94713858259","1");
                }else{
                    ActivityCompat.requestPermissions(WaterTapActivity.this, new String[]{"android.permission.SEND_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
                }
            }
        });

    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
