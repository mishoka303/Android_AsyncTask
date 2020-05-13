package com.PMIU;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NumberCounterActivity extends AppCompatActivity {
    EditText number;
    TextView message;

    @Override //On activity create
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_counter);

        message = findViewById(R.id.resultText1);
        number = findViewById(R.id.textNumber1);
    }

    // When pressed button Submit
    public void function1(View view) {
        if(TextUtils.isEmpty(number.getText().toString()) || number.getText().toString().matches("\"^[0-9]*$\""))
        { Toast.makeText(NumberCounterActivity.this, "Enter a valid number!", Toast.LENGTH_SHORT).show(); }
        else
        {
            AsyncTask1 task = new AsyncTask1();
            task.execute(Integer.parseInt(number.getText().toString()));
        }
    }

    // When pressed button Back
    public void function2(View view) {
        startActivity(new Intent(NumberCounterActivity.this, MainActivity.class));
    }

    @SuppressLint("StaticFieldLeak") class AsyncTask1 extends AsyncTask<Integer,Integer,String> {
        ProgressDialog progress; //deprecated, check better implementation Dialog

        //Pre-execute function
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(NumberCounterActivity.this, "Time left: ", "The final countdown...");
        }

        //Post-execute function
        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            message.setText(str);
            message.setTextColor(Color.RED);
            message.setTextSize(48);

            progress.dismiss();
        }

        //Update during execution
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setMessage(values[0]+" second(s).");
        }


        //"Timer"
        @Override
        protected String doInBackground(Integer... integers) {
            String message = "Complete!";

            for(int i=integers[0];i>0;i--) {
                publishProgress((i));
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return message;
        }
    }
}
