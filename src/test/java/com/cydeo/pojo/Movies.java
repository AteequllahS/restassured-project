package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movies {

    //first observe this nis not ca common java naming convention
    //because in java we start variable with lowercase
    //however we still want to instruct Jackson data-bind to match the fields we want
    //in order to instruct jackson library what json field to match what java field,
    // you can use the annotation coming from jackson library @ JsonProperty

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year; // now this can be anything, as JsonPath follows what is declared in JasonProperty

    //no need to annotate this as it is already in lowercase (following java naming convention)
    private String imdbID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Poster")
    private String poster;

    public Movies(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "Title='" + title + '\'' +
                ", Year='" + year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", Type='" + type + '\'' +
                ", Poster='" + poster + '\'' +
                '}';
    }
}
