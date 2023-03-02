package com.example.muskless;

import android.content.Context;
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

    ArrayList<Message> messageList;

    public RecyclerAdapter(ArrayList<Message> messageList){

        this.messageList = messageList;

    }

    public class ViewHolderMessages extends RecyclerView.ViewHolder {

        TextView user;
        TextView txt;
        ImageView img;
        TextView date;

        public ViewHolderMessages(@NonNull View itemView) {
            super(itemView);

            user = (TextView) itemView.findViewById(R.id.usrNameDisplay);
            txt = (TextView) itemView.findViewById(R.id.usrTxtDisplay);
            img = itemView.findViewById(R.id.usrMessageImg);
            date = (TextView) itemView.findViewById(R.id.dateText);

        }

        public void bind(Message message) {

            user.setText(message.getUser());
            txt.setText(message.getTxt());
            date.setText(message.getDate());
            img.setImageResource(R.drawable.avatar_cinco);

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

    public static Drawable getImage(Context c, String ImageName) {

        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));

    }

}
