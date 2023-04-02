package com.app.axzif;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.app.axzif.Utils.ApplicationConstant;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MyMapPlacesApiActivity extends AppCompatActivity {
    String TAG = "placeautocomplete";
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_map_places_api);

        txtView = findViewById(R.id.txtView);

        // Initialize Places.
       // Places.initialize(getApplicationContext(),getResources().getString( R.string.google_place_key ));
        Places.initialize(getApplicationContext(), ApplicationConstant.INSTANCE.key);
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields( Arrays.asList( Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS,Place.Field.TYPES,Place.Field.ADDRESS));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                txtView.setText("Place: " +place.getName()+",\nID: "+place.getId()+ ",\nLatLng: " + place.getLatLng());
                Log.i(TAG, "Place: " + place.getName() + ",ID " + place.getId()+ ",LatLng " + place.getLatLng()+ ",ADDRESS" + place.getAddress()+ ",TYPES " + place.getTypes());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
}