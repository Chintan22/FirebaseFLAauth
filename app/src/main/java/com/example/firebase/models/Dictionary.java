package com.example.firebase.models;

public class Dictionary {

    public String id;
    public String wordName;


    public Dictionary(){

    }

    public Dictionary( String id,String wordName) {
        this.wordName = wordName;
        this.id = id;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
