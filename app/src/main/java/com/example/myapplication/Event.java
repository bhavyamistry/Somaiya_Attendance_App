package com.example.myapplication;

import android.content.Intent;

public class Event {

    private String title,category,date,description;
    private String image;
    private boolean expanded;


    public Event(String title, String category, String date, String description,String images) {//, String image
        this.title = title;
        this.category = category;
        this.date = date;
        this.description = description;
        this.image = images;
        this.expanded = false;
    }


    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }



    public String getImage() {
        return image;
    }


    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
