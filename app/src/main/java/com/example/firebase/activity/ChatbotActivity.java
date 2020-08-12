package com.example.firebase.activity;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.example.firebase.R;

public class ChatbotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChatbotActivity.this);
        alertDialogBuilder
                .setMessage("Work in progress")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent7=new Intent(ChatbotActivity.this,DashboardActivity.class);
        startActivity(intent7);
        finish();

    }
}