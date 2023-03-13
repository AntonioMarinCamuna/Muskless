package com.example.muskless;

/*
 * Definimos los importes necesarios para el funcionamiento de la aplicación.
 * */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderMessages> {

    //Definimos los elementos empleados en el activity.
    private ArrayList<Message> messageList;
    private Context c;
    private String messageUser;

    /*
    * Constructor de la clase RecyclerAdapter.
    * */
    public RecyclerAdapter(ArrayList<Message> messageList, Context c, String messageUser){

        this.messageList = messageList;
        this.c = c;
        this.messageUser = messageUser;

    }

    /*
    * Clase ViewHolderMessages que extiende de RecyclerView.ViewHolder.
    * */
    public class ViewHolderMessages extends RecyclerView.ViewHolder {

        //Definimos los elementos empleados en la clase.
        private TextView user;
        private TextView txt;
        private ImageView img;
        private TextView date;
        private Context c;

        /*
        * Constructor de la clase ViewHolderMessages.
        * */
        public ViewHolderMessages(@NonNull View itemView) {
            super(itemView);

            //Instanciamos los elementos del activity.
            user = (TextView) itemView.findViewById(R.id.usrNameDisplay);
            txt = (TextView) itemView.findViewById(R.id.usrTxtDisplay);
            img = itemView.findViewById(R.id.usrMessageImg);
            date = (TextView) itemView.findViewById(R.id.dateText);
            c = RecyclerAdapter.this.c.getApplicationContext();

        }

        /*
        * Método bind encargado de "setear" la información del recycler adapter.
        * */
        public void bind(Message message) {

            user.setText(message.getUser());
            txt.setText(message.getTxt());
            date.setText(message.getDate());

            getImage(message, c, img);

        }
    }

    @NonNull
    @Override
    public ViewHolderMessages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_message_row, parent, false);

        return new ViewHolderMessages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMessages holder, int position) {

        holder.bind(messageList.get(position));

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    /*
    * Método encargado de asignar al mensaje la imagen del usuario actual.*/
    public static void getImage(Message message, Context c, ImageView img) {

        String avatar = message.getImg().substring(0, message.getImg().length() - 4);

        String uri = "@drawable/" + avatar;
        int imageResource = c.getResources().getIdentifier(uri, "drawable", c.getPackageName());
        Drawable res = c.getResources().getDrawable(imageResource);

        img.setImageDrawable(res);

    }

}
