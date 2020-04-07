package com.learntodroid.autocompletetextviewtutorial.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "google_place_table")
public class GooglePlace {
    @PrimaryKey
    @NonNull
    private String placeId;

    private String placeName;

    private String placeName2;

    public GooglePlace(String placeId, String placeName, String placeName2) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeName2 = placeName2;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceName2() {
        return placeName2;
    }
}
