package com.trojanow.media;

import com.trojanow.model.Video;

/**
 * Created by pabloivan57 on 3/27/15.
 */
public class VideoMedia extends Media {
    /**
     *
     * @return videoMedia
     *
     * Captures a video using the specified source and returns an object containing the video info
     * with default duration
     */
    public Video captureMedia() {
        return null;
    }

    /**
     * Starts a new video capture
     */
    public void startCapture() { };

    /**
     * Changes the source type
     */
    public void changeSource(SourceType type){

    }

    /**
     *
     * @return video
     * Stopes the recording of a video
     */
    public Video stopCapture() { return null; }
}
