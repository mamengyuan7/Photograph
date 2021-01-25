package net.onest.photographget.entity;

public class User {
    private int id;
    private String name;
    private String psd;
    private String head_portrait;
    private String background;
    private String pers_signature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
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
}
