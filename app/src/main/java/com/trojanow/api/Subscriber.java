package com.trojanow.api;

/**
 * Created by alisha on 5/1/2015.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.util.EventLogTags;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.pabloivan57.trojanow.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trojanow.model.Post;
import com.trojanow.networking.QueryStringBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class Subscriber {
//create a constructor to pass context or create an authservice outside

    private Activity context;
    private SubscriberDelegate delegate;
    private static final String STATUS = "description";
    private static final String NAME = "name";
    JSONArray description=null;
    ArrayList<String> title_array = new ArrayList<String>();
    ArrayList<String> notice_array = new ArrayList<String>();
    ArrayList<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
   public static ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();


    public Subscriber(Activity context) {
        this.context = context;
    }

    public void fetch() {
        new FetchPostTask().execute();
    }

    public SubscriberDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(SubscriberDelegate delegate) {
        this.delegate = delegate;
    }

    private class FetchPostTask extends AsyncTask<Post, Void, ArrayList<Post>> {

        private String error;

        protected ArrayList<Post> doInBackground(Post... params) {
            ArrayList<Post> posts = null;
            URL getUrl = null;
            String responseText = null;
            try {
                getUrl = new URL("http://trojanow.herokuapp.com/statuses");
                HttpURLConnection urlConnection = (HttpURLConnection)getUrl.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(false);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("AUTHORIZATION_TOKEN", new AuthService(context).getAuthToken());

                urlConnection.connect();
                int status = urlConnection.getResponseCode();
                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    br.close();
                    responseText = sb.toString();
                    Type listType = new TypeToken<ArrayList<Post>>(){}.getType();
                    posts = new Gson().fromJson(responseText, listType);
                } else if (status == 401) {
                    error = "Unauthorized for the operation";
                } else {
                    error = "An error ocurred in server " + status;
                }

                } catch (MalformedURLException e) {
                e.printStackTrace();
                error = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                error = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                error = e.getMessage();
            }
            return posts;
        }



        protected void onPostExecute(ArrayList<Post> posts) {
            if (posts != null) {
                if (delegate != null) {
                    delegate.subscriberDidFinishGettingPosts(posts);
                }
            } else {
                if (delegate != null) {
                    delegate.subscriberDidFailedGettingPosts(this.error);
                }
            }
        }
    }
}
