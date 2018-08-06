package com.example.user.sangwa_test.Board.DTO;

public class BoardReplyDTO {
    private int parentid;
    private int index;
    private String title;
    private String content;
    private String id;

    public BoardReplyDTO() {
    }

    public BoardReplyDTO(int index, String title, String content, String id) {
        this.index = index;
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public BoardReplyDTO(int parentid, int index, String title, String content, String id) {
        this.parentid = parentid;
        this.index = index;
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
