package net.onest.photographget.entity;

public class PictureDetail {
	private int id;
	private int picId;
	private int flag;
	private String brand;
	private String type;
	private String ptype;
	private String carmeraLen;
	private String focalLength;
	private String iso;
	private String time;
	private double latitude;
	private double longitude;
	private String address;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPicId() {
		return picId;
	}
	public void setPicId(int picId) {
		this.picId = picId;
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
	public String getCarmeraLen() {
		return carmeraLen;
	}
	public void setCarmeraLen(String carmeraLen) {
		this.carmeraLen = carmeraLen;
	}
	public String getFocalLength() {
		return focalLength;
	}
	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

	
}
