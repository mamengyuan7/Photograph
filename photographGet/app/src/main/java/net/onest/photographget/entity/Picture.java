package net.onest.photographget.entity;

import java.util.Date;

public class Picture {
    private int id;
    private String img_address;
    private int user_id;
    private String brand;
    private String type;
    private String ptype;
    private String camera_lens;
    private String focal_length;
    private String iso;
    private String time;
    private double latitude;
    private double longitude;

    public Picture(int id, String img_address, int user_id, String brand, String type, String ptype, String camera_lens, String focal_length, String iso, String time, double latitude, double longitude) {
        this.id = id;
        this.img_address = img_address;
        this.user_id = user_id;
        this.brand = brand;
        this.type = type;
        this.ptype = ptype;
        this.camera_lens = camera_lens;
        this.focal_length = focal_length;
        this.iso = iso;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_address() {
        return img_address;
    }

    public void setImg_address(String img_address) {
        this.img_address = img_address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getCamera_lens() {
        return camera_lens;
    }

    public void setCamera_lens(String camera_lens) {
        this.camera_lens = camera_lens;
    }

    public String getFocal_length() {
        return focal_length;
    }

    public void setFocal_length(String focal_length) {
        this.focal_length = focal_length;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
