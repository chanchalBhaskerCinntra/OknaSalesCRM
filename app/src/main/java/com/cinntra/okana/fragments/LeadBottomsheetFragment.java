package com.cinntra.okana.fragments;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.BottomSheetBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.newapimodel.LeadValue;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;



public class LeadBottomsheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{


    private static final String TAG = "TAg";
//    @BindView(R.id.whatsapp_view)
//    TextView whatsapp_view;
//    @BindView(R.id.callView)
//    TextView callView;
//    @BindView(R.id.messageView)
//    TextView messageView;
//    @BindView(R.id.emailView)
//    TextView emailView;
//    @BindView(R.id.btnCancelDialog)
//    TextView btnCancelDialog;
    Context context;

    LeadValue leadValue;
    BottomSheetBinding binding;
    public LeadBottomsheetFragment(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     binding= BottomSheetBinding.inflate(inflater,container,false);
       View v = inflater.inflate(R.layout.bottom_sheet,container,false);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            leadValue = b.getParcelable(Globals.LeadDetails);
        }

     //   ButterKnife.bind(this,v);
        binding.callView.setText("Call: "+leadValue.getPhoneNumber());
        binding.emailView.setOnClickListener(this);
        binding.whatsappView.setOnClickListener(this);
        binding.messageView.setOnClickListener(this);
        binding.callView.setOnClickListener(this);
        binding.btnCancelDialog.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.whatsapp_view:

                openWhatsapp(leadValue.getPhoneNumber());

                break;
            case R.id.callView:
                if(!leadValue.getPhoneNumber().isEmpty()){

                    Dexter.withActivity(getActivity())
                            .withPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_PHONE_STATE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    if (report.areAllPermissionsGranted()) {
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:" + leadValue.getPhoneNumber()));
//                                    context.startActivity(Intent.createChooser(callIntent, "Choose App"));
                                        context.startActivity(callIntent);
                                        Globals.CallLeadID = leadValue.getId();
                                        Globals.CallPhoneNum = leadValue.getPhoneNumber();

                                 /*       Intent serviceIntent = new Intent(getContext(), CallRecorder.class);
                                        serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startService(serviceIntent);
*/
                                        dismiss();
                                             } else {
                                        Toast.makeText(getContext(), "Please enable permission", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();



                }else {
                    Toast.makeText(context,"Mobile number not found",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.messageView:
                if(!leadValue.getPhoneNumber().isEmpty()) {
                    /*Uri smsUri = Uri.parse("tel:" + leadValue.getPhoneNumber());
                    Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                    intent.setType("vnd.android-dir/mms-sms");
                    context.startActivity(intent);*/
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", leadValue.getPhoneNumber(), null)));

                }else{
                    Toast.makeText(getContext(),"No Mobile Number Found", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.emailView:
               if(!leadValue.getEmail().isEmpty()){
                   Intent intent = new Intent(Intent.ACTION_SENDTO);
                   intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                   intent.putExtra(Intent.EXTRA_EMAIL,  new String[]{leadValue.getEmail()});
                   intent.putExtra(Intent.EXTRA_SUBJECT, "");
                   /*if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                       startActivity(intent);
                   }*/
                   startActivity(intent);
               }else{
                   Toast.makeText(getContext(),"No Email Address Found", Toast.LENGTH_SHORT).show();
               }

                break;
            case R.id.btnCancelDialog:
                dismiss();
                break;
        }
    }

    private void openWhatsapp(String phoneNumber) {
        try {
          //  phoneNumber = phoneNumber.replace(" ", "").replace("+", "");

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", "91"+phoneNumber+"@s.whatsapp.net");
            context.startActivity(sendIntent);

        } catch(Exception e) {
            Log.e(TAG, "ERROR_OPEN_MESSANGER"+e.toString());
        }
    }
}
