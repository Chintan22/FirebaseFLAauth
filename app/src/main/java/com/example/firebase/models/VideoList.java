package com.example.firebase.models;

public class VideoList {

        private String title, linkname;

        public VideoList() {
        }

        public VideoList(String title, String linkname) {
            this.title = title;
            this.linkname = linkname;

        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }
}
