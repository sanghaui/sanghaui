package com.example.user.sangwa_test.Board.DTO;

public class SangWaDTO {
    private int index;
    private String id;
    private String pw;
    private String title;
    private String content;
    private String date;
    private String reply;
    private int like;
    private int readCount;
    private String imgRes;
    private String encodedImage;
    private  String likecount;


    //빈생성자
    public SangWaDTO() {}

    public SangWaDTO(String id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public SangWaDTO(String id, String title, String content, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public SangWaDTO(int index, String id, String title, String content, String date, String reply, int like, int readCount, String imgRes) {
        this.index = index;
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.reply = reply;
        this.like = like;
        this.readCount = readCount;
        this.imgRes = imgRes;
    }

    public SangWaDTO(int index, String id, String title, String content, String date, int readCount) {
        this.index = index;
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.readCount = readCount;
    }

    //리플

    public SangWaDTO(int index, String id, String content, String date) {
        this.index = index;
        this.id = id;
        this.content = content;
        this.date = date;
    }


    //getter & setter


    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
