package com.example.muskless;

public class Message {

    private String user;
    private String txt;
    private String img;

    public Message(String user, String txt, String img) {
        this.user = user;
        this.txt = txt;
        this.img = img;
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
}
