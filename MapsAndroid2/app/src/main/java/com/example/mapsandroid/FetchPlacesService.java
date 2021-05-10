package com.example.mapsandroid;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.udb.mapsandroid.R;


public class FetchPlacesService extends IntentService {
    //Hará las veces de identificador
    public static String NOTIFICATION = "edu.udb.mapsandroid";
    public static String RESULT = "dataResult";
    private ArrayList<Place> result = new ArrayList();

    //Un identificador de clase, se pasa como parámetro en súper.
    public FetchPlacesService() {
        super("fetchplaces");
    }

    @Override

    protected void onHandleIntent(Intent intent) {
        result.add(new Place("Parque La Glorieta Urbanizacion Cima IV", 13.676365863210318, -89.25482096489257));
        result.add(new Place("Parque Cima 4", 13.671063883863876, -89.23038437106932));
        result.add(new Place("Parque Memorial La Resurrección", 13.715769, -89.153387));
        result.add(new Place("Parque Metropolitano El Talapo", 13.671063883863876, -89.23038437106932));
        result.add(new Place("Parque Lomas de Versalles", 13.680228454923578, -89.23008304448851));
        result.add(new Place("Parque Colonia Loma Linda", 13.67644366672812, -89.22755128573607));
        result.add(new Place("Parque Cima 2", 13.3494248, -89.6837175));
        publishData();
    }


    public void publishData() {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    /**
     * A demo class that stores and retrieves data objects with each marker.
     */
    public class MarkerDemoActivity extends AppCompatActivity implements
            GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
        private final LatLng PERTH = new LatLng(-31.952854, 115.857342);
        private final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
        private final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
        private Marker markerPerth;
        private Marker markerSydney;
        private Marker markerBrisbane;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            SupportMapFragment mapFragment = (SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        /**
         * Called when the map is ready.
         */
        @Override
        public void onMapReady(GoogleMap map) {
            markerPerth = map.addMarker(new
                    MarkerOptions().position(PERTH).title("Perth"));
            markerPerth.setTag(0);
            markerSydney = map.addMarker(new
                    MarkerOptions().position(SYDNEY).title("Sydney"));
            markerSydney.setTag(0);
            markerBrisbane = map.addMarker(new
                    MarkerOptions().position(BRISBANE).title("Brisbane"));
            markerBrisbane.setTag(0);
            map.setOnMarkerClickListener(this);
        }

        /**
         * Called when the user clicks a marker.
         */
        @Override
        public boolean onMarkerClick(final Marker marker) {

// Retrieve the data from the marker.
            Integer clickCount = (Integer) marker.getTag();
// Check if a click count was set, then display the click count.
            if (clickCount != null) {
                clickCount = clickCount + 1;
                marker.setTag(clickCount);
                Toast.makeText(this, marker.getTitle() + " has been clicked " +
                        clickCount + " times.", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }
}

