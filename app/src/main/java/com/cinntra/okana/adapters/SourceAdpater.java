package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.fragments.ReminderSelectionSheet;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.LeadTypeData;

import java.util.List;


public class SourceAdpater extends RecyclerView.Adapter<SourceAdpater.ViewHolder> {
    Context context;
    List<LeadTypeData> leadValueList;
    public SourceAdpater(Context c, List<LeadTypeData> leadValueList) {
        this.context =c;
        this.leadValueList = leadValueList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.multiselect_source,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeadTypeData lv = leadValueList.get(position);
        holder.sourcecheck.setText(lv.getName());
        holder.sourcecheck.setChecked(leadValueList.get(position).isIschecked());

        holder.sourcecheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    leadValueList.get(position).setIschecked(true);
                    Globals.sourcechecklist.add(leadValueList.get(position).getName());
                }else{
                    leadValueList.get(position).setIschecked(false);
                    Globals.sourcechecklist.remove(leadValueList.get(position).getName());

                }
            }
        });
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
       CheckBox sourcecheck;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sourcecheck = itemView.findViewById(R.id.sourcecheck);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  if(!leadValueList.get(getAdapterPosition()).isIschecked()){
                        leadValueList.get(getAdapterPosition()).setIschecked(true);
                        Globals.sourcechecklist.add(leadValueList.get(getAdapterPosition()).getName());
                    }else{
                        leadValueList.get(getAdapterPosition()).setIschecked(false);
                        Globals.sourcechecklist.remove(leadValueList.get(getAdapterPosition()).getName());

                    }*/
                }
            });



        }
    }
}