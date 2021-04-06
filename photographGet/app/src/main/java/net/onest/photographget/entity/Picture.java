package net.onest.photographget.entity;

import java.util.Date;

public class Picture {
    private int id;
    private String imgAddress;
    private int userId;
    private String title;
    private String introduce;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getImgAddress() {
        return imgAddress;
    }
    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
