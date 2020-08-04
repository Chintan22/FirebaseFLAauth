package com.example.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public TextView tvUserName,tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvUserName= (TextView) findViewById(R.id.tvUserName);
        tvEmail= (TextView) findViewById(R.id.tvEmail);


        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("firebase_user",""+user.getEmail());

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_lectres:

                        Intent intent=new Intent(DashboardActivity.this,LecturesListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_quiz:

                        Intent intent1=new Intent(DashboardActivity.this,QuizActivity.class);
                        startActivity(intent1);

                        break;
                    case R.id.nav_games:

                        Intent intent2=new Intent(DashboardActivity.this,GamesActivity.class);
                        startActivity(intent2);

                        break;
                    case R.id.nav_dictionary:

                        Intent intent3=new Intent(DashboardActivity.this,DictionaryActivity.class);
                        startActivity(intent3);

                        break;
/*                    case R.id.nav_favorites:

                        Intent intent5=new Intent(DashboardActivity.this,FavoritesActivity.class);
                        startActivity(intent5);

                        break;*/
                    case R.id.nav_chatbot:

                        Intent intent6=new Intent(DashboardActivity.this,ChatbotActivity.class);
                        startActivity(intent6);

                        break;
                    case R.id.nav_logout:

                        Intent intent7=new Intent(DashboardActivity.this,MainActivity.class);
                        startActivity(intent7);

                        break;
                    default:
                        return true;
                }


                return true;

            }
        });

    }
}