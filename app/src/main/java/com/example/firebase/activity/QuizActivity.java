package com.example.firebase.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase.R;
import com.example.firebase.models.Question;

import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_one, btn_two, btn_three, btn_four;
    TextView tv_question;

    private Question question = new Question();

    private String answer;
    private int questionLength = question.questions.length;

    Random random;
    SharedPreferences sharedpreferences;
    Integer points=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        random = new Random();

        sharedpreferences = getSharedPreferences("quizdata", Context.MODE_PRIVATE);
        points=sharedpreferences.getInt("points",0);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_one.setOnClickListener(this);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_two.setOnClickListener(this);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_three.setOnClickListener(this);
        btn_four = (Button) findViewById(R.id.btn_four);
        btn_four.setOnClickListener(this);

        tv_question = (TextView) findViewById(R.id.tv_question);

        NextQuestion(random.nextInt(questionLength));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                if (btn_one.getText() == answer) {
                    Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    points=points+1;
                    editor.putInt("points", points);
                    editor.commit();
                    NextQuestion(random.nextInt(questionLength));

                } else {
                    GameOver();
                }

                break;

            case R.id.btn_two:
                if (btn_two.getText() == answer) {
                    Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                    NextQuestion(random.nextInt(questionLength));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    points=points+1;
                    editor.putInt("points", points);
                    editor.commit();
                } else {
                    GameOver();
                }

                break;

            case R.id.btn_three:
                if (btn_three.getText() == answer) {
                    Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    points=points+1;
                    editor.putInt("points", points);
                    editor.commit();
                    NextQuestion(random.nextInt(questionLength));
                } else {
                    GameOver();
                }

                break;

            case R.id.btn_four:
                if (btn_four.getText() == answer) {
                    Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    points=points+1;
                    editor.putInt("points", points);
                    editor.commit();
                    NextQuestion(random.nextInt(questionLength));
                } else {
                    GameOver();
                }

                break;
        }
    }

    private void GameOver() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setTitle("Quiz Over")
                .setMessage("Your Points: "+points.toString())
                .setCancelable(false)
                .setPositiveButton("New Quiz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                        points=0;
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("points", points);
                        editor.commit();
                    }
                }).setNeutralButton("Share Points", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Your Achivement Points");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "Your Achivement Points is :"+points.toString());
                /*Fire!*/
                startActivity(Intent.createChooser(intent, "Your Achivement Points is "));
            }
        })
        ;
        alertDialogBuilder.show();

    }

    private void NextQuestion(int num) {
        tv_question.setText(question.getQuestion(num));
        btn_one.setText(question.getchoice1(num));
        btn_two.setText(question.getchoice2(num));
        btn_three.setText(question.getchoice3(num));
        btn_four.setText(question.getchoice4(num));

        answer = question.getCorrectAnswer(num);
    }
}