package com.example.firebase.activity;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebase.R;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public TextView tvUserName,tvEmail;
    public ImageView imageView;
    public MaterialButton btn_quiz,btn_lectures,btn_dictionary,btn_games,btn_chatbot,btn_favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();



        tvUserName= (TextView) findViewById(R.id.tvUserName);
        tvEmail= (TextView) findViewById(R.id.tvEmail);

        btn_favorites=findViewById(R.id.btn_favorites);
        btn_quiz= findViewById(R.id.btn_quiz);
        imageView=findViewById(R.id.profile);
        btn_lectures= findViewById(R.id.btn_lectures);
        btn_dictionary=  findViewById(R.id.btn_dictionary);
        btn_games= findViewById(R.id.btn_games);
        btn_chatbot=  findViewById(R.id.btn_chantbot);
        auth = FirebaseAuth.getInstance();



        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (auth.getCurrentUser() != null) {
                    Intent intent1=new Intent(DashboardActivity.this,QuizActivity.class);
                    startActivity(intent1);
                }
                else {
                    startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                    finish();
                }

            }
        });

        btn_lectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(DashboardActivity.this,LecturesListActivity.class);
                startActivity(intent1);

            }
        });


        btn_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(DashboardActivity.this,NewDictionaryActivity.class);
//                Intent intent1=new Intent(DashboardActivity.this,DictionaryActivity.class);
                startActivity(intent1);

            }
        });
        btn_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    Intent intent1=new Intent(DashboardActivity.this,FavoritesActivity.class);
                    startActivity(intent1);
                }
                else {
                    startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                    finish();
                }
            }
        });
        btn_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    Intent intent1=new Intent(DashboardActivity.this,GamesActivity.class);
                    startActivity(intent1);
                }
                else {
                    startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                    finish();
                }
            }
        });
        btn_chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Intent intent5 = new Intent(DashboardActivity.this, NewChatBotActivity.class);
                    if(user.getEmail().split("@")[0].toLowerCase().contains("customercare")){
                        intent5 = new Intent(DashboardActivity.this, CustomerCareChatActivity.class);
                    }
                    startActivity(intent5);
                }
                else {
                    startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                    finish();
                }
            }
        });
//get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            tvEmail.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            tvUserName.setText(user.getDisplayName());
            tvEmail.setText(user.getEmail());
        }
        else {
            tvEmail.setVisibility(View.GONE);
            tvUserName.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);

        }
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
        if (user!=null) {
            textView.setText(user.getEmail());
        }
        else
        {
            textView.setText("Guest User");
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_profile:
                        if (user!=null) {
                            Intent intent5 = new Intent(DashboardActivity.this, ProfileActivity.class);
                            startActivity(intent5);
                            finish();
                        }
                        else {
                            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                            finish();
                        }
                        break;

                    case R.id.nav_logout:
                        if (user!=null) {
                            Intent intent7 = new Intent(DashboardActivity.this, MainActivity.class);
                            startActivity(intent7);
                            finish();
                        }
                        else {
                            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                            finish();
                        }
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