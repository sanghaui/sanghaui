package com.example.home.sample;

public class BoardDTO {
    String title;
    String id;
    int resid;

    public BoardDTO(String title, String id, int resid) {
        this.title = title;
        this.id = id;
        this.resid = resid;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public int getResid() {
        return resid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }
}
