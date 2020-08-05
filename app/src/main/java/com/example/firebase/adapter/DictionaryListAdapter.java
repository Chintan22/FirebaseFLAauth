package com.example.firebase.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.firebase.R;
import com.example.firebase.models.Dictionary;
import com.example.firebase.models.VideoList;

import java.util.List;

public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.MyViewHolder> {

    private List<Dictionary> dictionariesList;
    public Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvWord);

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Dictionary movie = dictionariesList.get(position);
        holder.title.setText(movie.getWordName());


    }

    @Override
    public int getItemCount() {
        return dictionariesList.size();
    }
}
