package com.cinntra.okana.workManager;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.cinntra.okana.globals.Globals;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApiSchedular {
    private static int INTERVAL_MINUTES = 15;
    private static int JOB_ID = 1;

    public static void schedularCall(Activity activity){
        /*ComponentName componentName = new ComponentName(activity, WorkManagerApplication.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName).setPeriodic(15 * 60 * 1000).build();
        JobScheduler jobScheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        Prefs.putBoolean(Globals.Location_FirstTime,false);*/


        //todo hit and try code here---

        // Calculate the initial delay to skip the first 0 to 15 minutes
        long initialDelay = calculateInitialDelay();

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        // Schedule a task to run every 15 minutes

        scheduler.scheduleAtFixedRate(new Runnable() {
            int count = 0;

            @Override
            public void run() {
                // Increment count after 15 minutes
                count++;

                ComponentName componentName = new ComponentName(activity, WorkManagerApplication.class);
                JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName).setPeriodic(15 * 60 * 1000).build();
                JobScheduler jobScheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(jobInfo);
                Prefs.putBoolean(Globals.Location_FirstTime,false);
                // Your task logic here
                Log.e("After 15 min executed= ", "run: " + count);

            }
        }, 15, 15, TimeUnit.MINUTES); //todo its working fine hitting after skip initial 15 min--

        /*scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {


            }
        }, initialDelay, 15, TimeUnit.MINUTES);


        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                // Display toast message
                Log.e("First Task executed= ", "run: " + System.currentTimeMillis());
            }


        }, 15 - initialDelay, TimeUnit.MINUTES);*/



    }


    private static long calculateInitialDelay() {
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Calculate the time until the next 15-minute interval
        long nextInterval = 15 * 60 * 1000 - (currentTimeMillis % (15 * 60 * 1000));

        // Return the initial delay
        return nextInterval;
    }

}
