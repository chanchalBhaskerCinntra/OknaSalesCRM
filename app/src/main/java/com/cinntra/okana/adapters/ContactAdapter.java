package com.cinntra.okana.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.cinntra.okana.R;
import com.cinntra.okana.model.ContactPersonData;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter <ContactAdapter.ContactViewHolder>{

    Activity context;
    ArrayList<ContactPersonData> contactEmployeesList =new ArrayList<>();
    public ContactAdapter(Activity context, ArrayList<ContactPersonData> contactEmployeesList) {
        this.context = context;
        this.contactEmployeesList.addAll(contactEmployeesList);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.contact_adapter,parent,false);
        return new ContactViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactPersonData contactPersonData = contactEmployeesList.get(position);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRound(Character.toString(contactPersonData.getFirstName().charAt(0)).toUpperCase(), color1);
        holder.profile_pic.setImageDrawable(drawable);
        holder.name_value.setText(contactPersonData.getFirstName()+" "+contactPersonData.getLastName());
        holder.role_val.setText(contactPersonData.getMobilePhone());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!contactPersonData.getMobilePhone().isEmpty()){


                Dexter.withActivity(context)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                // permission is granted, open the camera
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:" + contactPersonData.getMobilePhone()));
                                context.startActivity(Intent.createChooser(callIntent, "Choose App"));
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                // check for permanent denial of permission
                                if (response.isPermanentlyDenied()) {
                                    // navigate user to app settings
                                    Toast.makeText(context,"Enable permission from app Setting",Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
                }else {
                    Toast.makeText(context,"Mobile number not found",Toast.LENGTH_LONG).show();
                }

            }
        });

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!contactPersonData.getMobilePhone().isEmpty()) {
                    Uri smsUri = Uri.parse("tel:" + contactPersonData.getMobilePhone());
                    Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                    intent.setType("vnd.android-dir/mms-sms");
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactEmployeesList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView name_value,role_val;
        ImageView profile_pic,call,chat;
        public ContactViewHolder(@NonNull View itemView) {

            super(itemView);
            role_val = itemView.findViewById(R.id.role_val);
            name_value= itemView.findViewById(R.id.name_value);
            profile_pic= itemView.findViewById(R.id.profile_pic);
            call= itemView.findViewById(R.id.call);
            chat= itemView.findViewById(R.id.chat);

        }
    }
}
