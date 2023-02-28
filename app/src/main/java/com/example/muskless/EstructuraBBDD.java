package com.example.muskless;

public class EstructuraBBDD {

    public static final String TABLE_USERS = "registeredUsers";
    public static final String COLUMN_ID = "userId";
    public static final String COLUMN_NAME = "userName";
    public static final String COLUMN_USERNAME = "userUsername";
    public static final String COLUMN_EMAIL = "userEmail";
    public static final String COLUMN_PASSWORD = "userPassword";
    public static final String COLUMN_BIRTHDAY = "userBirthday";
    public static final String COLUMN_AVATAR = "userAvatar";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EstructuraBBDD.TABLE_USERS + " (" +
                    EstructuraBBDD.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    EstructuraBBDD.COLUMN_NAME + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_USERNAME + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_EMAIL + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_PASSWORD + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_BIRTHDAY + " TEXT NOT NULL," +
                    EstructuraBBDD.COLUMN_AVATAR + " TEXT NOT NULL)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_USERS;




}
