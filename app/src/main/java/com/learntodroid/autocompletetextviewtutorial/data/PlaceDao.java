package com.learntodroid.autocompletetextviewtutorial.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.learntodroid.autocompletetextviewtutorial.model.Place;

import java.util.List;

@Dao
public interface PlaceDao {
    @Insert
    void insertAll(List<Place> placesList);

    @Query("SELECT * FROM place_table ORDER BY place ASC")
    LiveData<List<Place>> getAllPlaces();
}
