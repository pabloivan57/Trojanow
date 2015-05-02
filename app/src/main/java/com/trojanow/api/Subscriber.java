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
import com.trojanow.lists.PostAdapter;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     *
     * @param post
     *
     * Sends a request to the api to post a new message on Trojanow
     */
    public void post(final Post post) {
        new PostTask().execute(post);
    }

    public SubscriberDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(SubscriberDelegate delegate) {
        this.delegate = delegate;
    }

    private class PostTask extends AsyncTask<Post, Void, Post> {

        private String error;

        protected Post doInBackground(Post... params) {
            Post post = params[0];
            Post createdpost = null;
            URL postUrl = null;
            URL getUrl = null;
            URL getUrl1=null;
            //Post getpost;
            String responseText = null;
            try {
                getUrl = new URL("http://trojanow.herokuapp.com/statuses");
                getUrl1= new URL ("TEMPERATURE");
                HttpURLConnection urlConnection = (HttpURLConnection)getUrl.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(false);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Accept", "application/json");
                String auth_token = new AuthService(context).getAuthToken();
                urlConnection.setRequestProperty("AUTHORIZATION_TOKEN", new AuthService(context).getAuthToken());
                //System.out.println(urlConnection.getResponseCode());
                HttpURLConnection urlConnection1 = (HttpURLConnection)getUrl1.openConnection();
                urlConnection1.setDoInput(true);
                urlConnection1.setDoOutput(false);
                urlConnection1.setRequestMethod("GET");
                urlConnection1.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConnection1.setRequestProperty("Accept", "application/json");
                String auth_token1 = new AuthService(context).getAuthToken();
                urlConnection1.setRequestProperty("AUTHORIZATION_TOKEN", new AuthService(context).getAuthToken());
                //System.out.println(urlConnection.getResponseCode());
              //  String statuspost=post.getDescription();
               // String statuspost=post.getDescription();
             //   System.out.println(statuspost);

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









                    JSONArray jarray = new JSONArray(responseText);
                    for (int i = 0; i < jarray.length(); i++) {
                        HashMap<String, String > map = new HashMap<String, String>();

                    JSONObject obj = jarray.getJSONObject(i);
                    String description = obj.getString("description");
                    JSONObject obj1 = obj.getJSONObject("user");
                    JSONObject loc = obj.getJSONObject("location");
                  /*      JSONObject temp;
                        if(obj.getJSONObject("environment")!=null)
                        {  temp = obj.getJSONObject("environment");
                     String temperature = temp.getString("temperature");
                        map.put("environment",temperature);}
                        else
                        map.put("environment","null");*/
                       // map.put("temperature", temperature);}

                    String fullname = obj1.getString("fullname");
//                    System.out.println(fullname);
                    String latitude = loc.getString("latitude");
                    String longitude=loc.getString("longitude");

                        map.put("name", fullname);
                        map.put("latitude", latitude);
                        map.put("longitude",longitude);



                        map.put("description",description);


                //        mylist.add(map);



                        mylist.add(map);
                    //    System.out.println("---mlist--"+map);

                    }




                      mlist=mylist;

                    //There will be an exception here, replace for correct
                    // way of parsing json arrays using GSON library
                    // createdpost = new Gson().fromJson(responseText, Post.class);
                } else if (status == 401) {
                    error = "Unauthorized for the operation";
                } else {
                    error = "An error ocurred in server " + status;
                }
                urlConnection1.connect();
                int status1 = urlConnection1.getResponseCode();
                if (status1 == 200 || status1 == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection1.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    br.close();
                    responseText = sb.toString();
                    System.out.println("----temperature"+responseText);
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
            return createdpost;
        }



        protected void onPostExecute(Post post) {
            System.out.println(mylist);
         //  PostAdapter posted=new PostAdapter();
           // posted.passpost(mylist);
            if (post != null) {
                if (delegate != null) {
                  //  Toast.makeText(getApplicationContext(), "Received!", Toast.LENGTH_LONG).show();
                  //  etResponse.setText(result);
                    delegate.subscriberDidFinishGetting(post);
                }
            } else {
                if (delegate != null) {
                    delegate.subscriberDidFailedGetting(this.error);
                }
               // System.out.println("--------mlist print"+mlist);
            }
        }



    }
}
