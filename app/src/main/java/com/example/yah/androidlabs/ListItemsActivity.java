package com.example.yah.androidlabs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import static android.R.attr.data;

public class ListItemsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Switch mySwitch =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        ImageButton imageBut = (ImageButton) findViewById(R.id.image);

        imageBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE);

            }
        });

        mySwitch = (Switch) findViewById(R.id.switchBut);
        mySwitch.setOnCheckedChangeListener(this);

        final CheckBox myCheckBox = (CheckBox) findViewById(R.id.check);

        myCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (myCheckBox.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                    builder.setMessage(R.string.dialog_message);

                    builder.setTitle(R.string.dialog_title);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "My information to share");
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    builder.show();
                }
            }
        });
    }




    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CharSequence text = "";
        int duration = 0;

        if (isChecked) {
            text = getString(R.string.toastOn);
            duration = Toast.LENGTH_SHORT;
        } else {
            text = getString(R.string.toastOff);
            duration = Toast.LENGTH_LONG;
        }

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
        ImageButton imageBut = (ImageButton) findViewById(R.id.image);
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        imageBut.setImageBitmap(imageBitmap);
    }
    }

    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}