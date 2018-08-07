package com.example.user.sangwa_test.Board.DTO;

public class BoardReplyDTO {
    private int parentid;
    private int index;
    private String content;
    private String id;
    private String date;


    public BoardReplyDTO() {
    }

    public BoardReplyDTO(int index, String content, String id, String date) {
        this.index = index;
        this.content = content;
        this.id = id;
        this.date = date;
    }

    public BoardReplyDTO(int parentid, int index, String content, String id, String date) {
        this.parentid = parentid;
        this.index = index;
        this.content = content;
        this.id = id;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
