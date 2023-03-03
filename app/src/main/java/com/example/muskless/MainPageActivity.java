package com.example.muskless;

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

    public static final int REQUEST_CODE = 1;
    private static final String TAG = "MainPageActivity";

    private String data_1;
    private String data_2;
    private String data_3;
    private String data_4;
    int data_5;

    private String currentUser;

    private Context appContext;

    ArrayList<Message> messageList;
    FloatingActionButton floatingButton;
    ImageView profileImg;

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.d(TAG, "onActivityResult: ");

            if(result.getResultCode() == 1){

                Intent i = result.getData();

                if(i != null){

                    data_1 = currentUser;
                    data_2 = i.getStringExtra("a");

                    String avatar = currentUserAvatar(currentUser);

                    data_3 = avatar;

                    data_4 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                    data_5 = 1;

                    RecyclerView rv = findViewById(R.id.messagesRecycler);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainPageActivity.this, LinearLayoutManager.VERTICAL, false);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    rv.setLayoutManager(layoutManager);

                    Message m = new Message(data_1, data_2, data_3, data_4, data_5);
                    messageList.add(m);

                    BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    db.execSQL("INSERT INTO " + EstructuraBBDD.TABLE_MESSAGES + " (" + EstructuraBBDD.COLUMN_USER_NAME + ", "
                            + EstructuraBBDD.COlUMN_MESSAGE + ", " + EstructuraBBDD.COLUMN_MESSAGE_DATE + ", " + EstructuraBBDD.COLUMN_USER_AVATAR + ", " + EstructuraBBDD.COLUMN_ID_USER + ") VALUES ('" + data_1 + "', '"
                            + data_2 + "', '" + data_4 + "', '" + data_3 + "', '" + data_5 + "');");

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

        messageList = new ArrayList<Message>();

        floatingButton = findViewById(R.id.floatingButton);

        currentUser = getIntent().getStringExtra("currentUser");

        appContext = getApplicationContext();

        profileImg = findViewById(R.id.profileImg);

        String currentAvatar = currentUserAvatar(currentUser);
        String avatarNoExtension = currentAvatar.substring(0, currentAvatar.length() - 4);

        String uri = "@drawable/" + avatarNoExtension;
        int imageResource = getResources().getIdentifier(uri, "drawable", getPackageName());
        Drawable res = getResources().getDrawable(imageResource);

        profileImg.setImageDrawable(res);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainPageActivity.this, MessageActivity.class);
                activityLauncher.launch(i);

            }
        });

        RecyclerView rv = findViewById(R.id.messagesRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainPageActivity.this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);

        BaseDatosHelper dbHelper = new BaseDatosHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_USER_AVATAR + ", " + EstructuraBBDD.COlUMN_MESSAGE
                + ", " + EstructuraBBDD.COLUMN_USER_NAME + ", " + EstructuraBBDD.COLUMN_MESSAGE_DATE + ", " + EstructuraBBDD.COLUMN_ID_USER + " FROM "
                + EstructuraBBDD.TABLE_MESSAGES + "", null);

        if(cursor.getCount() > 1){

            cursor.moveToFirst();

            while (!cursor.isAfterLast()){

                String avatar = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_AVATAR));
                String message = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COlUMN_MESSAGE));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USER_NAME));
                String messageDate = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_MESSAGE_DATE));
                int idUser = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_ID_USER));

                Message m = new Message(username, message, avatar, messageDate, idUser);
                messageList.add(m);

                cursor.moveToNext();

                RecyclerAdapter adapter = new RecyclerAdapter(messageList, appContext, username);
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
            messageList.add(m);

            RecyclerAdapter adapter = new RecyclerAdapter(messageList, appContext, username);
            rv.setAdapter(adapter);

        }

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainPageActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

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

}