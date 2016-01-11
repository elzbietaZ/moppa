package com.radioactiveyak.android.moppa;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Checking Internet connection
        Context context = getApplicationContext();
        CharSequence text = "The mobile phone is connected to the Internet: " + String.valueOf(isInternetAvailable());
        int duration = Toast.LENGTH_SHORT;
        //message about Internet connection
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        //Checking if phone is charging
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;

        text = "The mobile phone is charging: " + String.valueOf(isCharging);
        duration = Toast.LENGTH_SHORT;
        //message about charging
        Toast toastCharging = Toast.makeText(context, text, duration);
        toastCharging.show();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //method for checking Internet connection
    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    public int calculateFactorial(int n){
        int fact = 1;
        for (int i=1; i<=n; i++)
            fact = fact*i;
        return fact;
    }

    public void calcFactorial(View view) {
        EditText textFromEdit = (EditText)findViewById(R.id.editTextFactorial);
        int val = Integer.parseInt( textFromEdit.getText().toString() );
        int n = calculateFactorial(val);
        Context context = getApplicationContext();
        CharSequence text = "Factorial of number : " + String.valueOf(val) + " is equal to " + String.valueOf(n);
        int duration = Toast.LENGTH_SHORT;
        Toast toastAnswer = Toast.makeText(context, text, duration);
        toastAnswer.show();
    }
}
