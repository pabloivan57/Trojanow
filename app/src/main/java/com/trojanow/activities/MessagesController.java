package com.trojanow.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pabloivan57.trojanow.R;
import com.trojanow.api.DirectMessaging;
import com.trojanow.lists.MessageAdapter;
import com.trojanow.lists.PostAdapter;
import com.trojanow.model.Message;
import com.trojanow.model.User;

import java.io.Console;
import java.util.ArrayList;

public class MessagesController extends ActionBarActivity {

    ListView lstMessages;
    ArrayList<Message> messages = new ArrayList<Message>();
    MessageAdapter messageAdapter;

    DirectMessaging directMessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_controller);

        directMessaging = new DirectMessaging();

        /**
         * Fake messages data
         */

        User sender1 = new User();
        sender1.setFullname("john_1");
        sender1.setEmail("jhon_1@gmail.com");

        User sender2 = new User();
        sender2.setFullname("stevenRogers");
        sender2.setEmail("stevenRogers@yahoo.com");

        Message message1 = new Message();
        message1.setMessage("Hey, are you going to the party tonight?");
        message1.setSender(sender1);

        Message message2 = new Message();
        message2.setMessage("I don't think this is happening today");
        message2.setSender(sender2);

        messages.add(message1);
        messages.add(message2);
        /*******************/

        messageAdapter = new MessageAdapter(this, messages);
        lstMessages = (ListView) findViewById(R.id.lstMessages);
        lstMessages.setAdapter(messageAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages_controller, menu);
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
}
