package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.DataBase;

import java.util.ArrayList;

public class DataBasesListAdapter extends RecyclerView.Adapter<DataBasesListAdapter.ViewHolder> {
    Context context;
    ArrayList<DataBase> DBList;
    DatabaseClick databaseClick;

    public DataBasesListAdapter(Context context,ArrayList<DataBase> DBList)
    {
  this.context = context;
  this.DBList  = DBList;
  databaseClick = (DatabaseClick)context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View rootView = LayoutInflater.from(context).inflate(R.layout.database_list_item,parent,false);
    return new ViewHolder(rootView);
      }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
     {
         holder.title.setText(DBList.get(position).getName());
     }

    @Override
    public int getItemCount()
      {
    return DBList.size();
      }



    class ViewHolder extends RecyclerView.ViewHolder {
          private TextView title;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseClick.onClick(getAdapterPosition());

                }
            });

        }
    }
}
