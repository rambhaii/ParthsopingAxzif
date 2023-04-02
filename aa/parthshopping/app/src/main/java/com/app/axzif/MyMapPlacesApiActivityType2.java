package com.app.axzif;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.axzif.Utils.ApplicationConstant;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MyMapPlacesApiActivityType2 extends AppCompatActivity
{
    String TAG = "placeautocomplete";
    TextView txtView;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    RelativeLayout locationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_map_places_api_type2);

        txtView = findViewById(R.id.txtView);
        locationMarker=(RelativeLayout) findViewById(R.id.locationMarker1);

        Places.initialize(getApplicationContext(), ApplicationConstant.INSTANCE.key);
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields( Arrays.asList( Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS,Place.Field.TYPES,Place.Field.ADDRESS));

        // Set up a PlaceSelectionListener to handle the response.

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS,Place.Field.TYPES,Place.Field.ADDRESS,Place.Field.PHONE_NUMBER);

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(MyMapPlacesApiActivityType2.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                txtView.setText("Place!!: " +place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                 Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

                // The user canceled the operation.

            }
        }
    }


}