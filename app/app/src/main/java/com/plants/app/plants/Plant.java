package com.plants.app.plants;


public class Plant{
    private String name;
    private String watering;
    private String insolation;
    private String pruning;
    private String soil;
    private String pot_size;
    private String picture;

    public Plant(String name, String watering, String insolation, String pruning, String soil, String pot_size, String picture) {
        this.name = name;
        this.watering = watering;
        this.insolation = insolation;
        this.pruning = pruning;
        this.soil = soil;
        this.pot_size = pot_size;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getWatering() {
        return watering;
    }

    public String getInsolation() {
        return insolation;
    }

    public String getPruning() {
        return pruning;
    }

    public String getSoil() {
        return soil;
    }

    public String getPot_size() {
        return pot_size;
    }

    public String getPicture() {
        return picture;
    }
}
