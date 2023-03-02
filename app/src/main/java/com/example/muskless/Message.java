package com.example.muskless;

public class Message {

    private String user;
    private String txt;
    private String img;
    private String date;
    private int idUser;

    public Message(String user, String txt, String img, String date, int idUser) {
        this.txt = txt;
        this.user = user;
        this.img = img;
        this.date = date;
        this.idUser = idUser;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
