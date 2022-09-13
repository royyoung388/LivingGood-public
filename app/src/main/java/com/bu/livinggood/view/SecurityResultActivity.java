package com.bu.livinggood.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;

import com.bu.livinggood.R;
import com.bu.livinggood.bean.PropertyResponse;
import com.bu.livinggood.bean.RawCrimeData;
import com.bu.livinggood.databinding.ActivitySecurityResultBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class SecurityResultActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivitySecurityResultBinding binding;
    private RawCrimeData crimeData;
    private PropertyResponse apartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecurityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        crimeData = (RawCrimeData) bundle.get("crimedata");
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

        // number of crimes to be shown on map
        int resultSize = crimeData.getIncidents().size();

        // zoom center location after the result shown
        LatLng zoomCenter = new LatLng(crimeData.getIncidents().get(0).getIncidentLatitude(), crimeData.getIncidents().get(0).getIncidentLongitude());

        // for listview adapter
        ArrayList<String> crimeInfo = new ArrayList<String>();

        // An array to store all markers
        ArrayList<Marker> markerList = new ArrayList<Marker>();

        // Add crime markers and crime info to the security listview
        for (int i = 0; i < resultSize; i++) {

            double lat = crimeData.getIncidents().get(i).getIncidentLatitude();
            double lon = crimeData.getIncidents().get(i).getIncidentLongitude();
            String name = crimeData.getIncidents().get(i).getIncidentOffense();
            String detail = crimeData.getIncidents().get(i).getIncidentOffenseDescription();
            String date = crimeData.getIncidents().get(i).getIncidentDate();

            // location of each crime
            LatLng crimeLoc = new LatLng(lat, lon);

            // Crime Info to be shown under the map as a listview
            crimeInfo.add("Crime " + (i + 1) + ": " + name + "\nDetail: " + detail + "\nDate " + date);

            // add marker and store the index of each marker to its tag
            // pin color based on different types of crime
            switch (detail) {
                case "All Other Offenses": {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(crimeLoc).title("Crime " + (i + 1) + ": " + name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    marker.setTag(new Integer(i));
                    markerList.add(marker);
                    break;
                }
                case "Disorderly Conduct": {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(crimeLoc).title("Crime " + (i + 1) + ": " + name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                    marker.setTag(new Integer(i));
                    markerList.add(marker);
                    break;
                }
                case "Shoplifting": {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(crimeLoc).title("Crime " + (i + 1) + ": " + name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    marker.setTag(new Integer(i));
                    markerList.add(marker);
                    break;
                }
                case "Simple Assault": {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(crimeLoc).title("Crime " + (i + 1) + ": " + name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                    marker.setTag(new Integer(i));
                    markerList.add(marker);
                    break;
                }
                case "Intimidation": {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(crimeLoc).title("Crime " + (i + 1) + ": " + name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    marker.setTag(new Integer(i));
                    markerList.add(marker);
                    break;
                }
                case "Burglary/Breaking & Entering": {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(crimeLoc).title("Crime " + (i + 1) + ": " + name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                    marker.setTag(new Integer(i));
                    markerList.add(marker);
                    break;
                }
                case "Destruction/Damage/Vandalism of Property": {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(crimeLoc).title("Crime " + (i + 1) + ": " + name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    marker.setTag(new Integer(i));
                    markerList.add(marker);
                    break;
                }
                default: {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(crimeLoc).title("Crime " + (i + 1) + ": " + name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    marker.setTag(new Integer(i));
                    markerList.add(marker);
                }
            }

        }

        // adapter for the security listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, crimeInfo);
        binding.crimeTypeList.setAdapter(adapter);

        // Move to the crime info corresponding to the marker in the listview
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTag() == null)
                    return false;

                marker.showInfoWindow();
                Integer index = (Integer) marker.getTag();
                binding.crimeTypeList.setSelection(index.intValue());

                return true;
            }
        });

        // Click item and move to the corresponding pin on map
        binding.crimeTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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