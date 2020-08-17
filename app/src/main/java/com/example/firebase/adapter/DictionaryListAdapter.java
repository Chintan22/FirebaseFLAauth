package com.example.firebase.adapter;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.firebase.R;
import com.example.firebase.activity.FavoritesActivity;
import com.example.firebase.models.Dictionary;
import com.example.firebase.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.MyViewHolder> {

    private List<Dictionary> dictionariesList;
    public Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public  boolean buttonOn;
    public FirebaseAuth firebaseAuth;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icLike;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvWord);
            icLike=view.findViewById(R.id.ic_like);

        }
    }


    public DictionaryListAdapter(Context context, List<Dictionary> dictionariesList) {
        this.dictionariesList = dictionariesList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dictionary, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        SharedPreferences sharedpreferences = context.getSharedPreferences("quizdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String uid=firebaseAuth.getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference("favorites").child(uid);
        final String userId = databaseReference.push().getKey();
        final Dictionary movie = dictionariesList.get(position);
        holder.title.setText(movie.getWordName());
        buttonOn=true;
        holder.icLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonOn) {
                    buttonOn = true;
                    holder.icLike.setBackgroundResource(R.drawable.ic_heart);
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("favorites").child(movie.getId());


                    databaseReference.removeValue();


                } else {
                    buttonOn = false;
                    holder.icLike.setBackgroundResource(R.drawable.ic_heart_fill);
                    Dictionary dictionary = new Dictionary(userId,movie.getWordName());
                    if (dictionary!=null) {
                        assert userId != null;
                        databaseReference.child(userId).setValue(dictionary);
                        context.startActivity(new Intent(context, FavoritesActivity.class));
                        ((AppCompatActivity)context).finish();
                    }

                }




            }
        });

    }

    @Override
    public int getItemCount() {
        return dictionariesList.size();
    }

    public void setFilter(List<Dictionary> newList){
        dictionariesList=new ArrayList<>();
        dictionariesList.addAll(newList);
        notifyDataSetChanged();
    }
}
