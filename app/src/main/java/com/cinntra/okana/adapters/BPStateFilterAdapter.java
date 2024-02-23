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
import com.cinntra.okana.fragments.CustomersFragment;
import com.cinntra.okana.fragments.ReminderSelectionSheet;
import com.cinntra.okana.interfaces.CommentStage;
import com.cinntra.okana.model.StateData;

import java.util.List;


public class BPStateFilterAdapter extends RecyclerView.Adapter<BPStateFilterAdapter.ViewHolder> {
    Context context;
    List<StateData> leadValueList;
    CommentStage changeTeam;

    public BPStateFilterAdapter(Context context, CustomersFragment c, List<StateData> leadValueList) {
        this.context =context;
        this.leadValueList = leadValueList;
        this.changeTeam = c;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StateData lv = leadValueList.get(position);
        holder.sourcecheck.setText(lv.getName());


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
                    changeTeam.stagecomment(leadValueList.get(getAdapterPosition()).getName(),"State");
                }
            });



        }
    }
}