package com.example.firebase.models;

public class Dictionary {

    public String wordName;

    public Dictionary(){

    }

    public Dictionary(String wordName) {
        this.wordName = wordName;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }
}
