package com.example.muskless;

/*
 * Definimos los importes necesarios para el funcionamiento de la aplicación.
 * */
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainPageActivity extends AppCompatActivity {

    /*
     * Definimos los elementos que se usarán de forma global.
     * */
    public static final int REQUEST_CODE = 1;   //Entero que se empleará a modo de clave para el onActivityResult.
    private static final String TAG = "MainPageActivity";   //String que se empleará en el método onActivityResult.

    //Datos relacionados al mensaje que se enviará.
    private String data_1; //User
    private String data_2; //Message
    private String data_3; //Avatar
    private String data_4; //Birthday
    int data_5;            //Id

    private String currentAvatar;
    private String currentUser;

    private Context appContext;

    //Elementos del activity
    private ArrayList<Message> messageList;
    private FloatingActionButton floatingButton;
    private ImageView profileImg;
    private ImageView infoButton;

    /*
    * Método encargado de recibir información de un activity lanzado desde este mismo activity,
    * al realizar un registro en 2 pasos, será necesario este método para recibir el mensaje introducido
    * en el siguiente activity.
    * */
    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.d(TAG, "onActivityResult: ");

            if(result.getResultCode() == 1){ //Caso en el que la clave obtenida del nuevo activity sea 1

                Intent i = result.getData(); //Obtenemos la información devuelta por el activity

                if(i != null){

                    //Asignamos a los datos del mensaje los datos necesarios.
                    data_1 = currentUser;
                    data_2 = i.getStringExtra("message");

                    String avatar = currentUserAvatar(currentUser);

                    data_3 = avatar;

                    data_4 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                    //Llamada a un método encargado de obtener la id del usuario que publica el mensaje.
                    getUserData(currentUser);

                    //Asignamos lo necesario al recycler view para que muestre el mensaje nuevo
                    //cuando se vuelva al activity.
                    RecyclerView rv = findViewById(R.id.messagesRecycler);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainPageActivity.this, LinearLayoutManager.VERTICAL, false);
                    layoutManager.setReverseLayout(true); //Damos la vuelta al layout asignado al RecyclerView para que muestre la lista al revés.
                    layoutManager.setStackFromEnd(true);
                    rv.setLayoutManager(layoutManager);

                    //Añadimos el mensaje a la lista de mensajes
                    Message m = new Message(data_1, data_2, data_3, data_4, data_5);
                    messageList.add(m);

                    //Añadimos el mensaje a la base de datos.
                    BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    db.execSQL("INSERT INTO " + EstructuraBBDD.TABLE_MESSAGES + " (" + EstructuraBBDD.COLUMN_USER_NAME + ", "
                            + EstructuraBBDD.COlUMN_MESSAGE + ", " + EstructuraBBDD.COLUMN_MESSAGE_DATE + ", "
                            + EstructuraBBDD.COLUMN_USER_AVATAR + ", " + EstructuraBBDD.COLUMN_ID_USER + ") VALUES ('" + data_1 + "', '"
                            + data_2 + "', '" + data_4 + "', '" + data_3 + "', '" + data_5 + "');");

                    //Instanciamos el RecyclerAdapter con la lista de mensajes,
                    RecyclerAdapter adapter = new RecyclerAdapter(messageList, appContext, currentUser);
                    rv.setAdapter(adapter);

                }

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //Obtenemos la información pasada por el intent.
        currentUser = getIntent().getStringExtra("currentUser");

        //Instanciamos los elementos que se utilizarán en el activity.
        messageList = new ArrayList<Message>();

        appContext = getApplicationContext();

        profileImg = findViewById(R.id.profileImg);
        infoButton = findViewById(R.id.infoButton);
        floatingButton = findViewById(R.id.floatingMessage);

        setUserAvatar(); //Llamada al método encargado de asignar la imagen del usuario a la imagen del avatar.

        defaultMessageSet(); //Llamada al método encargado de mostrar en pantalla los mensajes ya enviados
                             //cada vez que se accede al activity.

        /*
        * Definimos un setOnClickListener al floating button que muestre la pantalla de escritura
        * de mensajes.
        * */
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Definimos e instanciamos un intent que envíe al usuario al activity de escritura de mensajes.
                Intent i = new Intent(MainPageActivity.this, MessageActivity.class);
                activityLauncher.launch(i);

            }
        });

        /*
        * Definimos un setOnClickListener a la imagen del avatar que nos permita acceder al activity
        * del perfil del usuario.
        * */
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainPageActivity.this, UserActivity.class);

                i.putExtra("currentUser", currentUser); //Añadimos el nombre del usuario actual para
                                                              // acceder a la información del mismo en el activity.
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

                Intent i = new Intent(MainPageActivity.this, NewspaperActivity.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("currentAvatar", currentAvatar);
                startActivity(i);

            }
        });

    }

    /*
    * Método encargado de mostrar por pantalla los mensajes ya enviados cada vez que se acceda al activity.
    * */
    public void defaultMessageSet(){

        //Instanciamos el RecyclerView
        RecyclerView rv = findViewById(R.id.messagesRecycler);

        //"Configuramos" el layout manager del RecyclerView.
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainPageActivity.this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);

        BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Con un cursor, buscamos en la base de datos la información relativa a cada mensaje enviado.
        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USER_AVATAR + ", " + EstructuraBBDD.COlUMN_MESSAGE
                + ", " + EstructuraBBDD.COLUMN_USER_NAME + ", " + EstructuraBBDD.COLUMN_MESSAGE_DATE + ", " + EstructuraBBDD.COLUMN_ID_USER + " FROM "
                + EstructuraBBDD.TABLE_MESSAGES + "", null);

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
                messageList.add(m);

                cursor.moveToNext(); //Pasamos al siguiente elemento del cursor.

                RecyclerAdapter adapter = new RecyclerAdapter(messageList, appContext, username);
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
            messageList.add(m);

            RecyclerAdapter adapter = new RecyclerAdapter(messageList, appContext, username);
            rv.setAdapter(adapter);

        }

    }

    /*
    * Método encargado de asignar a la imagen de perfil el avatar del usuario.
    * */
    public void setUserAvatar(){

        //Llamada al método encargado de sacar debase de datos el nombre del avatar del usuario.
        currentAvatar = currentUserAvatar(currentUser);

        //Eliminamos la extensión ".png" de la imagen.
        String avatarNoExtension = currentAvatar.substring(0, currentAvatar.length() - 4);

        //Obtenemos una String uri, con la que obtendremos el recurso de la imagen y la asignaremos al ImageView
        String uri = "@drawable/" + avatarNoExtension;
        int imageResource = getResources().getIdentifier(uri, "drawable", getPackageName());
        Drawable res = getResources().getDrawable(imageResource);

        profileImg.setImageDrawable(res);

    }

    /*
    * Método encargado de buscar en base de datos el String del nombre del avatar del usuario.
    * */
    public String currentUserAvatar(String user){

        String str = "";

        BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Mediante un cursor obtenemos dicho String.
        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_AVATAR + " FROM "
                + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_USERNAME + " = '" + user + "'", null);

        if(cursor.moveToFirst()){

            str = cursor.getString(0);

        }

        return str;

    }

    /*
    * Método encargado de obtener el ID del usuario que escribe un mensaje para añadirlo al objeto Message.
    * */
    public void getUserData(String currentUser){

        BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " +  EstructuraBBDD.COLUMN_ID + " FROM "
                + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_USERNAME + " = '" + currentUser + "'", null);

        cursor.moveToFirst();

        data_5 = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_ID));

    }

    /*
     * Método sobreescrito que bloquear el botón de hacia atrás.
     * */
    @Override
    public void onBackPressed() {
        //Bloquear el botón de atrás
    }

}