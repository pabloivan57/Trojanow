package com.trojanow.api;
import com.trojanow.model.Post;
/**
 * Created by alisha on 5/1/2015.
 */
public interface SubscriberDelegate {
    public void subscriberDidFinishGetting(Post fetchPost);
    public void subscriberDidFailedGetting(String error);
}
