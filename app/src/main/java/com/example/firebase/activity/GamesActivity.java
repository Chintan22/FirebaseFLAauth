package com.example.firebase.activity;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.example.firebase.R;

public class GamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Games");


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GamesActivity.this);
        alertDialogBuilder
                .setMessage("This feature will be added soon")
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
        Intent intent7=new Intent(GamesActivity.this,DashboardActivity.class);
        startActivity(intent7);
        finish();

    }
}