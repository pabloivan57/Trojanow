package com.trojanow.api;
import com.trojanow.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alisha on 5/1/2015.
 */
public interface SubscriberDelegate {
    public void subscriberDidFinishGettingPosts(ArrayList<Post> posts);
    public void subscriberDidFailedGettingPosts(String error);
}
