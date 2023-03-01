package com.example.muskless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainPageActivity extends AppCompatActivity {

    ArrayList<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        messageList = new ArrayList<Message>();

        RecyclerView rv = findViewById(R.id.messagesRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        Message m = new Message("Pedro", "Pedro", "avatar_uno.png");
        messageList.add(m);

        RecyclerAdapter adapter = new RecyclerAdapter(messageList);
        rv.setAdapter(adapter);

    }
}