package com.trojanow.lists;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pabloivan57.trojanow.R;
import com.trojanow.model.Message;
import com.trojanow.model.Post;

import java.util.ArrayList;

/**
 * Created by pabloivan57 on 3/28/15.
 */
public class MessageAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Message> data;
    private static LayoutInflater inflater=null;

    public MessageAdapter(Activity a, ArrayList<Message> data) {
        this.activity = a;
        this.data=data;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.post_row, null);

     //   TextView username = (TextView)vi.findViewById(R.id.post_row_username); // title
      //  TextView postDescription = (TextView)vi.findViewById(R.id.post_row_post); // artist name

        Message message = data.get(position);

        // Setting all values in listview
      //  username.setText(message.getSender().getFullname());
       // postDescription.setText(message.getMessage());
        return vi;
    }
}
