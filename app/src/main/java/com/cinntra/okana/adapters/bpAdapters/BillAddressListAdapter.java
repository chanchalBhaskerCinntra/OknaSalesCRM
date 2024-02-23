package com.cinntra.okana.adapters.bpAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.activities.bpActivity.UpdateBranchActivity;
import com.cinntra.okana.databinding.BpBranchAddressLayoutBinding;
import com.cinntra.okana.model.OpportunityModels.OppAddressResponseModel;

import java.util.ArrayList;

public class BillAddressListAdapter extends RecyclerView.Adapter<BillAddressListAdapter.ViewHolder>{

    ArrayList<OppAddressResponseModel.Data> dataArrayList_gl = new ArrayList<>();
    Context mContext;
    String CardType = "";
    public OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position, OppAddressResponseModel.Data data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public BillAddressListAdapter(Context context, ArrayList<OppAddressResponseModel.Data> arrayList, String cardType) {
        this.mContext = context;
        this.dataArrayList_gl = arrayList;
        this.CardType = cardType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BpBranchAddressLayoutBinding binding = BpBranchAddressLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OppAddressResponseModel.Data modelData = dataArrayList_gl.get(position);

        holder.binding.branchAddressLayout.setVisibility(View.VISIBLE);

        holder.binding.tvSolID.setText(modelData.getBranchName());
        holder.binding.tvLocation.setText(modelData.getCity() + ", " + modelData.getU_STATE() + " , " + modelData.getU_COUNTRY());

        holder.binding.cardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateBranchActivity.class);
                intent.putExtra("id", modelData.getId());
                intent.putExtra("cardCode", modelData.getBPCode());
                intent.putExtra("flag", "bo_BillTo");
                intent.putExtra("cardType", CardType);
                intent.putExtra("BPLID", modelData.getBPID());
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
