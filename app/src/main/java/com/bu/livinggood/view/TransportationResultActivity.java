package com.bu.livinggood.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;

import com.bu.livinggood.R;
import com.bu.livinggood.bean.Place;
import com.bu.livinggood.bean.PropertyResponse;
import com.bu.livinggood.databinding.ActivityTransportationResultBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TransportationResultActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTransportationResultBinding binding;
    private Place transportation;
    private PropertyResponse apartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTransportationResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        transportation = (Place) bundle.get("transportation");
        apartment = (PropertyResponse) bundle.get("apartment");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // add apartment marker
        mMap.addMarker(new MarkerOptions().position(new LatLng(apartment.getLatitude(), apartment.getLongitude()))
                .title(apartment.getAddressLine1())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // number of results to be shown on map
        int resultSize = transportation.getResults().size();

        // zoom center location after the result shown
        LatLng zoomCenter = new LatLng(transportation.getResults().get(0).getGeometry().getLocation().getLat(),
                transportation.getResults().get(0).getGeometry().getLocation().getLng());

        // for listview adapter
        ArrayList<String> transportationInfo = new ArrayList<String>();

        // An array to store all markers
        ArrayList<Marker> markerList = new ArrayList<Marker>();

        // Customize and resize the icon pin
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.trainimage);
        Bitmap icon = Bitmap.createScaledBitmap(imageBitmap, 80, 80, false);

        for (int i = 0; i < resultSize; i++) {
            double lat = transportation.getResults().get(i).getGeometry().getLocation().getLat();
            double lon = transportation.getResults().get(i).getGeometry().getLocation().getLng();
            String name = transportation.getResults().get(i).getName();

            // location of each bus stop found
            LatLng transportationLoc = new LatLng(lat, lon);

            // Bus station Info to be shown under the map as a listview
            transportationInfo.add(name);

            // add marker and store the index of each marker to its tag
            Marker marker = mMap.addMarker(new MarkerOptions().position(transportationLoc).title(name).icon(BitmapDescriptorFactory.fromBitmap(icon)));
            marker.setTag(new Integer(i));
            markerList.add(marker);
        }

        // adapter for the transportation listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, transportationInfo);
        binding.transportationList.setAdapter(adapter);

        // Move to the bus stop info corresponding to the marker in the listview
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTag() == null)
                    return false;

                marker.showInfoWindow();
                Integer index = (Integer) marker.getTag();
                binding.transportationList.setSelection(index.intValue());

                return true;
            }
        });

        // Click item and move to the corresponding pin on map
        binding.transportationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Marker marker = markerList.get(i);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14.0f));
                marker.showInfoWindow();
            }
        });

        // default zoom and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomCenter, 14.0f));
    }
}