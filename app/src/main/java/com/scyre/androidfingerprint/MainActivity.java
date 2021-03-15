package com.scyre.androidfingerprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.Executor;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class MainActivity extends AppCompatActivity {

    private ImageView fingerPrintTap;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fingerPrintTap=findViewById(R.id.fingerPrintTap);
        fingerPrintTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              biometricPrompt.authenticate(promptInfo);
            }
        });

        //init biometric
        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast toast=Toast.makeText(getApplicationContext(),"Authentication Error" + errString,Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast toast=Toast.makeText(getApplicationContext(),"Authentication Successful",Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast toast=Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_LONG);
                toast.show();
            }
        });

        //setup title and error for authentication dialog
        promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authentication Required")
                .setSubtitle("Place your finger on your device to verify your identity")
                .setNegativeButtonText("User App Password")
                .build();

    }
}