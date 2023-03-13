package com.example.muskless;

/*
 * Definimos los importes necesarios para el funcionamiento de la aplicación.
 * */
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    /*
     * Definimos los elementos que se usarán de forma global.
     * */
    private ImageView mainPageButton;
    private ImageView infoButton;
    private TextView userFullNameText;
    private TextView userUsernameText;
    private TextView userBirthdayText;
    private TextView userMailText;
    private ImageView user_Avatar;
    private ImageView avatarImg;
    private Button logOut;

    private String currentUser;
    int userId;

    private String userName;
    private String userBirth;
    private String userMail;
    private String userAvatar;
    private String currentAvatar;
    private String user_username;

    private ArrayList<Message> userMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().hide(); //Escondemos la barra que aparece por defecto.

        //Obtenemos la información pasada por el intent.
        currentUser = getIntent().getStringExtra("currentUser");
        getUserData(currentUser); //Llamada al método encargado de obtener de la BD la información
                                  // relativa al usuario actual.

        //Instanciamos todos los elementos del activity.
        user_username = "@" + currentUser;

        userMessageList = new ArrayList<Message>();

        mainPageButton = findViewById(R.id.mainPageButton);
        infoButton = findViewById(R.id.infoButton);

        userFullNameText = findViewById(R.id.userFullName);
        userUsernameText = findViewById(R.id.currentUserName);
        userBirthdayText = findViewById(R.id.userBirth);
        userMailText = findViewById(R.id.userMail);
        user_Avatar = findViewById(R.id.userAvatar);
        avatarImg = findViewById(R.id.profileImg);
        logOut = findViewById(R.id.logOutButton);

        userFullNameText.setText(userName);
        userUsernameText.setText(user_username);
        userBirthdayText.setText(userBirth);
        userMailText.setText(userMail);

        userAvatarSetter(); //Llamada al método encargado de poner la imagen del usuario actual.
        setUserMessages(); //Llamada la método encargado de mostrar el historial de mensajes del usuario.

        /*
         * Definimos un setOnClickListener para la imagen/botón encargado de lanzar el activity de la
         * página principal.
         * */
        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserActivity.this, MainPageActivity.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);

            }
        });

        /*
         * Definimos un setOnClickListener al "botón" de información que nos permitirá acceder a un
         * activity donde se mostrará un periódico en base al idioma del dispositivo.
         * */
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserActivity.this, NewspaperActivity.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("currentAvatar", currentAvatar);
                startActivity(i);

            }
        });

        /*
        * Definimos un setOnClickListener para el botón "Cerrar sesión" del activity, esta será la
        * única forma de volver al activity del login.
        * */
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

    }

    /*
     * Método encargado de mostrar por pantalla los mensajes ya enviados por el usuario cada vez
     * que se acceda al activity.
     * */
    public void setUserMessages(){

        //Instanciamos el RecyclerView.
        RecyclerView rv = findViewById(R.id.messagesRecycler);

        //"Configuramos" el layout manager del RecyclerView.
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserActivity.this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);

        BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Con un cursor, buscamos en la base de datos la información relativa a cada mensaje enviado.
        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USER_AVATAR + ", " + EstructuraBBDD.COlUMN_MESSAGE
                + ", " + EstructuraBBDD.COLUMN_USER_NAME + ", " + EstructuraBBDD.COLUMN_MESSAGE_DATE + ", " + EstructuraBBDD.COLUMN_ID_USER + " FROM "
                + EstructuraBBDD.TABLE_MESSAGES + " WHERE " + EstructuraBBDD.COLUMN_ID_USER + " like " + userId + "", null);

        if(cursor.getCount() > 1){ //Caso en el que el cursor devuelva mas de un resultado.

            cursor.moveToFirst();

            while (!cursor.isAfterLast()){

                //Obtenemos cada dato extraido por el cursor.
                String avatar = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_AVATAR));
                String message = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COlUMN_MESSAGE));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_NAME));
                String messageDate = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_MESSAGE_DATE));
                int idUser = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_ID_USER));

                //Instanciamos un nuevo objeto mensaje y lo añadimos a la lista.
                Message m = new Message(username, message, avatar, messageDate, idUser);
                userMessageList.add(m);

                cursor.moveToNext(); //Pasamos al siguiente elemento del cursor.

                RecyclerAdapter adapter = new RecyclerAdapter(userMessageList, getApplicationContext(), username);
                rv.setAdapter(adapter);

            }

        } else if (cursor.getCount() == 1) { //Caso en el que el cursor devuelva un único resultado.

            cursor.moveToFirst();

            //Obtenemos cada dato extraido por el cursor.
            String avatar = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_AVATAR));
            String message = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COlUMN_MESSAGE));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_NAME));
            String messageDate = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_MESSAGE_DATE));
            int idUser = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_ID_USER));

            //Instanciamos un nuevo objeto mensaje y lo añadimos a la lista.
            Message m = new Message(username, message, avatar, messageDate, idUser);
            userMessageList.add(m);

            RecyclerAdapter adapter = new RecyclerAdapter(userMessageList, getApplicationContext(), username);
            rv.setAdapter(adapter);

        }

    }

    /*
     * Método encargado de asignar a la imagen de perfil el avatar del usuario.
     * */
    public void userAvatarSetter(){

        currentAvatar = currentUserAvatar(currentUser);
        String avatarNoExtension = currentAvatar.substring(0, currentAvatar.length() - 4);

        String uri = "@drawable/" + avatarNoExtension;
        int imageResource = getResources().getIdentifier(uri, "drawable", getPackageName());
        Drawable res = getResources().getDrawable(imageResource);

        user_Avatar.setImageDrawable(res);
        avatarImg.setImageDrawable(res);

    }

    /*
     * Método encargado de obtener toda la información del usuario que accede a su página del perfil.
     * */
    public void getUserData(String currentUser){

        BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_NAME + ", " + EstructuraBBDD.COLUMN_EMAIL
                + ", " + EstructuraBBDD.COLUMN_BIRTHDAY + ", " + EstructuraBBDD.COLUMN_AVATAR + ", " + EstructuraBBDD.COLUMN_ID + " FROM "
                + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_USERNAME + " = '" + currentUser + "'", null);

        cursor.moveToFirst();

        userName = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NAME));
        userBirth = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_BIRTHDAY));
        userMail = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_EMAIL));
        userAvatar = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_AVATAR));
        userId = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_ID));

    }

    /*
     * Método encargado de buscar en base de datos el String del nombre del avatar del usuario.
     * */
    public String currentUserAvatar(String user){

        String str = "";

        BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_AVATAR + " FROM "
                + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_USERNAME + " = '" + user + "'", null);

        if(cursor.moveToFirst()){

            str = cursor.getString(0);

        }

        return str;

    }

    /*
     * Método sobreescrito que bloquear el botón de hacia atrás.
     * */
    @Override
    public void onBackPressed() {
        //Bloquear el botón de atrás
    }
}