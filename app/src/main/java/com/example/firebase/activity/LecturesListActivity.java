package com.example.firebase.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.firebase.R;
import com.example.firebase.adapter.LinkListAdapter;
import com.example.firebase.models.VideoList;

import java.util.ArrayList;
import java.util.List;

public class LecturesListActivity extends AppCompatActivity {

    private List<VideoList> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinkListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures_list);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Android Tutorials");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new LinkListAdapter(this,movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();

    }
    private void prepareMovieData() {
        VideoList movie = new VideoList("1.Lesson 1: for beginners in French", "V86Y2OGmb0o");
        movieList.add(movie);

        movie=new VideoList("2.Lesson 2: Do you know the French Alphabet?","zw8LXdFuTqQ");
        movieList.add(movie);
        movie=new VideoList("3.Lesson 3: French Pronunciation","62ZO5AeVpKw");
        movieList.add(movie);
        movie=new VideoList("4.Learn 545 French Basic Vocabs and Phrases","ZXvW6BvMo4o");
        movieList.add(movie);
        movie=new VideoList("5. Top 400 French Verbs For Everyday Life","_6au_o00-JE");
        movieList.add(movie);
        movie=new VideoList("6. Learn French in 1 Hour - ALL of Your Intermediate French Questions Answered!","72Ki8-7sae0");
        movieList.add(movie);
        movie=new VideoList("7.Efficient training of French listening - Intermediate Level","RMUv9EfYkl8");
        movieList.add(movie);
        movie=new VideoList("8.Learn French Pronunciation with Basic & Useful Phrases","6TCWvSRrnCk");
        movieList.add(movie);
        movie=new VideoList("9.French Pronunciation - Les Voyelles Nasales \"ON\" - \"EN\" - \"AIN\"- Free French Phonetic Lesson","j_Qs75dfpko");
        movieList.add(movie);
        movie=new VideoList("10.TOP 30 MUST-KNOW FRENCH QUESTIONS","1x5AifZ4-LE");
        movieList.add(movie);
        mAdapter.notifyDataSetChanged();
    }

}