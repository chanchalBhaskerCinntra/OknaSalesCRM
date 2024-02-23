package com.cinntra.okana.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.OwnerItem;
import java.util.List;
import static android.app.Activity.RESULT_OK;


public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.ViewHolder> {
    Context context;
    List<OwnerItem> ownerList;
    public OwnerAdapter(Context context, List<OwnerItem> ownerList)
       {
    this.context   = context;
    this.ownerList = ownerList;

      }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View rootView = LayoutInflater.from(context).inflate(R.layout.owner_list_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.title.setText(ownerList.get(position).getFirstName()+" "+ownerList.get(position).getMiddleName()+" "+ownerList.get(position).getLastName());



    }

    @Override
    public int getItemCount() {
        return ownerList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
       public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent();
                   intent.putExtra(Globals.OwnerItemData, ownerList.get(getAdapterPosition()));
                   ((AppCompatActivity)context).setResult(RESULT_OK, intent);
                   ((AppCompatActivity)context).finish();
               }
           });



        }
    }
}
