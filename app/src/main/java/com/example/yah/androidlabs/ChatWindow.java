package com.example.yah.androidlabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private ListView listView;
    private EditText chatMessage;
    private Button sendButton;
    private ArrayList<String> messageLog = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ListView listView =(ListView)findViewById(R.id.listView);
        final EditText chatMessage = (EditText) findViewById(R.id.chatMessage);
        Button sendButton = (Button) findViewById(R.id.SendButton);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String chats = chatMessage.getText().toString();
                messageLog.add(chats);
                messageAdapter.notifyDataSetChanged();
                chatMessage.setText("");
            }
        });


    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int  getCount(){
            return messageLog.size();
        }

        public String getItem(int position){
            return messageLog.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2==0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText( getItem(position));
            return result;
        }

    }

}

