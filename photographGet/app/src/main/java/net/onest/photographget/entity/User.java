package net.onest.photographget.entity;

public class User {
    private int id;
    private String nickName;
    private String head_portrait;
    private String background;
    private String pers_signature;
    private String telephone;
    private String password;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
