package com.cinntra.okana.adapters.bpAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPModel.AtatchmentListModel;

import java.util.ArrayList;

public class BPAttachmentAdapter extends RecyclerView.Adapter<BPAttachmentAdapter.ContactViewHolder> {

    Context context;
    ArrayList<AtatchmentListModel.Data> UriList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position, AtatchmentListModel.Data data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BPAttachmentAdapter(Context context, ArrayList<AtatchmentListModel.Data> UriList) {
        this.context = context;
        this.UriList = (ArrayList<AtatchmentListModel.Data>) UriList;
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
                .placeholder(R.drawable.logo)
                .error(R.drawable.ic_invoice);
        String ImageUrl = Globals.ImageURl + UriList.get(position).getFile();

        Glide.with(context).load(ImageUrl).apply(options).into(holder.loadimage);
        holder.loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImageUrl));
                context.startActivity(browserIntent);
            }
        });

        holder.cross.setVisibility(View.VISIBLE);
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position, UriList.get(position));
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


}
