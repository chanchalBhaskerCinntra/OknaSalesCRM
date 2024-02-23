package com.cinntra.okana.viewModel;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cinntra.okana.R;
import com.cinntra.okana.fragments.ChatterFragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.UpdateFavourites;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.webservices.NewApiClient;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewholder extends ChildViewHolder {
    TextView item_title,price,item_date,quanity,name,follow_up,mode1;
    ImageView star,chat;
    LikeButton star_button;
    Context context;
    public ProductViewholder(View itemView,Context context) {
        super(itemView);
        item_title = itemView.findViewById(R.id.item_title);
        follow_up = itemView.findViewById(R.id.follow_up);
        mode1 = itemView.findViewById(R.id.mode1);
        item_date  = itemView.findViewById(R.id.item_date);
        quanity    = itemView.findViewById(R.id.quanity);
        price      = itemView.findViewById(R.id.price);
        name       = itemView.findViewById(R.id.name);
        star       = itemView.findViewById(R.id.star);
        star_button       = itemView.findViewById(R.id.star_button);
        chat       = itemView.findViewById(R.id.chat);
        this.context = context;


    }

    public void onBind(NewOpportunityRespose product){
        item_title.setText(product.getOpportunityName());
        item_date.setText(product.getClosingDate());
        item_date.setText(product.getPredictedClosingDate());
        price.setText(" : "+product.getMaxLocalTotal());
        name.setText(product.getCardCode());

        mode1.setText(product.getCurrentStageName());

        if(product.getUFav().equalsIgnoreCase("Y")){
           star_button.setLiked(true);

        }else{
            star_button.setLiked(false);
        }

        follow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFollowUpdialog();
            }
        });



        star_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);
                UpdateFavourites obj1 = new UpdateFavourites();
                obj1.setU_FAV("Y");
                obj1.setId(product.getId());
                updatefavourites(obj1);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
                UpdateFavourites obj1 = new UpdateFavourites();
                obj1.setU_FAV("N");
                obj1.setId(product.getId());
                updatefavourites(obj1);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Globals.OpportunityItem,product);
                ChatterFragment chatterFragment = new ChatterFragment();
                chatterFragment.setArguments(bundle);
                FragmentTransaction chattransaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                chattransaction.replace(R.id.quatoes_main_container, chatterFragment);
                chattransaction.addToBackStack("Back");
                chattransaction.commit();

            }

        });


    }

    private void openFollowUpdialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.followup_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        EditText date_value = dialog.findViewById(R.id.date_value);
        EditText time_value = dialog.findViewById(R.id.time_value);
        Button add = dialog.findViewById(R.id.add);

        date_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.selectDate(dialog.getContext(),date_value);

            }
        });
        time_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.selectTime(context,time_value);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });




        dialog.show();
    }

    private void updatefavourites(UpdateFavourites in)
    {
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().updateoppFavorite(in);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                assert response.body() != null;
                if(response.code()==200)
                {

                }

            }
            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
