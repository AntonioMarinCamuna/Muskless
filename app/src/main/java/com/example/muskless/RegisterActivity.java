package com.example.muskless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private Button submitButton;
    private TextView loginText;
    private EditText userText;
    private EditText passwordText;
    private EditText mailText;
    private EditText birthText;
    private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        submitButton = findViewById(R.id.submitButton);
        loginText = findViewById(R.id.clickableText);
        userText = findViewById(R.id.userUsername);
        passwordText = findViewById(R.id.userPassword);
        mailText = findViewById(R.id.userMail);
        birthText = findViewById(R.id.userBirthday);
        nameText = findViewById(R.id.userName);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegisterActivity.this, AvatarChoosingActivity.class);
                startActivity(i);

            }
        });

    }

}