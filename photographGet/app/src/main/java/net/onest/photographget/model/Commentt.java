package net.onest.photographget.model;

/**
 * Created by yyp on 2016/8/10.
 */
public class Commentt {

    String name; //评论者
    String content; //评论内容

    public Commentt(){

    }

    public Commentt(String name, String content){
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
