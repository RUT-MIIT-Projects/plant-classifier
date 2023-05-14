package com.plants.app.adapters.user;

public class User {
    private String username;
    private Integer countEchinocactus;
    private Integer countMimosa;
    private Integer countMonstera;
    private Integer countOrchid;
    private Integer countRose;

    public User(String username) {
        this.username = username;
        this.countEchinocactus = 0;
        this.countMimosa = 0;
        this.countMonstera = 0;
        this.countOrchid = 0;
        this.countRose = 0;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCountEchinocactus() {
        countEchinocactus++;
    }

    public void setCountMimosa() {
        countMimosa++;
    }

    public void setCountMonstera() {
        countMonstera++;
    }

    public void setCountOrchid() {
        countOrchid++;
    }

    public void setCountRose() {
        countRose++;
    }

    public String getUsername() {
        return username;
    }

    public Integer getCountEchinocactus() {
        return countEchinocactus;
    }

    public Integer getCountMimosa() {
        return countMimosa;
    }

    public Integer getCountMonstera() {
        return countMonstera;
    }

    public Integer getCountOrchid() {
        return countOrchid;
    }

    public Integer getCountRose() {
        return countRose;
    }
}
