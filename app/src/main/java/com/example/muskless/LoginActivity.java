package com.example.muskless;

/*
* Definimos los importes necesarios para el funcionamiento de la aplicación.
* */
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

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    /*
    * Definimos los elementos que se usarán de forma global.
    * */
    private Button submitButton;
    private TextView registerText;
    private EditText userText;
    private EditText passwordText;

    private BaseDatosHelper dbHelper; //Definimos un objeto de tipo BaseDatosHelper.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); //Escondemos la barra que aparece por defecto.

        //Instanciamos el objeto helper de la base de datos para que se cree la misma si es necesario.
        dbHelper = new BaseDatosHelper(LoginActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //"Obtenemos" una base de datos sobre la que podamos escribir.

        //Instanciamos los elementos que se utilizarán en este activity.
        submitButton = findViewById(R.id.submitButton);
        registerText = findViewById(R.id.clickableText);
        userText = findViewById(R.id.userUsername);
        passwordText = findViewById(R.id.userPassword);

        /*
        * Definimos un onClickListener para el botón que nos envía al activity del registro de usuarios.
        * */
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Definimos e instanciamos un intent que nos envíe al activity de registro.
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        /*
         * Definimos un onClickListener para el botón que realiza el inicio de sesión.
         *
         * Este método se encarga de buscar en base de datos si los datos introducidos en los campos
         * existen en base de datos para conceder acceso a la app.
         * */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //"Obtenemos" una base de datos sobre la que hacer consultas.
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                //Con un cursor y una rawQuery buscamos la información introducida en la BD.
                Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USERNAME + " FROM "
                        + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_PASSWORD
                        + " = '" + passwordText.getText().toString() + "' AND " + EstructuraBBDD.COLUMN_USERNAME
                        + " = '" + userText.getText().toString() + "'", null);

                if(cursor.moveToFirst()){ //Registro exitoso.

                    //En caso de inicio de sesión exitoso, nos movemos al siguiente activity.
                    Intent i = new Intent(LoginActivity.this, MainPageActivity.class);

                    //Añadimos al intent información adicional, en este caso el valor de la variable
                    //"currentUser" que contiene el nombre del usuario actual de la app.
                    i.putExtra("currentUser", userText.getText().toString());
                    startActivity(i);

                    //Toast que muestra un mensaje de inicio de sesión exitoso.
                    Context context = getApplicationContext();
                    CharSequence text = "";

                    if(Locale.getDefault().getLanguage() == "en"){

                        text = "Successfully logged in!";

                    } else {

                        text = "¡Inicio de sesión exitoso!";

                    }

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                } else {

                    //Toast que indica que la información introducida no es válida.
                    Context context = getApplicationContext();
                    CharSequence text = "";

                    if(Locale.getDefault().getLanguage() == "en"){

                        text = "Wrong data!";

                    } else {

                        text = "¡Datos erróneos!";

                    }

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });

    }

    /*
    * Método sobreescrito que bloquear el botón de hacia atrás.
    * */
    @Override
    public void onBackPressed() {
        //Bloquear el botón de atrás
    }

}