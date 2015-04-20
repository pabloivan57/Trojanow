package com.trojanow.api;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.trojanow.model.User;
import com.trojanow.networking.QueryStringBuilder;

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
public class AuthService {

    public static final String AUTHORIZATION_TOKEN = "AUTH_TOKEN";

    private Activity context;
    private AuthServiceDelegate delegate;

    public AuthService(Activity context) {
        this.context = context;
    }

    /**
     * @param user
     * Signup function, it sends a request to API signup backend to create a new user
     * **/
    public void signUp(final User user) {
        new SignupTask().execute(user);
    }

    /**
     *
     * @param email
     * @param password
     * @return user
     *
     * Sends a login request to the server, if successful it returns a User object with the logged
     * user info, otherwise it returns null
     */
    public void login(String email, String password) {
        new LoginTask().execute(email, password);
    }

    /**
     *
     * @param authToken
     *
     * Sets the auth_token header on the networking library for any subsequent authenticated requests
     */
    public void setAuthToken(String authToken) {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(AUTHORIZATION_TOKEN, authToken);
        editor.commit();
    }

    public String getAuthToken() {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        String authToken = sharedPref.getString(AUTHORIZATION_TOKEN, "");
        return authToken;
    }

    public void setDelegate(AuthServiceDelegate delegate) {
        this.delegate = delegate;
    }

    public AuthServiceDelegate getDelegate(){
        return this.delegate;
    }


    private class SignupTask extends AsyncTask<User, Void, User> {

        protected User doInBackground(User... params) {
            User user = params[0];
            URL signUpUrl = null;
            String jsonUser = null;
            try {
                signUpUrl = new URL(APIEndpoints.LOGIN_USER);
                HttpURLConnection urlConnection = (HttpURLConnection) signUpUrl.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                Map<String, String> postParameters = new HashMap<String, String>();
                postParameters.put("email",user.getEmail());
                postParameters.put("password", user.getPassword());
                postParameters.put("password_confirmation", user.getPassword());

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
                        jsonUser = sb.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            User createdUser = new Gson().fromJson(jsonUser, User.class);
            return createdUser;
        }

        protected void onPostExecute(User user) {
            setAuthToken(user.getAuthenticationToken());

            if(delegate != null) {
                delegate.authServiceDidFinishSignup(user);
            }
        }

    }

    private class LoginTask extends AsyncTask<String, Void, User> {

        protected User doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            URL loginUrl = null;
            String jsonUser = null;
            try {
                loginUrl = new URL(APIEndpoints.LOGIN_USER);
                HttpURLConnection urlConnection = (HttpURLConnection) loginUrl.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                Map<String, String> postParameters = new HashMap<String, String>();
                postParameters.put("email", email);
                postParameters.put("password", password);

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
                        jsonUser = sb.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            User loggedUser = new Gson().fromJson(jsonUser, User.class);
            return loggedUser;
        }

        protected void onPostExecute(User user) {
            setAuthToken(user.getAuthenticationToken());

            if(delegate != null) {
                delegate.authServiceDidFinishLogin(user);
            }
        }
    }
}
