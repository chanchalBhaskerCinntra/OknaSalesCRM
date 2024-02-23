package com.cinntra.okana.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.newapimodel.AttachDocument;


import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PreviousImageViewAdapter extends RecyclerView.Adapter<PreviousImageViewAdapter.ContactViewHolder> {

    Context context;
    ArrayList<AttachDocument> UriList;
    String Flag = "";
    private DeleteItemClickListener mListener;

    public interface DeleteItemClickListener {
        void onDeleteItemClick(int id, Dialog dialog);
    }

    public void setOnDeleteItemClick(DeleteItemClickListener listener) {
        mListener = listener;
    }

    public PreviousImageViewAdapter(Context context, List<AttachDocument> UriList, String flag) {
        this.context = context;
        this.UriList = (ArrayList<AttachDocument>) UriList;
        this.Flag = flag;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageadapterscree, parent, false);
        return new ContactViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        // String ext = Globals.getFileExtension(UriList.get(position).getFileName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.icon)
                .error(R.drawable.ic_invoice);
        String ImageUrl = Globals.ImageURl + UriList.get(position).getFile();

        Glide.with(context).load(ImageUrl).apply(options).into(holder.loadimage);

        holder.loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if(ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("jpeg")||ext.equalsIgnoreCase("png")||ext.equalsIgnoreCase("svg")){

                }else{

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImageUrl));
                    context.startActivity(browserIntent);
                }*/
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImageUrl));
                context.startActivity(browserIntent);
            }
        });


        if (Flag == "LeadDetail" || Flag == "Quotation_Detail" || Flag == "Order_Detail" || Flag == "Payment_Detail"){
            holder.cross.setVisibility(View.VISIBLE);
        }else {
            holder.cross.setVisibility(View.GONE);
        }


        //todo delete image
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopupDialog(UriList.get(holder.getAdapterPosition()).getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return UriList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {


        ImageView loadimage, cross;

        public ContactViewHolder(@NonNull View itemView) {

            super(itemView);
            loadimage = itemView.findViewById(R.id.loadimage);
            cross = itemView.findViewById(R.id.cross);


        }
    }


    private void showDeletePopupDialog(int attachID) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You Want to Delete!")
                .setConfirmText("Yes,Delete!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        mListener.onDeleteItemClick(attachID, sDialog);
                    }
                })

                .show();
    }


}
