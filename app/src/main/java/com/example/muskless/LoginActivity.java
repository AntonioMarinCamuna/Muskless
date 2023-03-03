package com.example.muskless;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button submitButton;
    private TextView registerText;
    private EditText userText;
    private EditText passwordText;

    BaseDatosHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        dbHelper = new BaseDatosHelper(LoginActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

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

                SQLiteDatabase db = dbHelper.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USERNAME + " FROM " + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_PASSWORD + " = '" + passwordText.getText().toString() + "' AND " + EstructuraBBDD.COLUMN_USERNAME + " = '" + userText.getText().toString() + "'", null);

                if(cursor.moveToFirst()){

                    Intent i = new Intent(LoginActivity.this, MainPageActivity.class);
                    i.putExtra("currentUser", userText.getText().toString());
                    startActivity(i);

                } else {

                    Context context = getApplicationContext();
                    CharSequence text = "Datos incorrectos!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });

    }

}