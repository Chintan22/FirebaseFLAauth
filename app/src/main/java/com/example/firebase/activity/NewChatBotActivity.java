package com.example.firebase.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.R;
import com.example.firebase.models.ChatMessageDo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class NewChatBotActivity extends AppCompatActivity {

    private EditText etMessage;
    private ImageView ivSend;
    private RecyclerView rvChatList;
    private ChatMessagesListAdapter chatMessagesListAdapter;
    private String userName = "";
    private String userId = "", chatWith = "";
    private DatabaseReference mReference;
    private static final String Table_Chat = "Chat";
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.new_chat_bot_layout);
        userName = "Customer Care";
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getEmail().split("@")[0];
        if(!userId.toLowerCase().contains("customercare")){
            chatWith = "customercare";//customercare@gamil.com;
        }
        else {
            // customercare chat with user
            chatWith = "kong";
            if(getIntent().hasExtra("ChatWith")){
                chatWith = getIntent().getStringExtra("ChatWith");
            }
        }
        initialiseControls();
        rvChatList.setLayoutManager(new LinearLayoutManager(NewChatBotActivity.this, LinearLayoutManager.VERTICAL, false));
        rvChatList.setAdapter(chatMessagesListAdapter = new ChatMessagesListAdapter(NewChatBotActivity.this, new ArrayList<ChatMessageDo>()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReference = database.getReference(Table_Chat);
        getData();
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etMessage.getText().toString().trim().equalsIgnoreCase("")){
                    ChatMessageDo chatMessageDo = new ChatMessageDo(etMessage.getText().toString(), userId, userName, new Date().getTime());
                    mReference.child(tableName()).child(""+System.currentTimeMillis()).setValue(chatMessageDo)
                              .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            getData();
                        }
                    });
                    etMessage.setText("");
                }
            }
        });

    }

    private void initialiseControls(){
        rvChatList              = findViewById(R.id.rvChatList);
        etMessage               = findViewById(R.id.etMessage);
        ivSend                  = findViewById(R.id.ivSend);
    }

    private String tableName(){
        char tableName[] = (userId+chatWith).toCharArray();
        Arrays.sort(tableName);
        return new String(tableName);
    }
    public void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference(Table_Chat);
//        showLoader();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                hideLoader();
                if(snapshot!=null){
                    Log.e("Chat Count " ,""+snapshot.getChildrenCount());
                    HashMap hashMap = (HashMap) snapshot.getValue();
                    if(hashMap!=null && hashMap.get(tableName()) != null){
                        ArrayList<String> chatWithLlist = new ArrayList<String>(((HashMap)hashMap.get(tableName())).keySet());
                        ArrayList<ChatMessageDo> chatMessageDos = new ArrayList<>();
                        if(chatWithLlist!=null && chatWithLlist.size() > 0){
                            for (int i=0;i<chatWithLlist.size();i++){
                                ChatMessageDo chatMessageDo = new ChatMessageDo();
                                chatMessageDo.messageText = (String) ((HashMap)((HashMap)hashMap.get(tableName())).get(chatWithLlist.get(i))).get("messageText");
                                chatMessageDo.fromUserId = (String) ((HashMap)((HashMap)hashMap.get(tableName())).get(chatWithLlist.get(i))).get("fromUserId");
                                chatMessageDo.messageUserName = (String) ((HashMap)((HashMap)hashMap.get(tableName())).get(chatWithLlist.get(i))).get("messageUserName");
                                chatMessageDo.messageTime = (Long) ((HashMap)((HashMap)hashMap.get(tableName())).get(chatWithLlist.get(i))).get("messageTime");
//                                if(!chatMessageDo.fromUserId.equalsIgnoreCase(userId)){

//                                }
                                chatMessageDos.add(chatMessageDo);
                            }
                        }

                        if(chatMessageDos!=null && chatMessageDos.size() > 0){
                            Collections.sort(chatMessageDos);
                            chatMessagesListAdapter.refreshAdapter(chatMessageDos);
                            rvChatList.post(new Runnable() {
                                @Override
                                public void run() {
                                    rvChatList.smoothScrollToPosition(chatMessagesListAdapter.getItemCount()- 1);
                                }
                            });

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

    private class ChatMessagesListAdapter extends RecyclerView.Adapter<ChatHolder>{

        private Context context;
        private ArrayList<ChatMessageDo> chatMessageDos;

        public ChatMessagesListAdapter(Context context, ArrayList<ChatMessageDo> chatMessageDos){
            this.context = context;
            this.chatMessageDos = chatMessageDos;
        }

        private void refreshAdapter(ArrayList<ChatMessageDo> chatMessageDos){
            Collections.sort(chatMessageDos);
            this.chatMessageDos = chatMessageDos;
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(context).inflate(R.layout.chat_message_item_cell, parent, false);
            return new ChatHolder(convertView);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
            if(!userId.contains(chatMessageDos.get(position).fromUserId)){
                holder.tvMyMessage.setText(""+chatMessageDos.get(position).messageText);
                holder.tvMyMessage.setVisibility(View.VISIBLE);
                holder.tvOtherMessage.setVisibility(View.GONE);
            }
            else {
                holder.tvMyMessage.setVisibility(View.GONE);
                holder.tvOtherMessage.setVisibility(View.VISIBLE);
                holder.tvOtherMessage.setText(""+chatMessageDos.get(position).messageText);
            }
        }

        @Override
        public int getItemCount() {
            return chatMessageDos!=null?chatMessageDos.size():0;
        }
    }

    private class ChatHolder extends RecyclerView.ViewHolder {

        private TextView tvMyMessage, tvOtherMessage;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            tvMyMessage               = itemView.findViewById(R.id.tvMyMessage);
            tvOtherMessage            = itemView.findViewById(R.id.tvOtherMessage);
        }
    }
}
