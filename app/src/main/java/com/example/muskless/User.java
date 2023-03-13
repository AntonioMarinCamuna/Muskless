package com.example.muskless;

public class User {

    //Datos relativos a un usuario registrado en la app.
    private String userName;
    private String userUsername;
    private String userMail;
    private String userPassword;
    private String userBirthday;
    private String userAvatar;

    /*
    * Constructor de la clase encargado de asignar la información del usuario a las variables de datos.
    * */
    public User(String userName, String userUsername, String userMail, String userPassword, String userBirthday, String userAvatar) {
        this.userName = userName;
        this.userUsername = userUsername;
        this.userMail = userMail;
        this.userPassword = userPassword;

    }

    //Getters y Setters de los datos de cada usuario.
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

}
