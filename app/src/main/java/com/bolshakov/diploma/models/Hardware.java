package com.bolshakov.diploma.models;

public class Hardware {
    public String id;
    public String name;
    public String params;
    public String cost;
    public String isActive;

    public Hardware(String name, String params, String cost, String isActive) {
        this.name = name;
        this.params = params;
        this.cost = cost;
        this.isActive = isActive;
    }

    public Hardware() {
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hardware{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", params='" + params + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
