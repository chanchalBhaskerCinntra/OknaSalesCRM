package com.cinntra.okana.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.cinntra.okana.R;
import com.cinntra.okana.fragments.Opportunity_Detail_NewFragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.Company;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.viewModel.CompanyViewHolder;
import com.cinntra.okana.viewModel.ProductViewholder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends ExpandableRecyclerViewAdapter<CompanyViewHolder, ProductViewholder> {
    List<? extends ExpandableGroup> itemList;
    List<NewOpportunityRespose> products;
    List<NewOpportunityRespose> open;
    List<NewOpportunityRespose> needanalysis;
    List<NewOpportunityRespose> quotation;
    List<NewOpportunityRespose> negotiation;
    List<NewOpportunityRespose> order;
    Context context;
    public ProductAdapter(List<? extends ExpandableGroup> groups,
                          List<NewOpportunityRespose> open,List<NewOpportunityRespose> needanalysis,
                          List<NewOpportunityRespose> quotation, List<NewOpportunityRespose> negotiaon,
                          List<NewOpportunityRespose> order,List<NewOpportunityRespose> allist,
                          Context context) {
        super(groups);
        this.itemList = groups;
        this.products = allist;
        this.open = open;
        this.needanalysis = needanalysis;
        this.quotation = quotation;
        this.negotiation = negotiaon;
        this.order = order;
        this.context = context;
        this.tempList = new ArrayList<NewOpportunityRespose>();
        this.tempList.addAll(allist);
    }
    List<NewOpportunityRespose> tempList =null ;
    public void AllData()
    {
        products.clear();
        products.addAll(tempList);
        notifyDataSetChanged();
    }
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        products.clear();
        if (charText.length() == 0) {
            products.addAll(tempList);
        } else {
            for (NewOpportunityRespose st : tempList) {
                /*if(st.getOpportunityName()!=null&&!st.getOpportunityName().isEmpty()) {
                    if (st.getCustomerName().toLowerCase().trim().contains(charText)) {*/
                if(st.getCardCode()!=null&&!st.getCardCode().isEmpty()&&st.getOpportunityName()!=null&&!st.getOpportunityName().isEmpty()) {
                    if (st.getCardCode().toLowerCase().trim().contains(charText)||st.getOpportunityName().toLowerCase().trim().contains(charText)) {
                        products.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public void Favfilter(String charText)
    {
        products.clear();

            for (NewOpportunityRespose st : tempList) {
                if(st.getUFav()!=null&&!st.getUFav().isEmpty()) {
                    if (st.getUFav().toLowerCase().trim().equalsIgnoreCase(charText)) {
                        products.add(st);
                    }

            }

        }
        notifyDataSetChanged();
    }
    @Override
    public CompanyViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_list,parent,false);

        return new CompanyViewHolder(view);
    }

    @Override
    public ProductViewholder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opportunity_new_screen,parent,false);

        return new ProductViewholder(view,context);
    }

    @Override
    public void onBindChildViewHolder(ProductViewholder holder, int flatPosition, ExpandableGroup group, int childIndex) {
    final NewOpportunityRespose product = (NewOpportunityRespose) group.getItems().get(childIndex);
    holder.onBind(product);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Bundle b = new Bundle();
         b.putParcelable(Globals.OpportunityItem,product);
//          Opportunity_Update_Fragment fragment = new Opportunity_Update_Fragment();
         Opportunity_Detail_NewFragment fragment = new Opportunity_Detail_NewFragment();
         fragment.setArguments(b);
         FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
         transaction.replace(R.id.quatoes_main_container, fragment);
         transaction.addToBackStack("Back");
         transaction.commit();
        }
    });

    }


    @Override
    public void onBindGroupViewHolder(CompanyViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Company company = (Company)group;
        holder.onBind(company);

    }


}
