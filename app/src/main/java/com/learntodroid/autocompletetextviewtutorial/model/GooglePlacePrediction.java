package com.learntodroid.autocompletetextviewtutorial.model;

import java.util.List;

public class GooglePlacePrediction {
    List<Prediction> predictions;

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public class Prediction {
        String description;
        String place_id;
        StructuredFormatting structured_formatting;

        public String getDescription() {
            return description;
        }

        public String getPlace_id() {
            return place_id;
        }

        public StructuredFormatting getStructured_formatting() {
            return structured_formatting;
        }

        public class StructuredFormatting {
            String main_text;
            String secondary_text;

            public String getMain_text() {
                return main_text;
            }

            public String getSecondary_text() {
                return secondary_text;
            }
        }
    }
}
