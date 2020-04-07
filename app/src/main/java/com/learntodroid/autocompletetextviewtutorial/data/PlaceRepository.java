package com.learntodroid.autocompletetextviewtutorial.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlace;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlaceDetails;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlacePrediction;
import com.learntodroid.autocompletetextviewtutorial.model.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.learntodroid.autocompletetextviewtutorial.Config.API_KEY;

public class PlaceRepository {
    private PlaceDao placeDao;
    private LiveData<List<Place>> placesLiveData;

    private PlacesClient googlePlacesClient;
    private AutocompleteSessionToken googleSessionToken;

    private GooglePlaceDao googlePlaceDao;
    private LiveData<List<GooglePlace>> googlePlacesLiveData;

    private GooglePlacesService googlePlacesAutoCompleteService;
    private MutableLiveData<GooglePlaceDetails> googlePlaceDetailsLiveData;

    public PlaceRepository(Application application) {
        PlaceDatabase db = PlaceDatabase.getDatabase(application);
        placeDao = db.placeDao();
        placesLiveData = placeDao.getAllPlaces();

        Places.initialize(application, API_KEY);
        googlePlacesClient = Places.createClient(application);
        googleSessionToken = AutocompleteSessionToken.newInstance();
        googlePlaceDao = db.googlePlaceDao();
        googlePlacesLiveData = googlePlaceDao.getAllGooglePlaces();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(" https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        googlePlacesAutoCompleteService = retrofit.create(GooglePlacesService.class);
        googlePlaceDetailsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Place>> getPlacesLiveData() {
        return placesLiveData;
    }

    public void searchGooglePlacesWithSdk(final String query) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(googleSessionToken)
                .setQuery(query)
                .build();

        googlePlacesClient.findAutocompletePredictions(request).addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
            @Override
            public void onSuccess(FindAutocompletePredictionsResponse response) {
                final List<GooglePlace> googlePlaces = new ArrayList<>();
                for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                    GooglePlace gp = new GooglePlace(
                      prediction.getPlaceId(),
                      prediction.getPrimaryText(null).toString(),
                      prediction.getSecondaryText(null).toString()
                    );
                    googlePlaces.add(gp);
                }
                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        googlePlaceDao.insertAll(googlePlaces);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                }
            }
        });
    }

    public void searchGooglePlacesWithApi(final String query) {
        googlePlacesAutoCompleteService.autocompletePlace(query, API_KEY).enqueue(new Callback<GooglePlacePrediction>() {
            @Override
            public void onResponse(Call<GooglePlacePrediction> call, Response<GooglePlacePrediction> response) {
                List<GooglePlace> googlePlaces = new ArrayList<>();
                for (GooglePlacePrediction.Prediction prediction: response.body().getPredictions()) {
                    GooglePlace g = new GooglePlace(
                            prediction.getPlace_id(),
                            prediction.getStructured_formatting().getMain_text(),
                            prediction.getStructured_formatting().getSecondary_text()
                    );
                    googlePlaces.add(g);
                }

                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        googlePlaceDao.insertAll(googlePlaces);
                    }
                });
            }

            @Override
            public void onFailure(Call<GooglePlacePrediction> call, Throwable t) {
            }
        });
    }

    public void searchGooglePlaceDetails(String place_id) {
        googlePlacesAutoCompleteService.placeDetails(
                place_id,
                "name,rating,formatted_phone_number,photos",
                API_KEY
        ).enqueue(new Callback<GooglePlaceDetails>() {
            @Override
            public void onResponse(Call<GooglePlaceDetails> call, Response<GooglePlaceDetails> response) {
                googlePlaceDetailsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GooglePlaceDetails> call, Throwable t) {

            }
        });
    }

    public LiveData<List<GooglePlace>> getGooglePlacesLiveData() {
        return googlePlacesLiveData;
    }

    public MutableLiveData<GooglePlaceDetails> getGooglePlaceDetailsLiveData() {
        return googlePlaceDetailsLiveData;
    }
}
