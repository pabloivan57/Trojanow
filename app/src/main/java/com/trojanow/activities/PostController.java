package com.trojanow.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.CheckBox;

import com.example.pabloivan57.trojanow.R;
import com.trojanow.api.AuthService;
import com.trojanow.api.AuthServiceDelegate;
import com.trojanow.api.Publisher;
import com.trojanow.api.PublisherDelegate;
import com.trojanow.api.Subscriber;
import com.trojanow.api.SubscriberDelegate;
import com.trojanow.lists.PostAdapter;

import com.trojanow.model.Post;
import com.trojanow.sensor.Environment;
import com.trojanow.sensor.Geolocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostController extends Activity implements PublisherDelegate, SubscriberDelegate {

    ListView lstPosts;
    ArrayAdapter mPostsAdapter;
    ArrayList posts = new ArrayList();
    PostAdapter postAdapter;
    EditText txtPost;
    Button btnPost;
    CheckBox chkEnvironment;
    CheckBox chkAnonymous;
    CheckBox chkEvent;
    Geolocation geolocation;
    Environment environment;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_controller);

        chkEnvironment =  (CheckBox) findViewById(R.id.chkEnviroment);
        chkAnonymous   =  (CheckBox) findViewById(R.id.chkAnonymous);
        chkEvent       =  (CheckBox) findViewById(R.id.chkEvent);
        postAdapter = new PostAdapter(this, posts);
        btnPost = (Button) findViewById(R.id.btnpost);
        btnPost.setOnClickListener(btnPostHandler);
        lstPosts = (ListView) findViewById(R.id.lstPosts);
        lstPosts.setAdapter(postAdapter);

        txtPost = (EditText) findViewById(R.id.txtPost);


        //Initialize sensors
        geolocation = new Geolocation(this);
        environment = new Environment(this);
      //  Post post=new Post();
       // status=post.getDescription();
        //System.out.println("--"+status);
    }
    protected void onStart()
    {

        super.onStart();
        Post post = new Post();
        System.out.print("------subscriber");
        Subscriber subscriber = new Subscriber(PostController.this);
        subscriber.setDelegate(PostController.this);
        subscriber.post(post);
        String statuspost= post.getDescription();
    //    post.setLocation(geolocation.getLocation());
      //  post.setIsEvent(chkEvent.isChecked());
    }


    @Override
    protected void onResume()
    {

        super.onResume();
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
     * Depending on the options selected by the user it queries for Environmental Information and creates a new post
     */
    public View.OnClickListener btnPostHandler = new View.OnClickListener(){
        public void onClick(View v) {
        Post post = new Post();
        post.setDescription(txtPost.getText().toString());
        post.setLocation(geolocation.getLocation());
        post.setIsEvent(chkEvent.isChecked());

        if(chkAnonymous.isChecked()) {
            post.setAnonymous(true);
        }


        if(chkEnvironment.isChecked()) {
            post.setShareEnvironment(true);
          //  System.out.println("----check--!"+environment.getEnviromentInfo());
            post.setEnvironmentInfo(environment.getEnviromentInfo());
        }

        Publisher publisher = new Publisher(PostController.this);
        publisher.setDelegate(PostController.this);
        publisher.post(post);

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

    @Override
    public void publisherDidFinishPosting(Post createdPost) {
        Toast.makeText(getApplicationContext(), "Status Posted Sucessfully",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void publisherDidFailedPosting(String error) {
        Toast.makeText(getApplicationContext(), "There was an error while posting the status",
                Toast.LENGTH_LONG).show();
    }

    public void subscriberDidFinishGetting(Post createdPost) {
       // Toast.makeText(getApplicationContext(), "Status Posted Sucessfully",
         //       Toast.LENGTH_LONG).show();
    }

    @Override
    public void subscriberDidFailedGetting(String error) {
       // Toast.makeText(getApplicationContext(), "There was an error while posting the status",
         //       Toast.LENGTH_LONG).show();
    }
}
