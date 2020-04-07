package com.learntodroid.autocompletetextviewtutorial.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.learntodroid.autocompletetextviewtutorial.R;
import com.learntodroid.autocompletetextviewtutorial.model.GooglePlace;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteGooglePlaceAdapter extends ArrayAdapter<GooglePlace> {
    private List<GooglePlace> allPlacesList;
    private List<GooglePlace> filteredPlacesList;

    public AutoCompleteGooglePlaceAdapter(@NonNull Context context, @NonNull List<GooglePlace> placesList) {
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
              R.layout.autocomplete_item_googleplace, parent, false
            );
        }

        TextView placeLabel = convertView.findViewById(R.id.autocomplete_item_googleplace_label);
        TextView place2Label = convertView.findViewById(R.id.autocomplete_item_googleplace_label2);

        GooglePlace place = getItem(position);
        if (place != null) {
            placeLabel.setText(place.getPlaceName());
            place2Label.setText(place.getPlaceName2());
        }

        return convertView;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            filteredPlacesList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                Log.i(AutoCompleteGooglePlaceAdapter.class.getSimpleName(), "performFiltering no constraint");
                filteredPlacesList.addAll(allPlacesList);
            } else {
                Log.i(AutoCompleteGooglePlaceAdapter.class.getSimpleName(), "performFiltering " + constraint.toString());
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GooglePlace place: allPlacesList) {
                    if (place.getPlaceName().toLowerCase().contains(filterPattern)) {
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
            return ((GooglePlace) resultValue).getPlaceName();
        }
    };
}
