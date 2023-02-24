package com.example.muskless;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button submitButton;
    private TextView registerText;
    private EditText userText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        submitButton = findViewById(R.id.submitButton);
        registerText = findViewById(R.id.clickableText);
        userText = findViewById(R.id.userUsername);
        passwordText = findViewById(R.id.userPassword);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userText.getText().toString().equals("admin") && passwordText.getText().toString().equals("admin")){

                    Intent i = new Intent(LoginActivity.this, MainPageActivity.class);
                    startActivity(i);

                }

            }
        });

    }

}