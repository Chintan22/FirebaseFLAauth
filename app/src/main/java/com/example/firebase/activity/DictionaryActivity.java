package com.example.firebase.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

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

        Dictionary dictionary = new Dictionary("","accompany");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","accurate");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","accountant");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","baggage");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","bankrupt");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","dawn");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","earlobe");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","happy");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","knee");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","major");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","negligence");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","negligent");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","obsessive");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","painter");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","panic");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","raise");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","rarely");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","tax");
        dictionaryArrayList.add(dictionary);
        dictionary = new Dictionary("","teammate");
        dictionaryArrayList.add(dictionary);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.option_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //loadHistory(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Dictionary> newList = new ArrayList<>();
                for (Dictionary productDetailPojo : dictionaryArrayList) {
                    String name = productDetailPojo.getWordName().toLowerCase();

                    if (name.contains(newText))
                        newList.add(productDetailPojo);
                }
                mAdapter.setFilter(newList);
                return true;
            }


        });

        return true;
    }




}