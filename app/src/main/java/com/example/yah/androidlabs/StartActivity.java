package com.example.yah.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {


    protected static final String ACTIVITY_NAME = "StartActivity";
    public final static int MESSAGE_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button start = (Button) findViewById(R.id.startbutton);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent getResult = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(getResult, 5);
            }
        });

        Button startChat = (Button) findViewById(R.id.startChat);
        startChat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i(ACTIVITY_NAME, "User clicked Start Chat!");
                Intent chatWindow = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(chatWindow);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 5){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            if (responseCode == RESULT_OK) {
               String messagePassed = data.getStringExtra("Response");
                Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


}
