package com.allever.coderhouse.modules.image.bean;

import java.util.List;

/**
 * Created by allever on 17-5-29.
 */

public class BingRoot {
    String code;
    String message;
    List<BingData> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BingData> getData() {
        return data;
    }

    public void setData(List<BingData> data) {
        this.data = data;
    }
}
