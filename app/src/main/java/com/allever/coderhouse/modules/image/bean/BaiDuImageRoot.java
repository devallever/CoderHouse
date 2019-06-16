package com.allever.coderhouse.modules.image.bean;

/**
 * Created by allever on 17-5-30.
 */

import java.util.List;
public class BaiDuImageRoot {
    private String col;

    private String tag;

    private String tag3;

    private String sort;

    private int totalNum;

    private int startIndex;

    private int returnNumber;

    private List<Imgs> imgs ;

    public void setCol(String col){
        this.col = col;
    }
    public String getCol(){
        return this.col;
    }
    public void setTag(String tag){
        this.tag = tag;
    }
    public String getTag(){
        return this.tag;
    }
    public void setTag3(String tag3){
        this.tag3 = tag3;
    }
    public String getTag3(){
        return this.tag3;
    }
    public void setSort(String sort){
        this.sort = sort;
    }
    public String getSort(){
        return this.sort;
    }
    public void setTotalNum(int totalNum){
        this.totalNum = totalNum;
    }
    public int getTotalNum(){
        return this.totalNum;
    }
    public void setStartIndex(int startIndex){
        this.startIndex = startIndex;
    }
    public int getStartIndex(){
        return this.startIndex;
    }
    public void setReturnNumber(int returnNumber){
        this.returnNumber = returnNumber;
    }
    public int getReturnNumber(){
        return this.returnNumber;
    }
    public void setImgs(List<Imgs> imgs){
        this.imgs = imgs;
    }
    public List<Imgs> getImgs(){
        return this.imgs;
    }

}