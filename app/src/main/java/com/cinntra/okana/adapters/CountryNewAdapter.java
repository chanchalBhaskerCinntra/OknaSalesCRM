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
import com.cinntra.okana.model.Countries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryNewAdapter extends RecyclerView.Adapter<CountryNewAdapter.ViewHolder> {
    Context context;
    ArrayList<Countries> DBList;
    DatabaseClick databaseClick;

  public CountryNewAdapter(Context context, ArrayList<Countries> DBList, DatabaseClick databaseClick)
    {
  this.context = context;
  this.DBList  = DBList;
  this.databaseClick = databaseClick;
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


    public void setList(ArrayList<Countries> DBList)
    {
        tempList      = new ArrayList<Countries>();
        tempList.addAll(DBList);
    }

    List<Countries> tempList =null ;
    public void filter(String charText)
       {


        charText = charText.toLowerCase(Locale.getDefault());
        DBList.clear();
        if (charText.length() == 0) {
            DBList.addAll(tempList);
        } else {
            for (Countries st : tempList) {
                if(st.getName()!=null&&!st.getName().isEmpty()) {
                    if (st.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        DBList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }
}
