package com.example.firebase.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    public TextView tvUserName,tvEmail;
    public Button btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");
        tvEmail= (TextView) findViewById(R.id.tvEmail);
        btnShare= (Button) findViewById(R.id.btnShare);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("firebase_user",""+user.getEmail());
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Email");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, user.getEmail());

                /*Fire!*/
                startActivity(Intent.createChooser(intent, user.getEmail()));
            }
        });


        tvEmail.setText(user.getEmail());
    }
}