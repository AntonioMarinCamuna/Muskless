package com.example.muskless;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AvatarChoosingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_choosing);

        getSupportActionBar().hide();

    }
}