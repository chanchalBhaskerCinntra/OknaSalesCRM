package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cinntra.okana.R;
import com.cinntra.okana.model.StagesItem;

import java.util.List;


public class StageSpinnerAdapter extends BaseAdapter {
    Context context;
    List<StagesItem> stagesList;
    LayoutInflater inflter;
   public StageSpinnerAdapter(Context context, List<StagesItem> stagesList)
      {
   this.context    = context;
   this.stagesList = stagesList;
   inflter = (LayoutInflater.from(context));
      }
    @Override
    public int getCount() {
        return stagesList.size();
    }

    @Override
    public StagesItem getItem(int position) {
        return stagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.stages_spinner_item, null);
        TextView title = (TextView)v.findViewById(R.id.title);
        title.setText(stagesList.get(position).getName());
        return v;
    }
}
