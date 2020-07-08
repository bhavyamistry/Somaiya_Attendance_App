package com.example.myapplication;

public class Notification {
    private String facultyName;
    private String Title;
    private String Images;

    public Notification(String facultyName, String title, String date,String images) {  //,int Image
        this.facultyName = facultyName;
        Title = title;
        Date = date;
        Images=images;
    }

    private String Date;

    public String getFacultyName() {
        return facultyName;
    }



    public String getTitle() {
        return Title;
    }


    public String getDate() {
        return Date;
    }


    public String getImage() {
        return Images;
    }



}
