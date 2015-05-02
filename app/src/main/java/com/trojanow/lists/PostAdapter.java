package com.trojanow.lists;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pabloivan57.trojanow.R;
import com.trojanow.model.Post;
import com.trojanow.api.Subscriber;

import java.util.ArrayList;

/**
 * Created by pabloivan57 on 3/28/15.
 */
public class PostAdapter extends ArrayAdapter<Post> {
    private final Context context;

    private final ArrayList<Post> mylist;

    public PostAdapter(Context context, ArrayList<Post> mylist) {
        super(context, 0, mylist);
        this.context = context;
        this.mylist = mylist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("---here");
        System.out.println(mylist);
        // Get the data item for this position
      //  Post post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_row, parent, false);
        }
        // Lookup view for data population
        TextView post_row_fullname = (TextView) convertView.findViewById(R.id.post_row_fullname);
        TextView post_row_status = (TextView) convertView.findViewById(R.id.post_row_status);
        // Populate the data into the template view using the data object
     //   post_row_fullname.setText(mylist.get(position).getfullname());
        post_row_status.setText(mylist.get(position).getDescription());
        // Return the completed view to render on screen
        return convertView;
    }
}