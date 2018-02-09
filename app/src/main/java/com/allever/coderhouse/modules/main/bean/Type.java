package com.allever.coderhouse.modules.main.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by allever on 17-5-27.
 */

public class Type extends DataSupport {
    private int id;
    private String typeName;
    private int clickCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
}
