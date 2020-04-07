package com.learntodroid.autocompletetextviewtutorial.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "place_table")
public class Place {
    @PrimaryKey
    @NonNull
    private String place;

    private String imageUrl;

    public Place(String place, String imageUrl) {
        this.place = place;
        this.imageUrl = imageUrl;
    }

    public String getPlace() {
        return place;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
