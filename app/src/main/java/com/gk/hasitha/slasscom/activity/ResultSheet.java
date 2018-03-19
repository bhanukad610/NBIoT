package com.gk.hasitha.slasscom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gk.hasitha.slasscom.MainActivity;

import aluth.solutions.etwinkle.aluth.R;

public class ResultSheet extends AppCompatActivity {

    private TextView score_text;
    private Button papersbtn;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_sheet);

        score_text = (TextView) findViewById(R.id.score_text);
        papersbtn = (Button) findViewById(R.id.papers_btn);
        Intent intent = getIntent();
        score = intent.getStringExtra("score");
        score_text.setText(score);


        papersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultSheet.this, MainActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultSheet.this,MainActivity.class));
    }
}
