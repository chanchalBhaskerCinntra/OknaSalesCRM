package com.cinntra.okana.adapters.bpAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.cinntra.okana.activities.bpActivity.UpdateContactActivity;
import com.cinntra.okana.databinding.BpBranchAddressLayoutBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPModel.BussinessPartnerDetailModel;

import java.util.ArrayList;

public class BPContactPersonAdapter extends RecyclerView.Adapter<BPContactPersonAdapter.ViewHolder>{

    ArrayList<BussinessPartnerDetailModel.ContactEmployee> dataArrayList_gl = new ArrayList<>();
    Context mContext;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position, BussinessPartnerDetailModel.ContactEmployee data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public BPContactPersonAdapter(Context context, ArrayList<BussinessPartnerDetailModel.ContactEmployee> arrayList) {
        this.mContext = context;
        this.dataArrayList_gl = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BpBranchAddressLayoutBinding binding = BpBranchAddressLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BussinessPartnerDetailModel.ContactEmployee modelData = dataArrayList_gl.get(position);

        holder.binding.branchAddressLayout.setVisibility(View.GONE);
        holder.binding.branchContactPersonLayout.setVisibility(View.VISIBLE);

        holder.binding.tvContactPerson.setText(modelData.getFirstName());
        holder.binding.tvContact.setText(modelData.getMobilePhone());


        holder.binding.callView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + modelData.getMobilePhone()));
                mContext.startActivity(intent);
            }
        });

        holder.binding.chatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{modelData.getE_Mail()}); // Assuming ticketdata is an object with a method getContactEmail()
                PackageManager packageManager = mContext.getPackageManager();
                if (intent.resolveActivity(packageManager) != null) {
                    mContext.startActivity(intent);
                } else {
                    Globals.showErrorMessage(mContext, "No Gmail Found");
                    // Handle the case where Gmail or the email application is not installed on the device
                }
            }
        });


        holder.binding.cardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateContactActivity.class);
                intent.putExtra("id", modelData.getId());
                intent.putExtra("cardCode", modelData.getCardCode());
                mContext.startActivity(intent);
            }
        });

        holder.binding.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position, modelData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataArrayList_gl.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private BpBranchAddressLayoutBinding binding;

        public ViewHolder(@NonNull BpBranchAddressLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}

