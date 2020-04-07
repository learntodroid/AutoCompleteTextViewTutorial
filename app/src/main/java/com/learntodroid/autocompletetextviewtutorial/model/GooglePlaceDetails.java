package com.learntodroid.autocompletetextviewtutorial.model;

import java.util.List;

public class GooglePlaceDetails {
    public GooglePlaceDetailsResult result;

    public GooglePlaceDetailsResult getResult() {
        return result;
    }

    public class GooglePlaceDetailsResult {
        public String name;
        public String formatted_phone_number;
        public float rating;
        public List<GooglePlacePhoto> photos;

        public String getName() {
            return name;
        }

        public String getFormatted_phone_number() {
            return formatted_phone_number;
        }

        public float getRating() {
            return rating;
        }

        public List<GooglePlacePhoto> getPhotos() {
            return photos;
        }

        public class GooglePlacePhoto {
            public int height;
            public int width;
            public String photo_reference;

            public int getHeight() {
                return height;
            }

            public int getWidth() {
                return width;
            }

            public String getPhoto_reference() {
                return photo_reference;
            }
        }
    }
}

