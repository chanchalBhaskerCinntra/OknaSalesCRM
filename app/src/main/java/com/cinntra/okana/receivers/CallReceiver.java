package com.cinntra.okana.receivers;

import android.content.Context;
import android.util.Log;

import java.util.Date;

public class CallReceiver extends CallStateReceiver {

    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start) {
        Log.d("onIncomingCallReceived", number + " " + start.toString());
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
        Log.d("onIncomingCallAnswered", number + " " + start.toString());
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d("onIncomingCallEnded", number + " " + start.toString() + "\t" + end.toString());
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.d("onOutgoingCallStarted", number + " " + start.toString());
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d("onOutgoingCallEnded", number + " " + start.toString() + "\t" + end.toString());
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.d("onMissedCall", number + " " + start.toString());
//        PostCallHandler postCallHandler = new PostCallHandler(number, "janskd" , "")
    }

}