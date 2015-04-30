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
import com.trojanow.lists.PostAdapter;
import com.trojanow.model.Location;
import com.trojanow.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostController extends Activity {

    ListView lstPosts;
    ArrayAdapter mPostsAdapter;
    ArrayList posts = new ArrayList();
    PostAdapter postAdapter;
    EditText txtPost;
    Button btnPost;
    CheckBox checkBox = (CheckBox) findViewById(R.id.chkEnviroment);
    private LocationManager locationManager;
    public PostController(Context context) {
        locationManager =(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_controller);

        postAdapter = new PostAdapter(this, posts);
        btnPost = (Button) findViewById(R.id.btnpost);
        btnPost.setOnClickListener(btnPostHandler);
        lstPosts = (ListView) findViewById(R.id.lstPosts);
        lstPosts.setAdapter(postAdapter);

        txtPost = (EditText) findViewById(R.id.txtPost);
        txtPost.setOnClickListener(btnPostHandler);
        String token = new AuthService(this).getAuthToken();

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
            if (checkBox.isChecked()) {


               // checkBox.setChecked(false); show two more options for location and temperature
                // Acquire a reference to the system Location Manager
             //   LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {

                    public void onLocationChanged(android.location.Location location) {
                        // Called when a new location is found by the network location provider.
                        //makeUseOfNewLocation(location);
                    }

                  //  @Override
                  //  public void onLocationChanged(android.location.Location location) {

                 //   }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                };

// Register the listener with the Location Manager to receive location updates
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

            }
                Post post = new Post();
                post.setDescription(txtPost.getText().toString());
            Toast.makeText(getApplicationContext(), "Post successful",
                    Toast.LENGTH_LONG).show();
            PostController.this.finish();

            //    authService.signUp(user);
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
