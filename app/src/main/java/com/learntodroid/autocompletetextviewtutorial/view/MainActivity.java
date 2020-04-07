package com.learntodroid.autocompletetextviewtutorial.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learntodroid.autocompletetextviewtutorial.viewmodel.PlaceViewModel;
import com.learntodroid.autocompletetextviewtutorial.R;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlace;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlaceDetails;
import com.learntodroid.autocompletetextviewtutorial.model.Place;

import java.util.ArrayList;
import java.util.List;

import static com.learntodroid.autocompletetextviewtutorial.Config.API_KEY;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAutoCompleteTextViewFromStringArray();
//        setupAutoCompleteTextViewFromStringsResource();
//        setupAutoCompleteTextViewWithPlaceNameAndPhoto();
//        setupAutoCompleteTextViewFromRoom();
//        setupAutoCompleteTextViewFromWebService();
    }

    private void setupAutoCompleteTextViewFromStringArray() {
        String[] places = new String[]{
                "Eiffel Tower", "Great Wall of China", "Kremlin",
                "Leaning Tower of Pisa", "Pyramid of Giza", "Sydney Opera House",
                "Statue of Liberty", "Taj Mahal", "Easter Island Moai", "Machu Picchu",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, places
        );
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(
                R.id.activity_main_autoCompleteTextView
        );
        autoCompleteTextView.setAdapter(adapter);
    }

    private void setupAutoCompleteTextViewFromStringsResource() {
        String[] places = getResources().getStringArray(R.array.places);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, places
        );
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(
                R.id.activity_main_autoCompleteTextView
        );
        autoCompleteTextView.setAdapter(adapter);
    }

    private void setupAutoCompleteTextViewWithPlaceNameAndPhoto() {
        List<Place> placesList = new ArrayList<Place>();
        placesList.add(new Place("Eiffel Tower", "https://www.kids-world-travel-guide.com/images/xparis_eiffeltower_ssk500.jpeg.pagespeed.ic.2lwZPZtnJ8.jpg"));
        placesList.add(new Place("Great Wall of China", "https://www.kids-world-travel-guide.com/images/xchina_wall_ssk500.jpeg.pagespeed.ic.g_9Qpc2Tzf.jpg"));
        placesList.add(new Place("Kremlin", "https://www.kids-world-travel-guide.com/images/xbasilscathedral_FelipeFrazao_ssk-2.jpg.pagespeed.ic.dl_pQr7qVV.jpg"));
        placesList.add(new Place("Leaning Tower of Pisa", "https://www.kids-world-travel-guide.com/images/xital_pisa_500.jpg.pagespeed.ic.tOXMILMJS7.jpg"));
        placesList.add(new Place("Pyramid of Giza", "https://www.kids-world-travel-guide.com/images/xpyramids_giza_500.jpeg.pagespeed.ic.L6HuuLoR_9.jpg"));
        placesList.add(new Place("Sydney Opera House", "https://www.kids-world-travel-guide.com/images/xsydney_tooykrub_ssk500.jpg.pagespeed.ic.PQaIYJhh9A.jpg"));
        placesList.add(new Place("Statue of Liberty", "https://www.kids-world-travel-guide.com/images/usa_statueofliberty_ssk500.jpeg.pagespeed.ce.VZYrPGmdYw.jpg"));
        placesList.add(new Place("Taj Mahal", "https://www.kids-world-travel-guide.com/images/xtajmahal_500.jpg.pagespeed.ic.V6ys_qmH8t.jpg"));
        placesList.add(new Place("Easter Island Moai", "https://www.kids-world-travel-guide.com/images/xchile_moais.jpg.pagespeed.ic.iYKdd3mkXc.jpg"));
        placesList.add(new Place("Machu Picchu", "https://www.kids-world-travel-guide.com/images/xmachupicchu_500.jpg.pagespeed.ic.YLfctoiBcF.jpg"));

        AutoCompletePlaceAdapter adapter = new AutoCompletePlaceAdapter(this, placesList);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(
                R.id.activity_main_autoCompleteTextView
        );
        autoCompleteTextView.setAdapter(adapter);
    }

    private void setupAutoCompleteTextViewFromRoom() {
        PlaceViewModel placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        placeViewModel.getPlacesLiveData().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> placesList) {
                AutoCompletePlaceAdapter adapter = new AutoCompletePlaceAdapter(MainActivity.this, placesList);
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) MainActivity.this.findViewById(
                        R.id.activity_main_autoCompleteTextView
                );
                adapter.getFilter().filter(autoCompleteTextView.getText().toString());
                autoCompleteTextView.setAdapter(adapter);
            }
        });
    }

    private void setupAutoCompleteTextViewFromWebService() {
        PlaceViewModel placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) MainActivity.this.findViewById(
                R.id.activity_main_autoCompleteTextView
        );

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= autoCompleteTextView.getThreshold()) {
                    placeViewModel.searchGooglePlaces(s.toString(), true);
                }
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GooglePlace googlePlace = (GooglePlace) autoCompleteTextView.getAdapter().getItem(position);
                placeViewModel.searchGooglePlaceDetails(googlePlace.getPlaceId());
            }
        });

        placeViewModel.getGooglePlacesLiveData().observe(this, new Observer<List<GooglePlace>>() {
            @Override
            public void onChanged(List<GooglePlace> placesList) {
                AutoCompleteGooglePlaceAdapter a = new AutoCompleteGooglePlaceAdapter(MainActivity.this, placesList);
                a.getFilter().filter(autoCompleteTextView.getText().toString());
                autoCompleteTextView.setAdapter(a);
            }
        });

        placeViewModel.getGooglePlaceDetailsLiveData().observe(this, new Observer<GooglePlaceDetails>() {
            @Override
            public void onChanged(GooglePlaceDetails googlePlaceDetails) {
                TextView selectedPlaceName = findViewById(R.id.activity_main_selected_place_name);
                ImageView selectedPlacePhoto = findViewById(R.id.activity_main_selected_place_photo);

                selectedPlaceName.setText(String.format("Selected Place: %s", googlePlaceDetails.getResult().getName()));

                if (googlePlaceDetails.getResult().getPhotos() != null) {
                    String imageUrl = String.format(
                            "%s?maxwidth=300&photoreference=%s&key=%s",
                            "https://maps.googleapis.com/maps/api/place/photo",
                            googlePlaceDetails.getResult().getPhotos().get(0).getPhoto_reference(),
                            API_KEY
                    );
                    Glide.with(getApplicationContext()).load(imageUrl).into(selectedPlacePhoto);
                }
            }
        });
    }
}