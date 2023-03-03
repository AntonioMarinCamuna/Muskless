package com.example.muskless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getSupportActionBar().hide();

        Button btn = findViewById(R.id.sendBtn);
        TextView txt = findViewById(R.id.txtMessage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = txt.getText().toString();

                Intent i = new Intent();
                i.putExtra("a", a);
                setResult(1, i);

                MessageActivity.super.onBackPressed();

            }
        });

    }
}