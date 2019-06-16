package com.allever.coderhouse.modules.main.bean;

/**
 * Created by allever on 17-5-27.
 */

import java.util.List;
public class Root {
    private int count;
    private boolean error;

    private List<Results> results ;

    public void setError(boolean error){
        this.error = error;
    }
    public boolean getError(){
        return this.error;
    }
    public void setResults(List<Results> results){
        this.results = results;
    }
    public List<Results> getResults(){
        return this.results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isError() {
        return error;
    }
}