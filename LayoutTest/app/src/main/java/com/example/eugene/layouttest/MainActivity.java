package com.example.eugene.layouttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    public static final String API_URL = "http://46.101.157.54/MoppaCustomerAPI/v1/tasks/";
    public static final int DEVICE_ID = 42;

    public TextView log;
    public TextView alert;
    public ScrollView scroll_view;
    public TextView counter;

    public boolean isOnline;
    public int overallTasks;

    public int ciclesCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = (TextView) this.findViewById(R.id.log);
        alert = (TextView) this.findViewById(R.id.alert);
        scroll_view = (ScrollView) this.findViewById(R.id.ScrollView);
        counter = (TextView) this.findViewById(R.id.counter);

        MoppaTimer timer = new MoppaTimer(900000000, 200);
        timer.start();
    }

    private void handleTask() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MoppaService moppa = retrofit.create(MoppaService.class);

        moppa.tasks(DEVICE_ID).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Response<Task> response) {
                if (response.isSuccess()) {
                    Task fetchedTask = response.body();

                    writeToLog("fetched task nValue " + String.valueOf(fetchedTask.nValue));

                    final int factResult = calculateFactorial(fetchedTask.nValue);
                    final String factResultStr = String.valueOf(factResult);

                    writeToLog("task calculated the result is " + String.valueOf(factResultStr));

                    Task newOne = new Task(factResult, fetchedTask);
                    Call<Task> call = moppa.saveResultTasks(newOne);

                    call.enqueue(new Callback<Task>() {
                        @Override
                        public void onResponse(Response<Task> response) {
                            writeToLog("successfully pushed to server");
                            overallTasks++;
                            counter.setText(String.valueOf(overallTasks));
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            writeToLog("failed push task to server");
                        }
                    });
                } else {
                    writeToLog("no tasks available for execution");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                writeToLog("error occurred while fetching task");
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void writeToLog(String txt) {
        log.setText(log.getText() + "\n" + txt);
        scroll_view.post(new Runnable() {
            public void run() {
                scroll_view.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public int calculateFactorial(int n){
        int fact = 1;
        for (int i=1; i<=n; i++)
            fact = fact*i;
        return fact;
    }

    public class MoppaTimer extends CountDownTimer {
        public MoppaTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if( PowerUtil.isConnected(getApplicationContext()) ) {
                alert.setBackgroundColor(Color.parseColor("#56C646"));
                alert.setText("Online");
            } else {
                alert.setBackgroundColor(Color.parseColor("#DB5657"));
                alert.setText("There is no charging cable");
                if( isOnline != PowerUtil.isConnected(getApplicationContext()) ) {
                    writeToLog("...");
                }
            }

            isOnline = PowerUtil.isConnected(getApplicationContext());

            ciclesCount++;

            if(ciclesCount % 10 == 0 && isOnline) {
                handleTask();
            }
        }

        @Override
        public void onFinish() {}
    }
}

