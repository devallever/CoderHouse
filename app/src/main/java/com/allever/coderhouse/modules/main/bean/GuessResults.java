package com.allever.coderhouse.modules.main.bean;

/**
 * Created by allever on 17-5-28.
 */

public class GuessResults {
    private String desc;

    private String ganhuo_id;

    private String publishedAt;

    private String readability;

    private String type;

    private String url;

    private String who;

    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setGanhuo_id(String ganhuo_id){
        this.ganhuo_id = ganhuo_id;
    }
    public String getGanhuo_id(){
        return this.ganhuo_id;
    }
    public void setPublishedAt(String publishedAt){
        this.publishedAt = publishedAt;
    }
    public String getPublishedAt(){
        return this.publishedAt;
    }
    public void setReadability(String readability){
        this.readability = readability;
    }
    public String getReadability(){
        return this.readability;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setWho(String who){
        this.who = who;
    }
    public String getWho(){
        return this.who;
    }

}