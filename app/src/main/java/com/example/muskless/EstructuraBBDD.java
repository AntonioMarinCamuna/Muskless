package com.example.muskless;

public class EstructuraBBDD {

    //Table users
    public static final String TABLE_USERS = "registeredUsers";
    public static final String COLUMN_ID = "userId";
    public static final String COLUMN_NAME = "userName";
    public static final String COLUMN_USERNAME = "userUsername";
    public static final String COLUMN_EMAIL = "userEmail";
    public static final String COLUMN_PASSWORD = "userPassword";
    public static final String COLUMN_BIRTHDAY = "userBirthday";
    public static final String COLUMN_AVATAR = "userAvatar";

    //Table messages
    public static final String TABLE_MESSAGES = "publishedMessages";
    public static final String COLUMN_ID_MESSAGE = "messageId";
    public static final String COLUMN_ID_USER = "userId";
    public static final String COlUMN_MESSAGE = "message";
    public static final String COLUMN_MESSAGE_DATE = "messageDate";

    public static final String SQL_CREATE_ENTRIES_USERS =
            "CREATE TABLE " + EstructuraBBDD.TABLE_USERS + " (" +
                    EstructuraBBDD.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    EstructuraBBDD.COLUMN_NAME + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_USERNAME + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_EMAIL + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_PASSWORD + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_BIRTHDAY + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_AVATAR + " TEXT NOT NULL)";

    public static final String SQL_CREATE_ENTRIES_MESSAGES =
            "CREATE TABLE " + EstructuraBBDD.TABLE_MESSAGES + " (" +
                    EstructuraBBDD.COLUMN_ID_MESSAGE + " INTEGER PRIMARY KEY," +
                    EstructuraBBDD.COlUMN_MESSAGE + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_MESSAGE_DATE + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_ID_USER + " INTEGER," +
                    "FOREIGN KEY (" + COLUMN_ID_USER + ") REFERENCES "
                    + TABLE_USERS + " (" + COLUMN_ID + "))";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_USERS;




}
