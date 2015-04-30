package com.trojanow.api;

import com.trojanow.model.Post;

/**
 * Created by pabloivan57 on 4/20/15.
 */
public interface PublisherDelegate {
    public void publisherDidFinishPosting(Post createdPost);
    public void publisherDidFailedPosting(String error);
}
