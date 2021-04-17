package net.onest.photographget.entity;

public class User {
    private int id;//id
    private String nickName;//昵称
    private String password;//密码
    private String head_portrait;//头像
    private String background;//背景
    private String pers_signature;//个签
    private String telephone;//手机号

    public User(){

    }

    public User(String telephone,String password ) {
        this.password = password;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPers_signature() {
        return pers_signature;
    }

    public void setPers_signature(String pers_signature) {
        this.pers_signature = pers_signature;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
