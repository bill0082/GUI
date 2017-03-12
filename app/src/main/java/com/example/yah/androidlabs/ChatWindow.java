package com.example.yah.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.yah.androidlabs.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.yah.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ListView listView;
    private EditText chatMessage;
    private Button sendButton;
    private ArrayList<String> messageLog = new ArrayList<String>();
    private static ChatDatabaseHelper chathelper;
    SQLiteDatabase messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ListView listView =(ListView)findViewById(R.id.listView);
        final EditText chatMessage = (EditText) findViewById(R.id.chatMessage);
        Button sendButton = (Button) findViewById(R.id.SendButton);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        chathelper = new ChatDatabaseHelper(this);
        messages = chathelper.getWritableDatabase();



        Cursor cursor = chathelper.getMessages();

            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                messageLog.add(cursor.getString(cursor.getColumnIndex(chathelper.KEY_MESSAGE)));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(chathelper.KEY_MESSAGE)));
                cursor.moveToNext();}



        Log.i(ACTIVITY_NAME, "Cursor's column count=" + cursor.getColumnCount());

        for(int i=0; i<cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Cursor Column Names=" + cursor.getColumnName(i));
        }

        Log.i(ACTIVITY_NAME, "Cursor trying to find row entries=" + cursor.getCount());

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String chats = chatMessage.getText().toString();
                messageLog.add(chats);

                ContentValues values = new ContentValues();
                values.put(chathelper.KEY_MESSAGE, chats);
                messages.insert(TABLE_NAME, KEY_MESSAGE, values);
                messageAdapter.notifyDataSetChanged();
                chatMessage.setText("");
            }
        });

    }

    protected void onDestroy() {
        super.onDestroy();
        messages.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
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

