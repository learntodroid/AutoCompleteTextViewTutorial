package com.learntodroid.autocompletetextviewtutorial.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.learntodroid.autocompletetextviewtutorial.data.PlaceRepository;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlace;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlaceDetails;
import com.learntodroid.autocompletetextviewtutorial.model.Place;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {
    private PlaceRepository placeRepository;
    private LiveData<List<Place>> placesLiveData;
    private LiveData<List<GooglePlace>> googlePlacesLiveData;
    private MutableLiveData<GooglePlaceDetails> googlePlaceDetailsLiveData;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        placeRepository = new PlaceRepository(application);
        placesLiveData = placeRepository.getPlacesLiveData();
        googlePlacesLiveData = placeRepository.getGooglePlacesLiveData();
        googlePlaceDetailsLiveData = placeRepository.getGooglePlaceDetailsLiveData();
    }

    public LiveData<List<Place>> getPlacesLiveData() {
        return placesLiveData;
    }

    public void searchGooglePlaces(String query, boolean useSDK) {
        if (useSDK) {
            placeRepository.searchGooglePlacesWithSdk(query);
        } else {
            placeRepository.searchGooglePlacesWithApi(query);
        }
    }

    public void searchGooglePlaceDetails(String place_id) {
        placeRepository.searchGooglePlaceDetails(place_id);
    }

    public LiveData<List<GooglePlace>> getGooglePlacesLiveData() {
        return googlePlacesLiveData;
    }

    public MutableLiveData<GooglePlaceDetails> getGooglePlaceDetailsLiveData() {
        return googlePlaceDetailsLiveData;
    }
}
