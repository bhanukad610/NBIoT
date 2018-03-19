package com.gk.hasitha.slasscom;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.gk.hasitha.slasscom.activity.DrawerActivity;

import aluth.solutions.etwinkle.aluth.R;

import com.gk.hasitha.slasscom.fragment.CategoryFragment;
import com.gk.hasitha.slasscom.fragment.RecyclerViewFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends DrawerActivity {

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;

    private static int width;
    private static int height;
    public static String type = " ";

    private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        ButterKnife.bind(this);

        String sampleString = "Apple Banana Carrot";
        String[] animals = sampleString.split(" ");
        Log.d("show me",animals.toString());

        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {


        }else{

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return CategoryFragment.newInstance();
                    case 1:
                        return RecyclerViewFragment.newInstance();
                    case 2:
                        return RecyclerViewFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 3:
                        return "About us";
                    case 1:
                        return "Add-Ons";
                    case 2:
                        return "Settings";
                    case 0:
                        return "My IoT";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://ak8.picdn.net/shutterstock/videos/28080688/thumb/10.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiC-cq8s_LZAhVHOI8KHRikDuQQjRx6BAgAEAU&url=https%3A%2F%2Fidealog.co.nz%2Ftech%2F2016%2F07%2Finternet-things-goes-mainstream&psig=AOvVaw3o3XNVGXW0JI8nnRJpCRVK&ust=1521343147537246");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwj4gufKxfHZAhXJa7wKHU8mBMAQjRx6BAgAEAU&url=https%3A%2F%2Fwww.businesslive.co.za%2Fbd%2Fbusiness-and-economy%2F2016-11-02-digital-transformation-not-a-priority-for-sas-business-leaders-study-finds%2F&psig=AOvVaw3mH72guffhIvNLiplhn_s7&ust=1521313710203695");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                    getMessages();
                }
            });
        }
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        MainActivity.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        MainActivity.height = height;
    }

    public void getMessages(){
        String message = "";
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
                        Log.d("show",strbody);
                        message += " "+strbody+"\n";
                        long longDate = cur.getLong(index_Date);
                        int int_Type = cur.getInt(index_Type);

                        smsBuilder.append("[ ");
                        smsBuilder.append(strAddress + ", ");
                        smsBuilder.append(intPerson + ", ");
                        smsBuilder.append(strbody + ", ");
                        smsBuilder.append(longDate + ", ");
                        smsBuilder.append(int_Type);
                        smsBuilder.append(" ]\n\n");
                    } while (cur.moveToNext());

                    Log.d("show",message);

                    if (!cur.isClosed()) {
                        cur.close();
                        cur = null;
                    }
                } else {
                    smsBuilder.append("no result!");
                } // end if

            } catch (SQLiteException ex) {
                Log.d("SQLiteException", ex.getMessage());
            }
        }else{

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }
}
