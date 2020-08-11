package com.example.firebase.activity;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.example.firebase.R;
import com.example.firebase.adapter.DictionaryListAdapter;
import com.example.firebase.models.Dictionary;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {

    private List<Dictionary> dictionaryArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DictionaryListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dictionary");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.dictionary_words);

        mAdapter = new DictionaryListAdapter(this, dictionaryArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareDictionaryData();


    }

    private void prepareDictionaryData() {

        Dictionary dictionary = new Dictionary("accompany");
        dictionaryArrayList.add(dictionary);

        dictionary = new Dictionary("accurate");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("accountant");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("baggage");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("bankrupt");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("dawn");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("earlobe");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("happy");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("knee");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("major");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("negligence");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("negligent");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("obsessive");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("painter");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("panic");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("raise");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("rarely");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("tax");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("teammate");
        dictionaryArrayList.add(dictionary);

    }
}