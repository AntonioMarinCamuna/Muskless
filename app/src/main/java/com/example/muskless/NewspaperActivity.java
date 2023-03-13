package com.example.muskless;

/*
 * Definimos los importes necesarios para el funcionamiento de la aplicación.
 * */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import java.util.Locale;

public class NewspaperActivity extends AppCompatActivity {

    /*
     * Definimos los elementos que se usarán de forma global.
     * */
    private ImageView mainPageButton;
    private ImageView profileButton;

    private String currentUser;
    private String currentAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);

        getSupportActionBar().hide(); //Escondemos la barra que aparece por defecto.

        //Obtenemos la información pasada a través del intent.
        currentUser = getIntent().getStringExtra("currentUser");
        currentAvatar = getIntent().getStringExtra("currentAvatar");

        //Instanciamos los elementos del activity.
        mainPageButton = findViewById(R.id.mainPageButton);
        profileButton = findViewById(R.id.profileImg);

        setUserAvatar(); //Llamada al método de asignar el avatar al elemento ImageView.

        //Configuramos el WebView para que pueda mostrar elementos JavaScript y para activar el caché.
        WebView myWebView = (WebView) findViewById(R.id.newspaperView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);


        if(Locale.getDefault().getLanguage() == "es"){ //Caso en el que el idioma del dispositivo sea español.

            myWebView.loadUrl("https://www.hoy.es/"); //Asignamos la URL de un periódico español.

        } else { //Caso contrario.

            myWebView.loadUrl("https://www.nytimes.com/"); //Asignamos la URL de un periódico inglés.

        }

        /*
        * Definimos un setOnClickListener para la imagen/botón encargado de lanzar el activity de la
        * página principal.
        * */
        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(NewspaperActivity.this, MainPageActivity.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);

            }
        });

        /*
        * Definimos un setOnClickListener para la imagen/botón encargado de lanzar el activity de el
        * perfil del usuario.
        * */
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(NewspaperActivity.this, UserActivity.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);

            }
        });

    }

    /*
     * Método encargado de asignar a la imagen de perfil el avatar del usuario.
     * */
    public void setUserAvatar(){

        //Eliminamos la extensión ".png" de la imagen.
        String avatarNoExtension = currentAvatar.substring(0, currentAvatar.length() - 4);

        //Obtenemos una String uri, con la que obtendremos el recurso de la imagen y la asignaremos al ImageView
        String uri = "@drawable/" + avatarNoExtension;
        int imageResource = getResources().getIdentifier(uri, "drawable", getPackageName());
        Drawable res = getResources().getDrawable(imageResource);

        profileButton.setImageDrawable(res);

    }

    /*
     * Método sobreescrito que bloquear el botón de hacia atrás.
     * */
    @Override
    public void onBackPressed() {
        //Bloquear el botón de atrás
    }
}