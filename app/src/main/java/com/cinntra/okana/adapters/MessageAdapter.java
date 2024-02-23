package com.cinntra.okana.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.ChatModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<com.cinntra.okana.adapters.MessageAdapter.ViewHolder> {

    Context context;
    ArrayList<ChatModel> messagelist;

    public MessageAdapter(Context context, ArrayList<ChatModel> messagelist) {
        this.context = context;
        this.messagelist = messagelist;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.chatter_layout_new, parent, false);
        return new com.cinntra.okana.adapters.MessageAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatModel chatModel = messagelist.get(position);


        holder.message.setText(chatModel.getMessage());
        //  if(chatModel.getSourceType().equalsIgnoreCase("Followup"))
        if (chatModel.getComm_mode().trim().equalsIgnoreCase("Call")) {
            holder.title.setText("Call - " + chatModel.getUpdateDate());
            holder.priority_dot.setImageResource(R.drawable.ic_call);
        } else if (chatModel.getComm_mode().trim().equalsIgnoreCase("Whatsapp")) {
            holder.title.setText("Whatsapp - " + chatModel.getUpdateDate());
            holder.priority_dot.setImageResource(R.drawable.ic_whatsapp);
        } else if (chatModel.getComm_mode().trim().equalsIgnoreCase("SMS")) {
            holder.title.setText("SMS - " + chatModel.getUpdateDate());
            holder.priority_dot.setImageResource(R.drawable.ic_sms);
        } else if (chatModel.getComm_mode().trim().equalsIgnoreCase("E-Mail")) {
            holder.title.setText("E-Mail - " + chatModel.getUpdateDate());
            holder.priority_dot.setImageResource(R.drawable.ic_email_latest);
        } else if (chatModel.getComm_mode().trim().equalsIgnoreCase("Visit")) {
            holder.title.setText("Visit - " + chatModel.getUpdateDate());
            holder.priority_dot.setImageResource(R.drawable.ic_baseline_directions_run_24);
        } else {
            holder.title.setText("SMS - " + chatModel.getUpdateDate());
            holder.priority_dot.setImageResource(R.drawable.ic_sms);
        }

        holder.receivertext.setText(chatModel.getMessage());
        holder.receiverdatetime.setText(chatModel.getUpdateDate() + "," + chatModel.getUpdateTime());
        holder.textimage.setText("" + chatModel.getEmpName().trim().charAt(0));

        //holder.timelineView.setTooltipText(""+chatModel.getEmpName().trim().charAt(0));
        if (chatModel.getEmpId().equalsIgnoreCase(Prefs.getString(Globals.EmployeeID, ""))) {
            holder.receiver_view.setGravity(Gravity.RIGHT);

            holder.textimage.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.messagelayout.getBackground().setColorFilter(Color.parseColor("#956387DA"), PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.receiver_view.setGravity(Gravity.LEFT);
            holder.textimage.setTextColor(context.getResources().getColor(R.color.green));
            holder.messagelayout.getBackground().setColorFilter(Color.parseColor("#9455BD63"), PorterDuff.Mode.SRC_ATOP);

        }
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView receivertext, receiverdatetime, textimage, message, title;
        LinearLayout imageicon;
        LinearLayout receiver_view, messagelayout;
        ImageView priority_dot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageicon = itemView.findViewById(R.id.imageicon);
            receivertext = itemView.findViewById(R.id.receivertext);
            receiverdatetime = itemView.findViewById(R.id.receiverdatetime);
            receiver_view = itemView.findViewById(R.id.receiver_view);
            messagelayout = itemView.findViewById(R.id.messagelayout);
            textimage = itemView.findViewById(R.id.textimage);
            priority_dot = itemView.findViewById(R.id.priority_dot);
            message = itemView.findViewById(R.id.message);
            title = itemView.findViewById(R.id.title);


        }
    }
}
