package com.learntodroid.autocompletetextviewtutorial.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.learntodroid.autocompletetextviewtutorial.model.GooglePlace;
import com.learntodroid.autocompletetextviewtutorial.model.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Place.class, GooglePlace.class}, version = 1, exportSchema = false)
public abstract class PlaceDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
    public abstract GooglePlaceDao googlePlaceDao();

    private static volatile PlaceDatabase INSTANCE;

    public static PlaceDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlaceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            PlaceDatabase.class,
                            "place_database"
                    )
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<Place> placesList = new ArrayList<>();

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

                                            getDatabase(context.getApplicationContext())
                                                    .placeDao().insertAll(placesList);
                                        }
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
