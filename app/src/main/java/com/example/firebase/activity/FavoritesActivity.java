package com.example.firebase.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.firebase.R;
import com.example.firebase.adapter.DictionaryListAdapter;
import com.example.firebase.adapter.FavoriteListAdapter;
import com.example.firebase.models.Dictionary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {



    private List<Dictionary> dictionaryArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FavoriteListAdapter mAdapter;

    DatabaseReference databaseArtists;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dictionary");
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();

        databaseArtists = FirebaseDatabase.getInstance().getReference("favorites").child(auth.getCurrentUser().getUid());

        recyclerView = (RecyclerView) findViewById(R.id.favortie_words);



    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                dictionaryArrayList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Dictionary dictionary = postSnapshot.getValue(Dictionary.class);
                    //adding artist to the list
                    dictionaryArrayList.add(dictionary);

                    mAdapter = new FavoriteListAdapter(FavoritesActivity.this, dictionaryArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}