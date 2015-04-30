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
        SharedPreferences sharedPref = context.getSharedPreferences("AUTHORIZATION", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(AUTHORIZATION_TOKEN, authToken);
        editor.commit();
    }

    public String getAuthToken() {
        SharedPreferences sharedPref = context.getSharedPreferences("AUTHORIZATION", 0);
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

        private String error;

        protected User doInBackground(User... params) {
            User user = params[0];
            URL signUpUrl = null;
            String responseText = null;
            User createdUser = null;

            try {
                signUpUrl = new URL(APIEndpoints.SIGN_UP_USER);
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
                postParameters.put("fullname", user.getFullname());

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
                    createdUser = new Gson().fromJson(responseText, User.class);
                } else {
                    error = "An error ocurred in server " + status;
                }

               } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return createdUser;
        }

        protected void onPostExecute(User user) {
            if(user == null) {
                if(delegate != null) {
                    delegate.authServiceDidFailedSignup(this.error);
                }
            }else {

                setAuthToken(user.getAuthenticationToken());

                if (delegate != null) {
                    delegate.authServiceDidFinishSignup(user);
                }
            }
        }

    }

    private class LoginTask extends AsyncTask<String, Void, User> {

        private String error;

        protected User doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            URL loginUrl = null;
            String responseText = null;
            User loggedUser = null;
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
                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    responseText = sb.toString();
                    loggedUser = new Gson().fromJson(responseText, User.class);
                } else if(status == 401) {
                    error = "Incorrect login/password";
                } else {
                    error = "An error occurred in server " + status;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return loggedUser;
        }

        protected void onPostExecute(User user) {
            if(user == null) {
                if(delegate != null) {
                    delegate.authServiceDidFailedLogin(this.error);
                }
            }else {

                setAuthToken(user.getAuthenticationToken());

                if (delegate != null) {
                    delegate.authServiceDidFinishLogin(user);
                }
            }
        }
    }
}
