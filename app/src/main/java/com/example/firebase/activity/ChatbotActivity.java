package com.example.firebase.activity;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.R;
import com.example.firebase.adapter.MessageListAdapter;
import com.example.firebase.models.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<Message> messageList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChatBox");
        setSupportActionBar(toolbar);

        mMessageAdapter = new MessageListAdapter(this, messageList);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent7=new Intent(ChatbotActivity.this,DashboardActivity.class);
        startActivity(intent7);
        finish();

    }
}