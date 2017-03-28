package com.example.yah.androidlabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolBar extends AppCompatActivity {
    static String newTask1msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);
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

        newTask1msg = getString(R.string.task1);

    }


    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {


        switch (mi.getItemId()) {
            case R.id.action_one:

                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(findViewById(R.id.action_one), newTask1msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you want to go back?");
// Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;

            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");

                AlertDialog.Builder customBuilder = new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                final View rootView = inflater.inflate(R.layout.dialog, null);
                final EditText newText = (EditText) rootView.findViewById(R.id.dialogText);
                customBuilder.setView(rootView)
                        // Add action buttons
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
//                                TextView newText = (TextView) findViewById(R.id.dialogText);
                                newTask1msg= newText.getText().toString();
                            }

                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                customBuilder.create();

                customBuilder.show();

 //               CustomDialogFragment cdf = new CustomDialogFragment();
                //AlertDialog dialog3 = cdf.onCreateDialog();
 //               cdf.show();

                break;

            case R.id.about:
                Toast toast = Toast.makeText(this, "Version 1.0, by Chris", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        return true;
    }


    public static class CustomDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder customBuilder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();


            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            customBuilder.setView(inflater.inflate(R.layout.dialog, null))
                    // Add action buttons
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                   //         newTask1msg = newText.getText().toString();
                        }

                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            return customBuilder.create();
        }
    }
}