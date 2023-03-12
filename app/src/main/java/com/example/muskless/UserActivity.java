package com.example.muskless;

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

    String currentUser;
    int userId;

    String userName;
    String userBirth;
    String userMail;
    String userAvatar;

    ArrayList<Message> userMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().hide();

        userMessageList = new ArrayList<Message>();

        currentUser = getIntent().getStringExtra("currentUser");

        getUserData(currentUser);

        ImageView mainPageButton = findViewById(R.id.mainPageButton);
        ImageView infoButton = findViewById(R.id.infoButton);

        TextView userFullNameText = findViewById(R.id.userFullName);
        TextView userUsernameText = findViewById(R.id.currentUserName);
        TextView userBirthdayText = findViewById(R.id.userBirth);
        TextView userMailText = findViewById(R.id.userMail);
        ImageView userAvatar = findViewById(R.id.userAvatar);
        ImageView avatarImg = findViewById(R.id.profileImg);
        Button logOut = findViewById(R.id.logOutButton);

        String user_username = "@" + currentUser;

        userFullNameText.setText(userName);
        userUsernameText.setText(user_username);
        userBirthdayText.setText(userBirth);
        userMailText.setText(userMail);

        String currentAvatar = currentUserAvatar(currentUser);
        String avatarNoExtension = currentAvatar.substring(0, currentAvatar.length() - 4);

        String uri = "@drawable/" + avatarNoExtension;
        int imageResource = getResources().getIdentifier(uri, "drawable", getPackageName());
        Drawable res = getResources().getDrawable(imageResource);

        userAvatar.setImageDrawable(res);
        avatarImg.setImageDrawable(res);

        RecyclerView rv = findViewById(R.id.messagesRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(UserActivity.this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);

        BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USER_AVATAR + ", " + EstructuraBBDD.COlUMN_MESSAGE
                + ", " + EstructuraBBDD.COLUMN_USER_NAME + ", " + EstructuraBBDD.COLUMN_MESSAGE_DATE + ", " + EstructuraBBDD.COLUMN_ID_USER + " FROM "
                + EstructuraBBDD.TABLE_MESSAGES + " WHERE " + EstructuraBBDD.COLUMN_ID_USER + " like " + userId + "", null);

        if(cursor.getCount() > 1){

            cursor.moveToFirst();

            while (!cursor.isAfterLast()){

                String avatar = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_AVATAR));
                String message = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COlUMN_MESSAGE));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_NAME));
                String messageDate = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_MESSAGE_DATE));
                int idUser = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_ID_USER));

                Message m = new Message(username, message, avatar, messageDate, idUser);
                userMessageList.add(m);

                cursor.moveToNext();

                RecyclerAdapter adapter = new RecyclerAdapter(userMessageList, getApplicationContext(), username);
                rv.setAdapter(adapter);

            }

        } else if (cursor.getCount() == 1) {

            cursor.moveToFirst();

            String avatar = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_AVATAR));
            String message = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COlUMN_MESSAGE));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_NAME));
            String messageDate = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_MESSAGE_DATE));
            int idUser = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_ID_USER));

            Message m = new Message(username, message, avatar, messageDate, idUser);
            userMessageList.add(m);

            RecyclerAdapter adapter = new RecyclerAdapter(userMessageList, getApplicationContext(), username);
            rv.setAdapter(adapter);

        }

        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserActivity.this, MainPageActivity.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);

            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserActivity.this, NewspaperActivity.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("currentAvatar", currentAvatar);
                startActivity(i);

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

    }

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

    @Override
    public void onBackPressed() {
        //Bloquear el botón de atrás
    }
}