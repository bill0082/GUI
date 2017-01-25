package com.example.yah.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static final String DEFAULT ="Not Found";
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        email = (EditText) findViewById(R.id.email);


        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                emailWrite();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);


            }
                                 });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    protected void emailWrite(){

        SharedPreferences sharedPref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("DefaultEmail", email.getText().toString());
        editor.commit();

        Toast.makeText(this, "Data was saved successfully ", Toast.LENGTH_LONG).show();

    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

        SharedPreferences preferences = getSharedPreferences("MyData",Context.MODE_PRIVATE);
        String defEmail = preferences.getString("DefaultEmail", DEFAULT);

        email.setText(defEmail);
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
