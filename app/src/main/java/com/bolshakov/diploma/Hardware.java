package com.bolshakov.diploma;

public class Hardware {
    String params;
    int cost;

    public Hardware(String params, int cost) {
        this.params = params;
        this.cost = cost;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
