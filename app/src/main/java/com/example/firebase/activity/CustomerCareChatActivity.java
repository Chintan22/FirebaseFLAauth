package com.example.firebase.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CustomerCareChatActivity extends AppCompatActivity {

    private TextView tvNoChatFound;
    private RecyclerView rvChatList;
    private ChatHistoryListAdapter chatHistoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.chat_history_list_layout);
        initialiseControls();
        rvChatList.setLayoutManager(new LinearLayoutManager(CustomerCareChatActivity.this, LinearLayoutManager.VERTICAL, false));
        chatHistoryListAdapter = new ChatHistoryListAdapter(CustomerCareChatActivity.this, new ArrayList<String>());
        rvChatList.setAdapter(chatHistoryListAdapter);
        getData();
    }

    private void initialiseControls(){
        rvChatList              = findViewById(R.id.rvChatList);
        tvNoChatFound           = findViewById(R.id.tvNoChatFound);
    }

    public void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("Chat");
//        showLoader();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                hideLoader();
                if(snapshot!=null){
                    Log.e("Chat Count " ,""+snapshot.getChildrenCount());
                    HashMap hashMap = (HashMap) snapshot.getValue();
                    ArrayList<String> chatUsersList = new ArrayList<>();
                    if(hashMap!=null && hashMap.size() > 0) {
                        ArrayList<String> chatWithLlist = new ArrayList<String>(hashMap.keySet());
                        for (int i=0;i<chatWithLlist.size();i++){
                            HashMap hashM = (HashMap) hashMap.get(chatWithLlist.get(i));

                            ArrayList<String> chatLlist = new ArrayList<String>(hashM.keySet());
                            for (int j=0;j<chatLlist.size();j++) {
                                String userId = ((HashMap)hashM.get(chatLlist.get(j))).get("fromUserId").toString();
                                if(!chatUsersList.contains(userId)){
                                    chatUsersList.add(userId);
                                }
                            }

                        }
                        if(chatUsersList!=null && chatUsersList.size() > 0){
                            Collections.sort(chatUsersList);
                            chatHistoryListAdapter.refreshAdapter(chatUsersList);
                            rvChatList.post(new Runnable() {
                                @Override
                                public void run() {
                                    rvChatList.smoothScrollToPosition(chatHistoryListAdapter.getItemCount()- 1);
                                }
                            });

                            rvChatList.setAdapter(chatHistoryListAdapter = new ChatHistoryListAdapter(CustomerCareChatActivity.this, chatUsersList));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                hideLoader();
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

    }

    private class ChatHistoryListAdapter extends RecyclerView.Adapter<ChatHolder>{

        private Context context;
        private ArrayList<String> loginDos;

        public ChatHistoryListAdapter(Context context, ArrayList<String> loginDos){
            this.context = context;
            this.loginDos = loginDos;
        }

        @NonNull
        @Override
        public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(context).inflate(R.layout.chat_history_item_cell, parent, false);
            return new ChatHolder(convertView);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatHolder holder, final int position) {
//            holder.tvUser.setText(""+loginDos.get(position).firstName+" "+loginDos.get(position).lastName);
            holder.tvUser.setText(""+loginDos.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NewChatBotActivity.class);
                    intent.putExtra("ChatWith", loginDos.get(position).split("@")[0]);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return loginDos!=null ? loginDos.size() : 0;
        }

        public void refreshAdapter(ArrayList<String> chatUsersList) {
            loginDos = chatUsersList;
            notifyDataSetChanged();
        }
    }

    private class ChatHolder extends RecyclerView.ViewHolder{

        private TextView tvUser;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            tvUser              = itemView.findViewById(R.id.tvUser);
        }
    }
}
