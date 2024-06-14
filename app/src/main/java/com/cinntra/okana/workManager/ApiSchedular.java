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
    private static final int INITIAL_DELAY_MINUTES = 15;

/*    public static void schedularCall(Activity activity){
        ComponentName componentName = new ComponentName(activity, WorkManagerApplication.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // Add any constraints you need
                .setPeriodic(INTERVAL_MINUTES * 60 * 1000) // Set the interval to 15 minutes
                .build();

        JobScheduler jobScheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        int result = jobScheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.e("JobScheduler", "Job scheduled successfully!");
            Prefs.putBoolean(Globals.Location_FirstTime, false);
        } else {
            Log.e("JobScheduler", "Job scheduling failed!");
        }
    }*/


    public static void schedularCall(Activity activity){
        // Schedule the initial task after 15 minutes
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                // After the initial 15 minutes, schedule the periodic job
                ComponentName componentName = new ComponentName(activity, WorkManagerApplication.class);
                JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // Add any constraints you need
                        .setPeriodic(INTERVAL_MINUTES * 60 * 1000) // Set the interval to 15 minutes
                        .build();

                JobScheduler jobScheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);

                int result = jobScheduler.schedule(jobInfo);
                if (result == JobScheduler.RESULT_SUCCESS) {
                    Log.e("JobScheduler", "Job scheduled successfully!");
                    Prefs.putBoolean(Globals.Location_FirstTime, false);
                    Prefs.putBoolean(Globals.LocationRestart, true);
                } else {
                    Log.e("JobScheduler", "Job scheduling failed!");
                }
            }
        }, INITIAL_DELAY_MINUTES, TimeUnit.MINUTES);

        Log.e("ScheduledTask", "Initial task scheduled to run in 15 minutes");
    }


    // todo Method to cancel the scheduled job
    public static void cancelScheduledJob(Activity activity) {
        JobScheduler jobScheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOB_ID);
        Log.e("ScheduledTask", "Scheduled job canceled");
    }


    // Method to restart the scheduled job
    public static void restartScheduledJob(Activity activity) {
        // Call your scheduler method to schedule the job again
        schedularCall(activity);
        Prefs.putBoolean(Globals.LocationRestart, false);
        Log.e("ScheduledTask", "Scheduled job restarted");
    }


 /*   public static void schedularCall(Activity activity){


//        ComponentName componentName = new ComponentName(activity, WorkManagerApplication.class);
//        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName).setPeriodic(15 * 60 * 1000).build();
//        JobScheduler jobScheduler = (JobScheduler) activity.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        jobScheduler.schedule(jobInfo);
//        Prefs.putBoolean(Globals.Location_FirstTime,false);


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

        *//*scheduler.scheduleAtFixedRate(new Runnable() {
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


        }, 15 - initialDelay, TimeUnit.MINUTES);*//*



    }*/


    private static long calculateInitialDelay() {
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Calculate the time until the next 15-minute interval
        long nextInterval = 15 * 60 * 1000 - (currentTimeMillis % (15 * 60 * 1000));

        // Return the initial delay
        return nextInterval;
    }

}
