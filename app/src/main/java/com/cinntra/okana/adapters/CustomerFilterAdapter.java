package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.fragments.Open_Order;
import com.cinntra.okana.fragments.Quotation_Open_Fragment;
import com.cinntra.okana.fragments.ReminderSelectionSheet;
import com.cinntra.okana.interfaces.CommentStage;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;

import java.util.List;


public class CustomerFilterAdapter extends RecyclerView.Adapter<CustomerFilterAdapter.ViewHolder> {
    Context context;
    List<BusinessPartnerData> leadValueList;
    CommentStage changeTeam;

    public CustomerFilterAdapter(Context context, Quotation_Open_Fragment c, List<BusinessPartnerData> leadValueList) {
        this.context =context;
        this.leadValueList = leadValueList;
        this.changeTeam = c;

    }

    public CustomerFilterAdapter(Context context, Open_Order c, List<BusinessPartnerData> utypelist) {
        this.context =context;
        this.leadValueList = utypelist;
        this.changeTeam =  c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusinessPartnerData lv = leadValueList.get(position);
        holder.sourcecheck.setText(lv.getCardName());


           }




    private void showBottomSheet(int id,String name)
    {

        ReminderSelectionSheet bottomSheetFragment = new ReminderSelectionSheet(id,name);
        bottomSheetFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), bottomSheetFragment.getTag());

    }



    @Override
    public int getItemCount() {
        return leadValueList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView sourcecheck;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sourcecheck = itemView.findViewById(android.R.id.text1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTeam.stagecomment(leadValueList.get(getAdapterPosition()).getCardName(),"Type");
                }
            });



        }
    }
}