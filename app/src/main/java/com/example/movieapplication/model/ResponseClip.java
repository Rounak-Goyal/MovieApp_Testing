package com.example.movieapplication.model;


import java.util.List;

public class ResponseClip {
    private List<Video> results;

    public List<Video> getVideo() {
        return results;
    }

    public void setVideo(List<Video>results) {
        this.results = results;
    }
}
