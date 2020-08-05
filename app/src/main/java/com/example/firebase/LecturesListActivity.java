package com.example.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
        VideoList movie = new VideoList("1.Introduction and Installing and Configuring Java", "PQqEKrr8KSQ");
        movieList.add(movie);

        movie=new VideoList("2.How to install Android Studio","SLNTnJkg6EE");
        movieList.add(movie);
        movie=new VideoList("3.Building Your First Android App (Hello World Example)","taSwS5rhtmc");
        movieList.add(movie);
        movie=new VideoList("4.Android Activity Lifecycle","odqACn2Vgic");
        movieList.add(movie);
        movie=new VideoList("5. Adding Two Numbers App (Simple Calculator)","7OQJIaXNmT4");
        movieList.add(movie);
        movie=new VideoList("6. Android ImageView example","IgbGeOIPu8w");
        movieList.add(movie);
        movie=new VideoList("7.Android WebView Example","nB-relROsrY");
        movieList.add(movie);
        movie=new VideoList("8.Fragments in Android","mcF28h9WiGQ");
        movieList.add(movie);
        movie=new VideoList("9.Android Studio Tutorial For Beginners","ZLNO2c7nqjw");
        movieList.add(movie);
        movie=new VideoList("10.Android App Design Tutorial Using Relative Layout and Linear Layout With Examples","dAGxG5SamX4");
        movieList.add(movie);
        mAdapter.notifyDataSetChanged();
    }

}