package com.gk.hasitha.slasscom.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gk.hasitha.slasscom.adapter.PaperAdapter;
import com.gk.hasitha.slasscom.data.MyDataOne;
import com.gk.hasitha.slasscom.data.PapersDataGetter;

import java.util.ArrayList;
import java.util.List;

import aluth.solutions.etwinkle.aluth.R;

public class CategoryActivity extends AppCompatActivity {

    private List<MyDataOne> dataOneList;
    private List<PapersDataGetter> paperList;
    private PaperAdapter paperAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String cat;
    private String paperNameCheck;

    private PullRefreshLayout pullRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Log.d("paperName","Here2");
        goOnPapers();

        ///Ads
        ///

        Intent intent = getIntent();
        cat = intent.getStringExtra("cat");
        paperNameCheck = intent.getStringExtra("paperName");

//        getSupportActionBar().setTitle(cat);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goOnPapers();
                pullRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout.setRefreshing(false);
                    }
                },3000);

            }

        });

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public void checkUp(){
        if (isNetworkAvailable()){

        }else{
            //startActivity(new Intent(CategoryFilter.this, NoConnection.class));
            Toast.makeText(this,"No Connection",Toast.LENGTH_SHORT);
        }
    }

    public void goOnPapers() {

        checkUp();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //dataOneList = new ArrayList<>();
        paperList = new ArrayList<>();
        //load_data_from_server(0);
        get_firebase_data_papers();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //mSwipeRefreshLayout.setVisibility(View.VISIBLE);

        //quezAdapter = new QuezAdapter(this, dataOneList);
        paperAdapter = new PaperAdapter(this, paperList,dataOneList, "Cat_Filter");
        recyclerView.setAdapter(paperAdapter);

    }

    public void get_firebase_data_papers(){

        AsyncTask<Integer,Void,Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                Firebase.setAndroidContext(CategoryActivity.this);
                Log.d("paperName","Here");
                Firebase ref = new Firebase("https://fire-base-7922a.firebaseio.com/papers");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            String id = (String) messageSnapshot.child("id").getValue();
                            String paperID = (String) messageSnapshot.child("paperID").getValue();
                            String paperName = (String) messageSnapshot.child("paperName").getValue();
                            Log.d("paperName",paperName);
                            String paperRealName = (String) messageSnapshot.child("paperRealName").getValue();
                            PapersDataGetter papersDataGetter = new PapersDataGetter(id,paperID,paperName,paperRealName);
                            if (paperRealName.contains(paperNameCheck)) {
                                paperList.add(papersDataGetter);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.d("paperName","Here3");
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                paperAdapter.notifyDataSetChanged();
                paperAdapter.updateData(paperList);
            }
        };

        asyncTask.execute(0);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}