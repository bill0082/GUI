package com.example.yah.androidlabs;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Yah on 2017-03-12.
 */

public class MessageFragment extends Fragment {
    boolean isTablet;
    ChatWindow cw = new ChatWindow();

    public MessageFragment() {
        this(false);
    }
    public MessageFragment(boolean isT  )
    {
        isTablet = isT;
    }

    public MessageFragment(ChatWindow cw){
        this.cw = cw;
    }

    String str;
    Long fid;
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        Bundle args =  this.getArguments();

        str= args.getString("messages");
        fid= args.getLong("ID");

        Log.i("In fragment, str=" , str);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_message_details, container, false);

        final TextView msgText = (TextView)rootView.findViewById(R.id.fragmsg);
        msgText.setText(str);

        TextView idText = (TextView)rootView.findViewById(R.id.fragid);
        idText.setText(fid.toString());

        Button delButton = (Button) rootView.findViewById(R.id.delButton);

        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String delText = msgText.getText().toString();


//                cw.messages.delete(TABLE_NAME, KEY_MESSAGE + "=?", new String[] {delText});

                cw.delEntry(delText);

//                cw.getFragmentManager().beginTransaction().remove(cw.getFrag()).commit();

                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.frameLayout)).commit();

             //   ListView list = (ListView)getActivity().findViewById(R.id.listView);

             //   ((ArrayAdapter<String>) list.getAdapter()).notifyDataSetChanged();

               getActivity().recreate();

             /*   FragmentManager fm = cw.getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.hide(mFrag);
                ft.commit(); */

            }
        });


        return rootView;
    }
}
