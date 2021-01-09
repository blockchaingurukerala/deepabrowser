package com.example.assessmentwebbrowser;

import android.widget.ImageView;

public class Websites {

    private int id;
    private String url;
    private ImageView image;


    private String title;

    public Websites(){

    }
public Websites(String url){
        this.url = url;
}

    public Websites(String url, String Title){
        this.url = url;
        this.title=title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}



