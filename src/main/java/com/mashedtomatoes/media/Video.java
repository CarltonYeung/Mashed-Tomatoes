package com.mashedtomatoes.media;

public class Video {

    private String thumbnailPath;
    private String videoPath;

    public Video(String thumbnailPath, String videoPath) {
        this.thumbnailPath = thumbnailPath;
        this.videoPath = videoPath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
