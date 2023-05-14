package com.plants.app.adapters.plants;

import android.os.Parcel;
import android.os.Parcelable;

public class Plant implements Parcelable {
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

    //Start Parcelable
    protected Plant(Parcel in) {
        name = in.readString();
        watering = in.readString();
        insolation = in.readString();
        pruning = in.readString();
        soil = in.readString();
        pot_size = in.readString();
        picture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(watering);
        dest.writeString(insolation);
        dest.writeString(pruning);
        dest.writeString(soil);
        dest.writeString(pot_size);
        dest.writeString(picture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Plant> CREATOR = new Creator<Plant>() {
        @Override
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };
    //End Parcelable

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
