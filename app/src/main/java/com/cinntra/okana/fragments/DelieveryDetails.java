package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.TrackDeleiveryDetailBinding;


public class DelieveryDetails extends Fragment implements  View.OnClickListener {
//    @BindView(R.id.checkbox1)
//    CheckBox checkbox;
//    @BindView(R.id.edit_ship_type)
//    ImageView edit_ship_type;
//    @BindView(R.id.edit_ship)
//    ImageView edit_ship;
//    @BindView(R.id.ship_mode_edit)
//    ImageView ship_mode_edit;
//    @BindView(R.id.head_title)
//    TextView head_title;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TrackDeleiveryDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       binding=TrackDeleiveryDetailBinding.inflate(inflater,container,false);
        View v=inflater.inflate(R.layout.track_deleivery_detail, container, false);
        //ButterKnife.bind(this,v);
        setDefaults();
        binding.checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.editShip.setVisibility(View.VISIBLE);
                    binding.editShipType.setVisibility(View.VISIBLE);
                    binding.shipModeEdit.setVisibility(View.VISIBLE);
                }else{
                    binding.editShip.setVisibility(View.GONE);
                    binding.editShipType.setVisibility(View.GONE);
                    binding.shipModeEdit.setVisibility(View.GONE);
                }
            }
        });


        return binding.getRoot();
    }

    private void setDefaults() {
       binding.headerBottomroundEdit.headTitle .setText(R.string.delivery_detail);
        binding.shipModeEdit.setOnClickListener(this);
        binding.editShip.setOnClickListener(this);
        binding.editShipType.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ship_mode_edit:
                ship_mode_dialog();
                break;
            case R.id.edit_ship:
                ship_edit_dialog();
                break;
            case R.id.edit_ship_type:
                ship_type_diualog();
                break;
        }
    }

    private void ship_type_diualog() {
        Dialog dialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog =layoutInflater.inflate(R.layout.shipping_type_alertbox,null);
        dialog.setContentView(custom_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void ship_mode_dialog() {
        Dialog dialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog =layoutInflater.inflate(R.layout.shipped_with_alertbox,null);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void ship_edit_dialog() {
        Dialog dialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog =layoutInflater.inflate(R.layout.shipping_address_alertbox,null);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
