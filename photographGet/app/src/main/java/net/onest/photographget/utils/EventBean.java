package net.onest.photographget.utils;

public class EventBean {
    private int tag;
    private String msg;
    private  int what;

    public EventBean(){
    }
    public EventBean(String msg) {
        this.msg = msg;
    }


    public EventBean(String msg, int what) {
        this.msg = msg;
        this.what = what;
    }

    public int getWhat() {
        return what;
    }
    public void setWhat(int what) {
        this.what = what;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
    public int getTag() {
        return tag;
    }
    public void setTag(int tag) {
        this.tag = tag;
    }
}
