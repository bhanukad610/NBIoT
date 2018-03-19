
package com.gk.hasitha.slasscom.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.gk.hasitha.slasscom.MainActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import aluth.solutions.etwinkle.aluth.R;

public class SmsActivity extends AppCompatActivity {

    private TextView sms;
    private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private ProgressDialog mProgressDialog;
    private int i;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        showProgressDialog();

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        sms = (TextView) findViewById(R.id.textSms);

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        getMessages();
                    }
                });

            }
        }, 2, 2, TimeUnit.SECONDS);

    }

    public void getMessages(){
        String message = "";
        String lastTemp = "";
        i=0;
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            StringBuilder smsBuilder = new StringBuilder();
            final String SMS_URI_INBOX = "content://sms/inbox";
            final String SMS_URI_ALL = "content://sms/";
            Log.d("Show","sa");
            try {
                Uri uri = Uri.parse(SMS_URI_INBOX);
                String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
                Cursor cur = getContentResolver().query(uri, projection, "address='+94716022116'", null, "date desc");
                if (cur.moveToFirst()) {
                    int index_Address = cur.getColumnIndex("address");
                    int index_Person = cur.getColumnIndex("person");
                    int index_Body = cur.getColumnIndex("body");
                    int index_Date = cur.getColumnIndex("date");
                    int index_Type = cur.getColumnIndex("type");
                    do {
                        String strAddress = cur.getString(index_Address);
                        int intPerson = cur.getInt(index_Person);
                        String strbody = cur.getString(index_Body);
                        String[] bodyList = strbody.split(" ");
                        if (i == 0){
                            try {
                                if (MainActivity.type.contains("humi")) {
                                    lastTemp = bodyList[0];
                                } else if (MainActivity.type.contains("temp")) {
                                    lastTemp = bodyList[1];
                                } else if (MainActivity.type.contains("mois")) {
                                    lastTemp = bodyList[2];
                                }
                            } catch (ArrayIndexOutOfBoundsException arr){
                                
                            }

                            i++;
                        }
                        message += " "+strbody+"\n";
                        long longDate = cur.getLong(index_Date);
                        int int_Type = cur.getInt(index_Type);
                        Log.d("show",String.valueOf(longDate));

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(longDate);
                        Date finaldate = calendar.getTime();
                        String smsDate = finaldate.toString();
                        Log.d("showw", smsDate);

                        smsBuilder.append("[ ");
                        smsBuilder.append(strAddress + ", ");
                        smsBuilder.append(intPerson + ", ");
                        smsBuilder.append(strbody + ", ");
                        smsBuilder.append(longDate + ", ");
                        smsBuilder.append(int_Type);
                        smsBuilder.append(" ]\n\n");
                    } while (cur.moveToNext());

                    Log.d("show",message);
                    sms.setText(lastTemp);

                    hideProgressDialog();

                    if (!cur.isClosed()) {
                        cur.close();
                        cur = null;
                    }
                } else {
                    smsBuilder.append("no result!");
                } // end if

            } catch (SQLiteException ex) {
                Log.d("SQLiteException", ex.getMessage());
                hideProgressDialog();
            }
        }else{
            hideProgressDialog();
            ActivityCompat.requestPermissions(SmsActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
