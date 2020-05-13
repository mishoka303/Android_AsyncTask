package com.PMIU;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class RandTasksCounterActivity extends AppCompatActivity {
    ProgressBar pb1, pb2, pb3;
    TextView txtView;
    boolean DownloadSuccess, LoginSuccess;
    int DownloadRandTime, LoginRandTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rand_tasks_counter);

        pb1 = findViewById(R.id.downloadProgressBar); // Progress bar 1
        pb2 = findViewById(R.id.loginProgressBar); // Progress bar 2
        pb3 = findViewById(R.id.circleProgressBar); // Progress bar 3
        pb3.setVisibility(View.INVISIBLE);
        txtView = findViewById(R.id.resultText2);
    }

    // Clicking Submit button
    public void function1(View view) {
        pb3.setVisibility(View.VISIBLE);
        DownloadRandTime = new Random().nextInt(3 + 1) + 2;
        LoginRandTime = new Random().nextInt(2 + 1) + 3;
        txtView.setText("");

        DownloadTask downloadPicture = new DownloadTask();
        LoginTask loginQueue = new LoginTask();
        downloadPicture.execute();
        loginQueue.execute();
    }

    // Clicking Back button
    public void function2(View view) { startActivity(new Intent(RandTasksCounterActivity.this, MainActivity.class)); }


    @SuppressLint("StaticFieldLeak") private class DownloadTask extends AsyncTask<Void,Integer,Boolean>
    {
        @Override protected void onPreExecute()
        {
            super.onPreExecute();
            pb1.setMax(DownloadRandTime);
            pb1.setProgress(0);
        }

        @Override protected void onPostExecute(Boolean aBoolean)
        {
            super.onPostExecute(aBoolean);
            DownloadSuccess = aBoolean;
        }

        @Override protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            pb1.setProgress(values[0]);
        }

        @Override protected Boolean doInBackground(Void... voids)
        {
            for (int i=0; i<DownloadRandTime; i++) {
                publishProgress((i*100)/DownloadRandTime);
                try { Thread.sleep(1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            return (Math.random()<0.5);
        }
    }

    @SuppressLint("StaticFieldLeak") private class LoginTask extends AsyncTask<Void,Integer,Boolean>
    {
        @Override protected Boolean doInBackground(Void... voids)
        {
            for (int i=0; i<LoginRandTime; i++) {
                publishProgress((i*100)/LoginRandTime);
                try { Thread.sleep(1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            return (Math.random()<0.5);
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();
            pb2.setMax(LoginRandTime);
            pb2.setProgress(0);
        }

        @SuppressLint("SetTextI18n")
        @Override protected void onPostExecute(Boolean aBoolean)
        {
            super.onPostExecute(aBoolean);
            pb3.setVisibility(View.INVISIBLE);
            LoginSuccess = aBoolean;
            txtView.setTextColor(Color.GREEN);
            txtView.setTextSize(32);
            txtView.setGravity(Gravity.CENTER_HORIZONTAL);
            if(DownloadSuccess && LoginSuccess) {
                txtView.setText("Synced!");
            }
            else {
                txtView.setText("Not synced!");
            }
        }

        @Override protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            pb2.setProgress(values[0]);
        }
    }
}
