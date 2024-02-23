package com.cinntra.okana.receivers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.Date;

public abstract class CallStateReceiver extends BroadcastReceiver {

    private final String LOG_TAG = "CallStateReceiver";
    private int lastState = TelephonyManager.CALL_STATE_IDLE;
    public static String prevState = TelephonyManager.EXTRA_STATE_IDLE;
    private Date callStartTime;
    private boolean isIncoming;
    private String savedNumber;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        /*Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        String state = bundle.getString(TelephonyManager.EXTRA_STATE);

        if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE)
                && !prevState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {

            showtoast(context,"message");
        }
        prevState = state;
  */    MediaRecorder recorder = new MediaRecorder();
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){


       //     showtoast(context,"Call Started..");

            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile("/storage/emulated/0/Pictures");
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                recorder.prepare();
                showtoast(context,"Call Recorded..");
            } catch (IOException e) {
                e.printStackTrace();
            }
            recorder.start();


        }else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                    // logic to set the EditText could go here
                }

                public void onFinish() {
                    Uri uricalllog = Uri.parse("content://call_log/calls");
                    Cursor cursorcalllog = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                            null, null, null, android.provider.CallLog.Calls.DATE + " DESC limit 1;");

                    if( cursorcalllog != null && cursorcalllog.moveToFirst() ){
                        @SuppressLint("Range") String stringduration = cursorcalllog.getString(cursorcalllog.getColumnIndex(CallLog.Calls.DURATION));
                        cursorcalllog.close();
                        showtoast(context,"Call Ended.."+stringduration);
                        recorder.release();
                    }
                }

            }.start();






















        }
        /*if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }


            onCallStateChanged(context, state, number);
        }*/
    }
    protected abstract void onIncomingCallReceived(Context ctx, String number, Date start);

    protected abstract void onIncomingCallAnswered(Context ctx, String number, Date start);

    protected abstract void onIncomingCallEnded(Context ctx, String number, Date start, Date end);

    protected abstract void onOutgoingCallStarted(Context ctx, String number, Date start);

    protected abstract void onOutgoingCallEnded(Context ctx, String number, Date start, Date end);

    protected abstract void onMissedCall(Context ctx, String number, Date start);
    private void showtoast(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void onCallStateChanged(Context context, int state, String number) {
        if (lastState == state) {
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallReceived(context, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false;
                    callStartTime = new Date();

                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                } /*else {
                    isIncoming = true;
                    callStartTime = new Date();
                    startRecording();
                    onIncomingCallAnswered(context, savedNumber, callStartTime);
                }*/

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    onMissedCall(context, savedNumber, callStartTime);
                } else if (isIncoming) {

                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                } else {

                    onOutgoingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                break;
        }
        lastState = state;
    }

}
