package com.example.muskless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

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

                String message = txt.getText().toString();

                if(message.equals("")){

                    Context context = getApplicationContext();
                    CharSequence text = "";

                    if(Locale.getDefault().getLanguage() == "en"){

                        text = "Message must at least contains a character!";

                    } else {

                        text = "¡El mensaje debe contener al menos un carácter!";

                    }

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                } else {

                    Intent i = new Intent();
                    i.putExtra("message", message);
                    setResult(1, i);

                    MessageActivity.super.onBackPressed();

                }

            }
        });

    }
}