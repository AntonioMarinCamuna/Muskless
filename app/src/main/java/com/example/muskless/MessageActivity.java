package com.example.muskless;

/*
 * Definimos los importes necesarios para el funcionamiento de la aplicación.
 * */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MessageActivity extends AppCompatActivity {

    /*
     * Definimos los elementos que se usarán de forma global.
     * */
    private Button btn;
    private EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getSupportActionBar().hide(); //Escondemos la barra que aparece por defecto.

        //Instanciamos los elementos que se utilizarán en este activity.
        btn = findViewById(R.id.sendBtn);
        txt = findViewById(R.id.txtMessage);

        /*
        * Definimos un setOnClickListener del Button para enviar la información introducida
        * en el cuadro de texto al activity "MainPageActivity".
        * */
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = txt.getText().toString();

                if(message.equals("")){ //Caso en el que el texto esté vacío.

                    //Toast que muestra un mensaje de error de texto vacío.
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

                } else { //Caso en el que se introduzca un mensaje válido.

                    //Definimos un intent, en este caso, añadimos información extra al mismo.
                    Intent i = new Intent();
                    i.putExtra("message", message);

                    //Esta será la información que validará que la información devuelta proviene de
                    //este activity y es información relativa a Message.
                    setResult(1, i);

                    MessageActivity.super.onBackPressed();

                }

            }
        });

    }
}