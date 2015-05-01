package com.trojanow.api;

/**
 * Created by alisha on 5/1/2015.
 */

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.trojanow.model.Post;
import com.trojanow.networking.QueryStringBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;




public class Subscriber {
//create a constructor to pass context or create an authservice outside

    private Activity context;
    private SubscriberDelegate delegate;

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
            //Post getpost;
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
                String auth_token = new AuthService(context).getAuthToken();
                urlConnection.setRequestProperty("AUTHORIZATION_TOKEN", new AuthService(context).getAuthToken());
                System.out.println(urlConnection.getResponseCode());

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

                    //There will be an exception here, replace for correct
                    // way of parsing json arrays using GSON library
                    // createdpost = new Gson().fromJson(responseText, Post.class);
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
            if (post != null) {
                if (delegate != null) {
                    delegate.subscriberDidFinishGetting(post);
                }
            } else {
                if (delegate != null) {
                    delegate.subscriberDidFailedGetting(this.error);
                }
            }
        }

    }
}
