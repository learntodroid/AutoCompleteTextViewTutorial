package com.learntodroid.autocompletetextviewtutorial.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.learntodroid.autocompletetextviewtutorial.R;
import com.learntodroid.autocompletetextviewtutorial.model.Place;

import java.util.ArrayList;
import java.util.List;

public class AutoCompletePlaceAdapter extends ArrayAdapter<Place> {
    private List<Place> allPlacesList;
    private List<Place> filteredPlacesList;

    public AutoCompletePlaceAdapter(@NonNull Context context, @NonNull List<Place> placesList) {
        super(context, 0, placesList);

        allPlacesList = new ArrayList<>(placesList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
              R.layout.autocomplete_item_place, parent, false
            );
        }

        ImageView placeImage = convertView.findViewById(R.id.autocomplete_item_place_image);
        TextView placeLabel = convertView.findViewById(R.id.autocomplete_item_place_label);

        Place place = getItem(position);
        if (place != null) {
            placeLabel.setText(place.getPlace());
            Glide.with(convertView).load(place.getImageUrl()).into(placeImage);
        }

        return convertView;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            filteredPlacesList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                Log.i(AutoCompletePlaceAdapter.class.getSimpleName(), "performFiltering no constraint");
                filteredPlacesList.addAll(allPlacesList);
            } else {
                Log.i(AutoCompletePlaceAdapter.class.getSimpleName(), "performFiltering " + constraint.toString());
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Place place: allPlacesList) {
                    if (place.getPlace().toLowerCase().contains(filterPattern)) {
                        filteredPlacesList.add(place);
                    }
                }
            }

            results.values = filteredPlacesList;
            results.count = filteredPlacesList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Place) resultValue).getPlace();
        }
    };
}
