package com.trojanow.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.pabloivan57.trojanow.R;
import com.trojanow.lists.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class PostController extends ActionBarActivity {

    ListView lstPosts;
    ArrayAdapter mPostsAdapter;
    ArrayList posts = new ArrayList();
    PostAdapter postAdapter;
    EditText txtGoInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_controller);

        postAdapter = new PostAdapter(this, posts);
        lstPosts = (ListView) findViewById(R.id.lstPosts);
        lstPosts.setAdapter(postAdapter);

        txtGoInbox = (EditText) findViewById(R.id.txtGoInbox);
        txtGoInbox.setOnClickListener(goInboxHandler);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * btnPost handler
     * Depending on the options selected by the user it queries for Enviromental Information and creates a new post
     */
    View.OnClickListener btnPostHandler = new View.OnClickListener(){
        public void onClick(View v) {

        }
    };

    /**
     * Open screen to see user profile, here it is possible to follow/unfollow user
     */
    View.OnClickListener usrTappedHandler = new View.OnClickListener(){
        public void onClick(View v) {

        }
    };

    View.OnClickListener goInboxHandler = new View.OnClickListener(){
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MessagesController.class);
            startActivityForResult(intent, 0);
        }
    };
}
