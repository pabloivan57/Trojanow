package com.trojanow.media;

import com.trojanow.model.MediaType;

/**
 * Created by pabloivan57 on 3/27/15.
 */
public abstract class Media {
    public enum SourceType {
        FrontCamera, BackCamera
    }

    public abstract MediaType captureMedia();
    public abstract void changeSource(SourceType type);
}
