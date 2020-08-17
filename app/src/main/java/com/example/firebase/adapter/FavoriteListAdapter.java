package com.example.firebase.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.R;
import com.example.firebase.models.Dictionary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.MyViewHolder> {

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


    public FavoriteListAdapter(Context context, List<Dictionary> dictionariesList) {
        this.dictionariesList = dictionariesList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorites, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Dictionary movie = dictionariesList.get(position);
        Log.d("listname",""+movie.getWordName());
        holder.title.setText(movie.getWordName());
               holder.icLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("favorites").child(firebaseAuth.getCurrentUser().getUid()).child(movie.getId());
                    databaseReference.removeValue();
                    dictionariesList.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();

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
