package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.PayMentTerm;

import java.util.List;

public class PaymentAdapter extends BaseAdapter {

    Context context;
    List<PayMentTerm> payMentTermList;
    LayoutInflater inflater;
    public PaymentAdapter(Context  context, List<PayMentTerm> payMentTermList) {
        this.context = context;
        this.payMentTermList = payMentTermList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return payMentTermList.size();
    }

    @Override
    public Object getItem(int position) {
        return payMentTermList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflater.inflate(R.layout.stages_spinner_item, null);
        TextView title = (TextView)v.findViewById(R.id.title);
        title.setText(payMentTermList.get(position).getPaymentTermsGroupName());
        return v;
    }
}
