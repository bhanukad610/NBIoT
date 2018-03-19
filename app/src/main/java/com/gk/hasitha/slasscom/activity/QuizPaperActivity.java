package com.gk.hasitha.slasscom.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import aluth.solutions.etwinkle.aluth.R;

public class QuizPaperActivity extends AppCompatActivity {

    private TextView question_paper,textpaper,id_paper,scoreText,correct_answer,invisibleID, correct_answer_check, you_are_correct;
    private RadioButton choice1,choice2,choice3,choice4,radioButton;
    private RadioGroup radioGroup;
    private String user_input,answer;
    private Button nextbtn, previousbtn, checkbtn;
    private int position = 0;
    private String paperID,OnGoingID;
    private Integer score = 0;
    private String status = "Next";
    private String previous_answer, next_answer;
    private Firebase choice1Fire,choice2Fire,choice3Fire,choice4Fire,question_paperFire,id_paperFire,answer_Fire,previous_answerFire, next_answerFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_paper);
        Firebase.setAndroidContext(this);
        OnClickListnerButton();
        position = 0;

        Intent intent = getIntent();
        paperID = intent.getStringExtra("id");

        question_paper = (TextView) findViewById(R.id.question_paper);
        id_paper = (TextView) findViewById(R.id.id_paper);
        choice1 = (RadioButton) findViewById(R.id.choice1);
        choice2 = (RadioButton) findViewById(R.id.choice2);
        choice3 = (RadioButton) findViewById(R.id.choice3);
        choice4 = (RadioButton) findViewById(R.id.choice4);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        nextbtn = (Button) findViewById(R.id.next_btn);
        previousbtn = (Button) findViewById(R.id.previous_btn);
        checkbtn = (Button) findViewById(R.id.check_btn);
        textpaper = (TextView) findViewById(R.id.textpaper);
        scoreText = (TextView) findViewById(R.id.Score);
        correct_answer  =(TextView) findViewById(R.id.correct_answer);
        invisibleID = (TextView) findViewById(R.id.invisibleID);
        correct_answer_check = (TextView) findViewById(R.id.correct_answer_check);
        you_are_correct = (TextView) findViewById(R.id.you_are_correct);
        //question_paper.setText(myDataOnes_list.get(0).getQuestion().toString());


        textpaper.setVisibility(View.VISIBLE);
        textpaper.setText("ප්\u200Dරශ්න පත්\u200Dර අංක: "+paperID);
        id_paper.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        question_paper.setVisibility(View.GONE);
        correct_answer.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);

    }

    private void updateQuiz(){
        if (position>0){
            previousbtn.setVisibility(View.VISIBLE);
        }
        OnGoingID = "";
        radioGroup.setVisibility(View.VISIBLE);
        id_paper.setVisibility(View.VISIBLE);
        question_paper.setVisibility(View.VISIBLE);
        textpaper.setVisibility(View.INVISIBLE);
        nextbtn.setText("මීළඟ ප්\u200Dරශ්නය");
        scoreText.setVisibility(View.VISIBLE);
        checkbtn.setVisibility(View.VISIBLE);


        question_paperFire = new Firebase("https://generalknowledge-6bb86.firebaseio.com/questions/" +paperID+"/"+ position + "/question");
        question_paperFire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                question_paper.setText(question);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        choice1Fire = new Firebase("https://generalknowledge-6bb86.firebaseio.com/questions/" +paperID+"/"+ position + "/choice1");
        choice1Fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String choice1F = dataSnapshot.getValue(String.class);
                choice1.setText(choice1F);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        choice2Fire = new Firebase("https://generalknowledge-6bb86.firebaseio.com/questions/" +paperID+"/"+ position + "/choice2");
        choice2Fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String choice1F = dataSnapshot.getValue(String.class);
                choice2.setText(choice1F);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        choice3Fire = new Firebase("https://generalknowledge-6bb86.firebaseio.com/questions/" +paperID+"/"+ position + "/choice3");
        choice3Fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String choice1F = dataSnapshot.getValue(String.class);
                choice3.setText(choice1F);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        choice4Fire = new Firebase("https://generalknowledge-6bb86.firebaseio.com/questions/" +paperID+"/"+ position + "/choice4");
        choice4Fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String choice1F = dataSnapshot.getValue(String.class);
                choice4.setText(choice1F);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        answer_Fire = new Firebase("https://generalknowledge-6bb86.firebaseio.com/questions/" + paperID+"/"+position + "/answer");
        answer_Fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                answer = question;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        previous_answerFire = new Firebase("https://generalknowledge-6bb86.firebaseio.com/questions/" +paperID+"/"+ (position-1) + "/answer");
        previous_answerFire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                previous_answer = question;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        next_answerFire = new Firebase("https://generalknowledge-6bb86.firebaseio.com/questions/" +paperID+"/"+ (position+1) + "/answer");
        next_answerFire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                next_answer = question;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        scoreText.setText("ලකුණු "+score);
        id_paper.setText("" + (position+1));
        position++;
        correct_answer.setText("නිවැරදි පිළිතුර: "+previous_answer);
        correct_answer_check.setText("නිවැරදි පිළිතුර: "+next_answer);
/**
        Toast.makeText(QuizPaperActivity.this,myDataOnes_list.get(position).getQuestion().toString(),Toast.LENGTH_SHORT).show();

        id_paper.setText(position+1);
        question_paper.setText(myDataOnes_list.get(position).getQuestion().toString());
        choice1.setText(myDataOnes_list.get(position).getChoice1().toString());
        choice2.setText(myDataOnes_list.get(position).getChoice2().toString());
        choice3.setText(myDataOnes_list.get(position).getChoice3().toString());
        choice4.setText(myDataOnes_list.get(position).getChoice4().toString());

 **/
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizPaperActivity.this, R.style.MyAlertDialogStyle);
        builder.setTitle("");
        builder.setMessage("ඉවත් වීමට අවශ්\u200Dයම ද?");
        builder.setPositiveButton("ඔව්", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("නැත", null);
        builder.show();
    }

    private void OnClickListnerButton(){

        correct_answer = (TextView) findViewById(R.id.correct_answer);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        nextbtn = (Button) findViewById(R.id.next_btn);
        previousbtn = (Button) findViewById(R.id.previous_btn);
        checkbtn = (Button) findViewById(R.id.check_btn);
        invisibleID = (TextView) findViewById(R.id.invisibleID);
        correct_answer_check = (TextView) findViewById(R.id.correct_answer_check);
        you_are_correct = (TextView) findViewById(R.id.you_are_correct);

        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct_answer.setVisibility(View.VISIBLE);
                status = "Back";
                position--;
                position--;
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioGroup.getChildAt(i).setEnabled(false);
                }
                updateQuiz();
                previousbtn.setVisibility(View.INVISIBLE);
                correct_answer_check.setVisibility(View.INVISIBLE);
                you_are_correct.setVisibility(View.INVISIBLE);
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position == 20 ){
                    Intent intent2 = new Intent(QuizPaperActivity.this, ResultSheet.class);
                    intent2.putExtra("score",score.toString());
                    startActivity(intent2);
                }

                correct_answer.setVisibility(View.INVISIBLE);
                correct_answer_check.setVisibility(View.INVISIBLE);
                you_are_correct.setVisibility(View.INVISIBLE);
                int selected_id = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selected_id);
                if(position >0) {
                    if(status == "Next") {
                        try {
                            user_input = radioButton.getText().toString();
                            if (answer.contains(user_input)) {
                                Toast.makeText(QuizPaperActivity.this, "නිවැරදියි", Toast.LENGTH_SHORT).show();
                                score++;
                            } else {
                                Toast.makeText(QuizPaperActivity.this, "වැරදියි", Toast.LENGTH_SHORT).show();
                            }
                            updateQuiz();
                        } catch (NullPointerException e) {
                            Toast.makeText(QuizPaperActivity.this, "පිළිතුරක් තෝරන්න", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        updateQuiz();
                    }
                }else{
                    updateQuiz();
                }
                status = "Next";
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioGroup.getChildAt(i).setEnabled(true);
                }
                radioGroup.clearCheck();
            }
        });

        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct_answer.setVisibility(View.INVISIBLE);
                int selected_id = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selected_id);
                try {
                    user_input = radioButton.getText().toString();
                    if (answer.contains(user_input)) {
                        you_are_correct.setVisibility(View.VISIBLE);
                    } else {
                        correct_answer_check.setVisibility(View.VISIBLE);
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(QuizPaperActivity.this, "පිළිතුරක් තෝරන්න", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioGroup.getChildAt(i).setEnabled(false);
                }
            }
        });

    }

}
