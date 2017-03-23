package com.example.yah.androidlabs;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.yah.androidlabs.ChatDatabaseHelper.KEY_ID;
import static com.example.yah.androidlabs.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.yah.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    protected ListView listView;
    private EditText chatMessage;
    private Button sendButton;
    private ArrayList<String> messageLog = new ArrayList<String>();
    private ArrayList<Long> idLog = new ArrayList<>();
    private static ChatDatabaseHelper chathelper;
    SQLiteDatabase messages;
    private FrameLayout frameLayout;
    protected ChatAdapter messageAdapter;


    private boolean isFrameUsed;
    private Cursor cursor;
private Fragment mFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);


        ListView listView =(ListView)findViewById(R.id.listView);
        final EditText chatMessage = (EditText) findViewById(R.id.chatMessage);
        Button sendButton = (Button) findViewById(R.id.SendButton);
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);

        if(frameLayout==null){
            isFrameUsed=false;
            Log.i(ACTIVITY_NAME, "You are using the phone layout");
        }
        else{
            isFrameUsed=true;
            Log.i(ACTIVITY_NAME, "You are using the tablet layout");
        }

        //       ExampleFragment fragment = (ExampleFragment)getFragmentManager().findFragmentById(R.id.frameLayout);
/**        FrameLayout frame = (FrameLayout) findViewById(R.id.frameLayout);

 if(frame.getVisibility() == View.VISIBLE){
 isFrameUsed = true;
 }
 else{
 isFrameUsed = false;
 }
 */

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        chathelper = new ChatDatabaseHelper(this);
        messages = chathelper.getWritableDatabase();

        cursor = chathelper.getMessages();
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                messageLog.add(cursor.getString(cursor.getColumnIndex(chathelper.KEY_MESSAGE)));
                idLog.add(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(chathelper.KEY_MESSAGE)));
                cursor.moveToNext();}

        Log.i(ACTIVITY_NAME, "Cursor's column count=" + cursor.getColumnCount());

        for(int i=0; i<cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Cursor Column Names=" + cursor.getColumnName(i));
        }

        Log.i(ACTIVITY_NAME, "Cursor trying to find row entries=" + cursor.getCount());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent , View v, int position, final long id){
        Log.d("test", "clicked");

                String data = (String) messageLog.get(position);

                Bundle clickedInfo = new Bundle();
                clickedInfo.putString("messages", data);
                clickedInfo.putLong("ID", id);


                if(isFrameUsed) {

                    ChatWindow cw = new ChatWindow();


                    mFrag = new MessageFragment(cw);
                    mFrag.setArguments(clickedInfo);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frameLayout, mFrag).addToBackStack(null);
                    ft.commit();

                    messageAdapter.notifyDataSetChanged();
                }

                   // getSupportFragmentManager().beginTransaction().add(R.id.container, new MessageFragment()).commit();

                   // FragmentTransaction ft = getFragmentManager().beginTransaction().add(frameLayout, mFrag).addToBackStack(null).commit(); }
                else {
                        Intent MsgDetailWindow = new Intent(ChatWindow.this, MessageDetails.class);
                    MsgDetailWindow.putExtras(clickedInfo);
                        startActivityForResult(MsgDetailWindow, 10);
                    }

                }


        });



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 10){
            if(resultCode == RESULT_OK) {
                String returnedID = data.getStringExtra("DELETE id");
                int idNum = Integer.parseInt(returnedID)+1;
                String returnedMSG = data.getStringExtra("DELETE msg");

                Log.i("The returned ID", returnedID);
                Log.i("The returned MSG", returnedMSG);

               // messages.delete(TABLE_NAME, KEY_ID + "=?", new String[]{ returnedID });

              //  messages.delete(TABLE_NAME, KEY_ID + "='" + returnedID + "' and " + KEY_MESSAGE + "='" +returnedMSG + "'", null);

         //       messages.delete(TABLE_NAME, KEY_ID + "=? and " + KEY_MESSAGE + "=?", new String[] {null, returnedMSG});
          //      messages.delete(TABLE_NAME, KEY_MESSAGE + "=?", new String[] {returnedMSG});
         //       messages.delete(TABLE_NAME, "_id like ?", new String[] {returnedID});


                 messages.execSQL("DELETE FROM MESSAGES WHERE Message='" + returnedMSG + "'"); // AND _id like " + returnedID );

                this.recreate();
            }
        }
    }

    protected void delEntry(String msg){
        SQLiteDatabase db = chathelper.getWritableDatabase();

        String returnedMSG = msg;

                db.delete(TABLE_NAME, KEY_MESSAGE + "=?", new String[] {returnedMSG});


 }

    protected void setFrag(Fragment frag) {this.mFrag = frag;}

    protected Fragment getFrag(){return mFrag;}


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

//        public long getItemId(int position){ return idLog.get(position);}

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

