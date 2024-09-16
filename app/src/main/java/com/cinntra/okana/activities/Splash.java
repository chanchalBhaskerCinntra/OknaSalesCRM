package com.cinntra.okana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.concurrent.Executor;


public class Splash extends AppCompatActivity {

    private boolean isFirstAnimation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
     /*   startActivity( new Intent(Splash.this, Login.class));
        finish();*/
        /*Simple hold animation to hold ImageView in the centre of the screen at a slightly larger
        scale than the ImageView's original one.*/
        Animation hold = AnimationUtils.loadAnimation(this, R.anim.hold);

        /* Translates ImageView from current position to its original position, scales it from
        larger scale to its original scale.*/
        final Animation translateScale = AnimationUtils.loadAnimation(this, R.anim.translate_scale);

        final ImageView imageView = findViewById(R.id.header_icon);

        translateScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isFirstAnimation) {
                    imageView.clearAnimation();

                    login();

                    //    openfingerprintbox();
                  /*  Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();*/

                }

                isFirstAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hold.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                imageView.startAnimation(translateScale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView.startAnimation(hold);


    }


    private void login() {
        if (Prefs.getBoolean(Globals.AutoLogIn, false)) {
            Globals.APILog = "Not";
            Prefs.putString(Globals.SalesEmployeeCode, Prefs.getString(Globals.SalesEmployeeCode, ""));
            Globals.TeamSalesEmployeCode = Prefs.getString(Globals.Employee_Code, "");
            Globals.TeamRole = Prefs.getString(Globals.Role, "");
            Globals.TeamEmployeeID = Prefs.getString(Globals.EmployeeID, "");
            Globals.SelectedDB = Prefs.getString(Globals.SelectedDB, "");
            long session = Long.parseLong("30");
            session = session * 60 * 1000;

            Prefs.putLong(Globals.SESSION_TIMEOUT, session);
            Prefs.putLong(Globals.SESSION_REMAIN_TIME, 0);


            Intent i = new Intent(Splash.this, MainActivity.class);

            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else {
            Intent intent = new Intent(Splash.this, Login.class);
            startActivity(intent);
        }
    }


    private void openfingerprintbox() {
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // this means we can use biometric sensor
            case BiometricManager.BIOMETRIC_SUCCESS:
           /*     Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();*/
                break;

            // this means that the device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:

                break;

            // this means that biometric sensor is not available
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:

                break;

            // this means that the device doesn't contain your fingerprint
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "Try Again", Toast.LENGTH_LONG).show();
                break;
        }
        // creating a variable for our Executor
        Executor executor = ContextCompat.getMainExecutor(this);
        // this will give us result of AUTHENTICATION
        final BiometricPrompt biometricPrompt = new BiometricPrompt(Splash.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

            }

            // THIS METHOD IS CALLED WHEN AUTHENTICATION IS SUCCESS
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                if (Prefs.getBoolean(Globals.AutoLogIn, false)) {

                    Globals.APILog = "Not";
                    Prefs.putString(Globals.SalesEmployeeCode, Prefs.getString(Globals.SalesEmployeeCode, ""));
                    Globals.TeamSalesEmployeCode = Prefs.getString(Globals.Employee_Code, "");
                    Globals.TeamRole = Prefs.getString(Globals.Role, "");
                    Globals.TeamEmployeeID = Prefs.getString(Globals.EmployeeID, "");
                    Globals.SelectedDB = Prefs.getString(Globals.SelectedDB, "");
                    long session = Long.parseLong("30");
                    session = session * 60 * 1000;

                    Prefs.putLong(Globals.SESSION_TIMEOUT, session);
                    Prefs.putLong(Globals.SESSION_REMAIN_TIME, 0);

                    Intent i = new Intent(Splash.this, MainActivity.class);

                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                } else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                }
                finish();


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        // creating a variable for our promptInfo
        // BIOMETRIC DIALOG
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Bridge Sales : CRM")
                .setDescription("Use your fingerprint to login ").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
    }
}