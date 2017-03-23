package com.example.yah.androidlabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Fragment mFrag = new MessageFragment();
        mFrag.setArguments(getIntent().getExtras());

        Bundle args =  mFrag.getArguments();

           TextView msgText = (TextView) findViewById(R.id.fragmsg);
           String str = args.getString("messages");
           msgText.setText(str);

            TextView idText = (TextView) findViewById(R.id.fragid);
            Long fid = args.getLong("ID");
            idText.setText(fid.toString());

//            str = bdl.getString("yourKey");

        Button delButton = (Button) findViewById(R.id.delButton);

        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                TextView msgText = (TextView) findViewById(R.id.fragmsg);
                String msgPassBack = msgText.getText().toString();

                TextView idText = (TextView) findViewById(R.id.fragid);
                String passBack = idText.getText().toString();

                Intent backToDel = new Intent();

                backToDel.putExtra("DELETE id", passBack);
                backToDel.putExtra("DELETE msg", msgPassBack);
                setResult(RESULT_OK, backToDel);

                finish();
            }
        });


    }


}
