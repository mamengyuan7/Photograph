package net.onest.photographget.entity;

public class Comment {
    private int id;
    private int userId;
    private String content;
    private int picId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getPicId() {
        return picId;
    }
    public void setPicId(int picId) {
        this.picId = picId;
    }
}
