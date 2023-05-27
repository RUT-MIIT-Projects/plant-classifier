package com.plants.app.buttons;

public class Button {
    private String name;
    private Integer image;

    public Button(String name, Integer image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Integer getImage() {
        return image;
    }
}
