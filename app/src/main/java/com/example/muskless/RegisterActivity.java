package com.example.muskless;

/*
 * Definimos los importes necesarios para el funcionamiento de la aplicación.
 * */
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    /*
     * Definimos los elementos que se usarán de forma global.
     * */
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

        getSupportActionBar().hide(); //Escondemos la barra que aparece por defecto.

        //Instanciamos los elementos que se utilizarán en este activity.
        submitButton = findViewById(R.id.submitButton);
        loginText = findViewById(R.id.clickableText);
        userText = findViewById(R.id.userUsername);
        passwordText = findViewById(R.id.userPassword);
        mailText = findViewById(R.id.userMail);
        birthText = findViewById(R.id.userBirth);
        nameText = findViewById(R.id.userName);

        /*
        * Definimos un setOnClickListener para el campo de selección de fecha de nacimiento.
        * */
        birthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){

                    case R.id.userBirth:
                        showDatePickerDialog(); //Mostramos el dialog de selección de fecha.
                        break;

                }
            }
        });

        /*
         * Definimos un setOnClickListener para el texto que lanza el activity del inicio de sesión.
         * */
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

        /*
        * Definimos un setOnClickListener encargado de gestionar la información para el registro del usuario.
        * */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Comprobamos que ese usuario no exista ya en la BD.
                BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USERNAME + " FROM "
                        + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_EMAIL + " = '"
                        + mailText.getText().toString() + "' OR " + EstructuraBBDD.COLUMN_USERNAME + " = '"
                        + userText.getText().toString() + "'", null);

                if(!cursor.moveToFirst()){ //Caso en el que el usuario no existe en la BD.

                    //Caso en el que algunos de los elementos de texto esté vacío.
                    if(nameText.getText().toString().equals("") || passwordText.getText().toString().equals("") || userText.getText().toString().equals("") || mailText.getText().toString().equals("") || birthText.getText().toString().equals("")){

                        //Toast que muestra un mensaje de error en base al idioma del dispositivo.
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

                    } else { //Caso en el que ningún elemento esté vacío.

                        if(mayorEdad()){ //Caso en el que la fecha introducida correponda con la de alguien mayor de edad.

                            Intent i = new Intent(RegisterActivity.this, AvatarChoosingActivity.class);

                            i.putExtra("name", nameText.getText().toString());
                            i.putExtra("username", userText.getText().toString());
                            i.putExtra("password", passwordText.getText().toString());
                            i.putExtra("email", mailText.getText().toString());
                            i.putExtra("birthday", birthText.getText().toString());

                            startActivity(i);

                        } else { //Caso en el que la fecha de nacimiento no corresponda a alguien mayor de edad.

                            //Toast que muestra una alerta de error de registro.
                            Context context = getApplicationContext();
                            CharSequence text = "";

                            if(Locale.getDefault().getLanguage() == "en"){

                                text = "You must be over 18 years old!";

                            } else {

                                text = "¡Debes ser mayor de edad!";

                            }

                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }



                    }

                } else { //Caso en el que el usuario ya exista en la BD.

                    //Toast que muestra una alerta de que el usuario ya existe.
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

    /*
    * Método encargado de almacenar la fecha introducida en el dialog como un String.
    * */
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                birthText.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /*
    * Método que comprueba que la fecha introducida corresponda con la de alguien mayor de edad.
    * */
    public Boolean mayorEdad(){

        boolean mayor = false;

        int year;
        int month;
        int day;

        String birthday = birthText.getText().toString();
        String[] birth = birthday.split("/");

        day = Integer.parseInt(birth[0]);
        month = Integer.parseInt(birth[1]);
        year = Integer.parseInt(birth[2]);

        Calendar birthdate = Calendar.getInstance();
        birthdate.set(year, month, day);

        Calendar now = Calendar.getInstance();

        int age = now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);

        if (birthdate.get(Calendar.DAY_OF_YEAR) > now.get(Calendar.DAY_OF_YEAR)) {

            age--;

        }

        if (age >= 18) {

            mayor = true;

        }

        return mayor;

    }

}