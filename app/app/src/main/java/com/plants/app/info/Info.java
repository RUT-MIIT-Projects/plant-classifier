package com.plants.app.info;

public class Info {
    private Integer count;
    private Integer imageId;

    public Info(Integer count, Integer imageId) {
        this.count = count;
        this.imageId = imageId;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getImage() {
        return imageId;
    }
}
