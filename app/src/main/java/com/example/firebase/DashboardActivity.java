package com.example.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public TextView tvUserName,tvEmail;
    public Button btn_quiz,btn_lectures,btn_dictionary,btn_favorites,btn_chatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvUserName= (TextView) findViewById(R.id.tvUserName);
        tvEmail= (TextView) findViewById(R.id.tvEmail);

        btn_quiz= (Button) findViewById(R.id.btn_quiz);
        btn_lectures= (Button) findViewById(R.id.btn_lectures);
        btn_dictionary= (Button) findViewById(R.id.btn_dictionary);
        btn_favorites= (Button) findViewById(R.id.btn_games);
        btn_chatbot= (Button) findViewById(R.id.btn_chantbot);
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("firebase_user",""+user.getEmail());

        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DashboardActivity.this,QuizActivity.class);
                startActivity(intent1);

            }
        });

        btn_lectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,LecturesListActivity.class);
                startActivity(intent);

            }
        });

        btn_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(DashboardActivity.this,GamesActivity.class);
                startActivity(intent2);

            }
        });
        btn_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(DashboardActivity.this,DictionaryActivity.class);
                startActivity(intent3);

            }
        });
        btn_chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5=new Intent(DashboardActivity.this,ChatbotActivity.class);
                startActivity(intent5);

            }
        });

        tvUserName.setText(user.getDisplayName());
        tvEmail.setText(user.getEmail());
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d("firebase_user",""+user.getEmail());
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView textView= (TextView) hView.findViewById(R.id.tvEmail);
        textView.setText(user.getEmail());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_profile:

                        Intent intent5=new Intent(DashboardActivity.this,ProfileActivity.class);
                        startActivity(intent5);
                        finish();
                        break;

                    case R.id.nav_logout:

                        Intent intent7=new Intent(DashboardActivity.this,MainActivity.class);
                        startActivity(intent7);
                        finish();
                        break;

                    case R.id.nav_share :
                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getApplicationContext().getResources().getString(R.string.app_name));
                            String shareMessage= "\nLet me recommend you this application\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                        } catch(Exception e) {
                            //e.toString();
                        }
                    default:
                        return true;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;

            }
        });

    }
}