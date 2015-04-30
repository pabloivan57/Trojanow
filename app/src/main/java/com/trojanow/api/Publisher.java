package com.trojanow.api;

import com.trojanow.model.Post;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.trojanow.model.User;
import com.trojanow.networking.QueryStringBuilder;
import com.trojanow.sensor.Sensor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
    /**
     *
     * @param post
     *
     * Sends a request to the api to post a new message on Trojanow
     */
    public void post(final Post post) {
        new PostTask().execute(post);
    }

    private class PostTask extends AsyncTask<Post, Void, Post> {

        protected Post doInBackground(Post... params) {
            Post post = params[0];
            URL postUrl = null;
            String jsonPost = null;
            try {
                postUrl = new URL(APIEndpoints.CREATE_STATUS);
                HttpURLConnection urlConnection = (HttpURLConnection) postUrl.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
              //  urlConnection.setRequestProperty("AUTHORIZATION_TOKEN", new AuthService(this).getAuthToken());

                Map<String, String> postParameters = new HashMap<String, String>();
                postParameters.put("status[description]", post.getDescription());
                if(post.getShareEnvironment() == true) {
                    post.setEnvironmentInfo(new Sensor().getEnviromentInfo());
                }
                //check for anonymous(True or False)

               // postParameters.put("password", user.getPassword());
              //  postParameters.put("password_confirmation", user.getPassword());

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

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        jsonPost = sb.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Post createdpost;
            createdpost = new Gson().fromJson(jsonPost, Post.class);
            return createdpost;
        }


       // protected void onPostExecute(User user) {
         //   setAuthToken(user.getAuthenticationToken());

        //    if(delegate != null) {
            //    delegate.authServiceDidFinishSignup(user);
          //  }
      //  }

    }
}
