package com.example.muskless;

/*
 * Definimos los importes necesarios para el funcionamiento de la aplicación.
 * */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class AvatarChoosingActivity extends AppCompatActivity {

    /*
     * Definimos los elementos que se usarán de forma global.
     * */
    private ImageButton avatar1;
    private ImageButton avatar2;
    private ImageButton avatar3;
    private ImageButton avatar4;
    private ImageButton avatar5;
    private ImageButton avatar6;

    private String usrName;
    private String usrUser;
    private String usrMail;
    private String usrPassword;
    private String usrBirthday;
    private String usrAvatar;

    private BaseDatosHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_choosing);

        getSupportActionBar().hide();

        //Instanciamos todos los elementos del activity.
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);

        //Obtenemos los datos del intent de registro.
        usrName = getIntent().getStringExtra("name");
        usrUser = getIntent().getStringExtra("username");
        usrMail = getIntent().getStringExtra("email");
        usrPassword = getIntent().getStringExtra("password");
        usrBirthday = getIntent().getStringExtra("birthday");

        /*
        * Definimos un setOnClickListener que instancie un usuario con el nombre del avatar seleccionado.
        * */
        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usrAvatar = "avatar_uno.png";

                addUser(usrName, usrUser, usrMail, usrPassword, usrBirthday, usrAvatar);

            }
        });

        /*
         * Definimos un setOnClickListener que instancie un usuario con el nombre del avatar seleccionado.
         * */
        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usrAvatar = "avatar_dos.png";

                addUser(usrName, usrUser, usrMail, usrPassword, usrBirthday, usrAvatar);

            }
        });

        /*
         * Definimos un setOnClickListener que instancie un usuario con el nombre del avatar seleccionado.
         * */
        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usrAvatar = "avatar_tres.png";

                addUser(usrName, usrUser, usrMail, usrPassword, usrBirthday, usrAvatar);

            }
        });

        /*
         * Definimos un setOnClickListener que instancie un usuario con el nombre del avatar seleccionado.
         * */
        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usrAvatar = "avatar_cuatro.png";

                addUser(usrName, usrUser, usrMail, usrPassword, usrBirthday, usrAvatar);

            }
        });

        /*
         * Definimos un setOnClickListener que instancie un usuario con el nombre del avatar seleccionado.
         * */
        avatar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usrAvatar = "avatar_cinco.png";

                addUser(usrName, usrUser, usrMail, usrPassword, usrBirthday, usrAvatar);

            }
        });

        /*
         * Definimos un setOnClickListener que instancie un usuario con el nombre del avatar seleccionado.
         * */
        avatar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usrAvatar = "avatar_seis.png";

                addUser(usrName, usrUser, usrMail, usrPassword, usrBirthday, usrAvatar);

            }
        });


    }

    /*
    * Definimos un método encargado de añadir a la BD el usuario instanciado.
    * */
    public void addUser(String name, String username, String mail, String password, String birthday, String avatar){

        dbHelper = new BaseDatosHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try{

            //Buscamos con el cursor que el usuario que queremos registrar no exista ya en BD.
            Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USERNAME + " FROM "
                    + EstructuraBBDD.TABLE_USERS + " WHERE " + EstructuraBBDD.COLUMN_EMAIL + " = '"
                    + mail + "' OR " + EstructuraBBDD.COLUMN_USERNAME + " = '" + username
                    + "'", null);

            if(!cursor.moveToFirst()){ //Caso en el que el usuario no exista.

                db = dbHelper.getWritableDatabase();

                //Añadimos a la BD la información introducida acerca del usuario.
                db.execSQL("INSERT INTO " + EstructuraBBDD.TABLE_USERS + " (" + EstructuraBBDD.COLUMN_NAME + ", "
                        + EstructuraBBDD.COLUMN_USERNAME + ", " + EstructuraBBDD.COLUMN_EMAIL + ", " + EstructuraBBDD.COLUMN_PASSWORD + ", "
                        + EstructuraBBDD.COLUMN_BIRTHDAY + ", " + EstructuraBBDD.COLUMN_AVATAR + ") VALUES ('" + name + "', '"
                        + username + "', '" + mail + "', '" + password + "', '" + birthday + "', '" + avatar + "');");

                //Toast que muestra un mensaje de registro completo.
                Context context = getApplicationContext();
                CharSequence text = "Usuario registrado correctamente.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Lanzamos el activity de login nuevamente.
                Intent i = new Intent(AvatarChoosingActivity.this, LoginActivity.class);
                startActivity(i);

            } else { //En caso de que el usuario ya exista.

                Context context = getApplicationContext();
                CharSequence text = "¡Ese usuario ya existe!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }

        } catch (Exception e){ //Caso en el que algún error ha ocurrido al registrar al usuario.

            Context context = getApplicationContext();
            CharSequence text = "Error al registrar el usuario.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }

    }
}