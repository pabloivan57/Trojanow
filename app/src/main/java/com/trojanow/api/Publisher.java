package com.trojanow.api;

import com.trojanow.model.Post;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.trojanow.networking.QueryStringBuilder;
import com.trojanow.sensor.Environment;

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

/**
 * Created by pabloivan57 on 3/27/15.
 */
public class Publisher {
//create a constructor to pass context or create an authservice outside

    private Activity context;
    private PublisherDelegate delegate;

    public Publisher(Activity context) {
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

    public PublisherDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(PublisherDelegate delegate) {
        this.delegate = delegate;
    }

    private class PostTask extends AsyncTask<Post, Void, Post> {

        private String error;

        protected Post doInBackground(Post... params) {
            Post post = params[0];
            Post createdpost = null;
            URL postUrl = null;
            String responseText = null;
            try {
                postUrl = new URL(APIEndpoints.CREATE_STATUS);
                HttpURLConnection urlConnection = (HttpURLConnection) postUrl.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Accept","application/json");
                String auth_token = new AuthService(context).getAuthToken();
                urlConnection.setRequestProperty("AUTHORIZATION_TOKEN", new AuthService(context).getAuthToken());

                Map<String, String> postParameters = new HashMap<String, String>();
                postParameters.put("status[description]", post.getDescription());
                postParameters.put("status[anonymous]", "false");
                if(post.getAnonymous() == true) {
                    postParameters.put("status[anonymous]", "true");
                }

                postParameters.put("status[location][latitude]", ((Double)post.getLocation().getLatitude()).toString());
                postParameters.put("status[location][longitude]", ((Double)post.getLocation().getLongitude()).toString());


                if(post.getShareEnvironment() == true) {
                    postParameters.put("status[environment][temperature]", ((Double)post.getEnvironmentInfo().getTemperature()).toString());
                }

                postParameters.put("status[status_type]", "status");
                if(post.getIsEvent()) {
                    postParameters.put("status[status_type]", "event");
                }

                String queryString = QueryStringBuilder.queryStringForParameters(postParameters);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(queryString);
                writer.flush();
                writer.close();
                os.close();

                urlConnection.connect();
                int status = urlConnection.getResponseCode();
                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    responseText = sb.toString();
                    createdpost = new Gson().fromJson(responseText, Post.class);
                } else if(status == 401) {
                    error = "Unauthorized for the operation";
                } else {
                    error = "An error ocurred in server " + status;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return createdpost;
        }


       protected void onPostExecute(Post post) {
           if(post != null) {
             if(delegate != null) {
                delegate.publisherDidFinishPosting(post);
             }
           } else {
             if(delegate != null) {
                 delegate.publisherDidFailedPosting(this.error);
             }
           }
       }

    }
}
