package com.learntodroid.autocompletetextviewtutorial.data;

import com.learntodroid.autocompletetextviewtutorial.model.GooglePlaceDetails;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlacePrediction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesService {
    @GET("/maps/api/place/autocomplete/json")
    Call<GooglePlacePrediction> autocompletePlace(@Query("input") String input, @Query("key") String key);

    @GET("/maps/api/place/details/json")
    Call<GooglePlaceDetails> placeDetails(@Query("place_id") String place_id, @Query("fields") String fields, @Query("key") String key);
}
