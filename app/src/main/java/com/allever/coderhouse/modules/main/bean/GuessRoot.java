package com.allever.coderhouse.modules.main.bean;

import java.util.List;

/**
 * Created by allever on 17-5-28.
 */

public class GuessRoot {
    private int count;

    private boolean error;

    private List<GuessResults> results;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean getError() {
        return this.error;
    }

    public void setResults(List<GuessResults> results) {
        this.results = results;
    }

    public List<GuessResults> getResults() {
        return this.results;
    }
}