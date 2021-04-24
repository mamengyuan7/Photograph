package net.onest.photographget.entity;

public class Picture {
    private int id;
    private String imgAddress;
    private int userId;
    private String title;
    private String introduce;
<<<<<<< HEAD

=======
    private int typeId;
>>>>>>> 7bbc011a00ac5f0ff21a401d0ea02914467dd27e
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

<<<<<<< HEAD
    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imgAddress='" + imgAddress + '\'' +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
=======
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
>>>>>>> 7bbc011a00ac5f0ff21a401d0ea02914467dd27e
    }
}