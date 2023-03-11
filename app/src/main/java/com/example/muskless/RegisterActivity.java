package com.example.muskless;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private Button submitButton;
    private TextView loginText;
    private EditText userText;
    private EditText passwordText;
    private EditText mailText;
    private EditText birthText;
    private EditText nameText;

    private String usrName;
    private String usrUser;
    private String usrMail;
    private String usrPassword;
    private int usrDay;
    private int usrMonth;
    private int usrYear;

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
        birthText = findViewById(R.id.userBirth);
        nameText = findViewById(R.id.userName);

        birthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){

                    case R.id.userBirth:
                        showDatePickerDialog();
                        break;

                }
            }
        });

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

                BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USERNAME + " FROM "
                        + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_EMAIL + " = '"
                        + mailText.getText().toString() + "' OR " + EstructuraBBDD.COLUMN_USERNAME + " = '"
                        + userText.getText().toString() + "'", null);

                if(!cursor.moveToFirst()){

                    if(nameText.getText().toString().equals("") || passwordText.getText().toString().equals("") || userText.getText().toString().equals("") || mailText.getText().toString().equals("") || birthText.getText().toString().equals("")){

                        Context context = getApplicationContext();
                        CharSequence text = "";

                        if(Locale.getDefault().getLanguage() == "en"){

                            text = "All fields must be filled!";

                        } else {

                            text = "¡Debes rellenar todos los campos!";

                        }

                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    } else {

                        Intent i = new Intent(RegisterActivity.this, AvatarChoosingActivity.class);

                        i.putExtra("name", nameText.getText().toString());
                        i.putExtra("username", userText.getText().toString());
                        i.putExtra("password", passwordText.getText().toString());
                        i.putExtra("email", mailText.getText().toString());
                        i.putExtra("birthday", birthText.getText().toString());

                        startActivity(i);

                    }

                } else {

                    Context context = getApplicationContext();
                    CharSequence text = "";

                    if(Locale.getDefault().getLanguage() == "en"){

                        text = "User is already registered!";

                    } else {

                        text = "¡Ese usuario ya existe!";

                    }

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });

    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                birthText.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}