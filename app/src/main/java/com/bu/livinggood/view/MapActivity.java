package com.bu.livinggood.view;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.bu.livinggood.controller.RatingController.PREF_FOOD;
import static com.bu.livinggood.controller.RatingController.PREF_SECURITY;
import static com.bu.livinggood.controller.RatingController.PREF_STORE;
import static com.bu.livinggood.controller.RatingController.PREF_TRANSIT;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bu.livinggood.BuildConfig;
import com.bu.livinggood.R;
import com.bu.livinggood.bean.Place;
import com.bu.livinggood.bean.PropertyResponse;
import com.bu.livinggood.bean.RatingSet;
import com.bu.livinggood.bean.RawCrimeData;
import com.bu.livinggood.bean.User;
import com.bu.livinggood.controller.APIController;
import com.bu.livinggood.controller.FirebaseController;
import com.bu.livinggood.controller.RatingController;
import com.bu.livinggood.databinding.ActivityMapBinding;
import com.bu.livinggood.databinding.NavHeaderMapBinding;
import com.bu.livinggood.tools.Tools;
import com.bu.livinggood.view.adapter.MapDetailsAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = MapActivity.class.getSimpleName();
    private static final double RADIUS = 1;

    private GoogleMap map;
    private ActivityMapBinding binding;
    private NavHeaderMapBinding navHeaderMapBinding;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private MapDetailsAdapter detailsAdapter;
    private List<Marker> markerList = new ArrayList<>();

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(40.7128, -74.0060);
    private static final int DEFAULT_ZOOM = 15;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;
    private LatLng center;

    //Firebase initialization
    private FirebaseAuth.AuthStateListener mAuthListener;
    private User user;
    private FirebaseController firebaseController;
    private TextView nav_title;
    private ImageView nav_image;
    private RawCrimeData crimeData;
    private Place restaurant, transportation, store;
    private List<PropertyResponse> propertyResponses;
    private List<RatingSet> ratingSetList;

    private ReentrantLock lock;
    private int lockCounter;
    private final int APICOUNT = 5;

    private APIController apiController;
    private RatingController ratingController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize data
        apiController = new APIController();
        lock = new ReentrantLock();
        lockCounter = 0;

        // initialize map
        initMap();

        // set NavigationBar
        setNavBar();
        setupLogout();

        initView();
    }

    /**
     * Send all API request
     *
     * @param longitude
     * @param latitude
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void requestAPI(double longitude, double latitude) {
        // 1. set radius
        Log.i(TAG, "API Radius: " + binding.mapSkRadius.getProgress() * 0.1 + 0.5);
        apiController.setRadius(binding.mapSkRadius.getProgress() * 0.1 + 0.5);

        // 2. Request property data
        if (BuildConfig.DEBUG) {
            // property
            apiController.requestProperty(longitude, latitude,
                    Tools.intWithPlus(binding.mapSpBedroom.getSelectedItem().toString()),
                    Tools.intWithPlus(binding.mapSpBathroom.getSelectedItem().toString()),
                    new Callback<List<PropertyResponse>>() {
                        @Override
                        public void onResponse(Call<List<PropertyResponse>> call, Response<List<PropertyResponse>> response) {
                            propertyResponses = response.body();
                            Log.i(TAG, "property request finished");
                            requestFinished();
                        }

                        @Override
                        public void onFailure(Call<List<PropertyResponse>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        } else {
            // use saved data
            try {
                ObjectInputStream ois = new ObjectInputStream(getResources().openRawResource(R.raw.property));
                propertyResponses = (List<PropertyResponse>) ois.readObject();
                Log.i(TAG, "property request finished");
                requestFinished();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // 3. Request crime data
        if (BuildConfig.DEBUG) {
            // crime data
            apiController.requestRawCrimeData(longitude, latitude, new Callback<RawCrimeData>() {
                @Override
                public void onResponse(Call<RawCrimeData> call, Response<RawCrimeData> response) {
                    crimeData = response.body();
                    Log.i(TAG, "crimedata request finished");
                    requestFinished();
                }

                @Override
                public void onFailure(Call<RawCrimeData> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            // use saved data
            try {
                ObjectInputStream ois = new ObjectInputStream(getResources().openRawResource(R.raw.rawcrime));
                crimeData = (RawCrimeData) ois.readObject();
                Log.i(TAG, "crimedata request finished");
                requestFinished();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // 4. Request google map nearby restaurant data
        apiController.requestRestaurant(longitude, latitude, new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                restaurant = response.body();
                Log.i(TAG, "restaurant request finished");
                requestFinished();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // 5. Request google map nearby transportation data
        apiController.requestTransportation(longitude, latitude, new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                transportation = response.body();
                Log.i(TAG, "transportation request finished");
                requestFinished();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // 6. Request google map nearby store data
        apiController.requestStore(longitude, latitude, new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                store = response.body();
                Log.i(TAG, "store request finished");
                requestFinished();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Execute when all api request finished
     * Use ReentrantLock to coordinate all API
     * Modify APICOUNT when add/reduce API amount
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestFinished() {
        // 1. lock
        lock.lock();
        // 2. modify counter
        try {
            lockCounter += 1;
            // 3. Still has API not complete, return
            if (lockCounter < APICOUNT)
                return;
        } finally {
            lock.unlock();
        }
        // 4. All API completed, reset counter
        lockCounter = 0;

        // 5. calculate rating using API response data
        ratingController = new RatingController(center, propertyResponses, restaurant, transportation, store, crimeData,
                Tools.floorIntWithPlus(binding.mapSpMinPrice.getSelectedItem().toString()),
                Tools.ceilIntWithPlus(binding.mapSpMaxPrice.getSelectedItem().toString()));
        // 6. clear old rating preferences
        ratingController.setPreference(0);
        // 7. set preferences
        if (binding.mapCbFood.isChecked())
            ratingController.setPreference(PREF_FOOD);
        if (binding.mapCbSecurity.isChecked())
            ratingController.setPreference(PREF_SECURITY);
        if (binding.mapCbStore.isChecked())
            ratingController.setPreference(PREF_STORE);
        if (binding.mapCbTransit.isChecked())
            ratingController.setPreference(PREF_TRANSIT);

        // 8. get rating result
        ratingSetList = ratingController.getRatingSet(apiController.getRadius());

        // 9. add marker on the map
        for (int i = 0; i < ratingSetList.size(); i++) {
            PropertyResponse property = ratingSetList.get(i).getApartment();
            LatLng latLng = new LatLng(property.getLatitude(), property.getLongitude());
            // add marker
            Marker marker = map.addMarker(new MarkerOptions().position(latLng)
                    .title(String.valueOf(ratingSetList.get(i).getOverallScore())));
            marker.setTag(i);
            markerList.add(marker);
        }

        // move to first marker
        if (markerList.size() > 0)
            markerList.get(0).showInfoWindow();
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(markerList.get(0).getPosition(), DEFAULT_ZOOM));

        // 10. set recycle view adapter, show the data
        detailsAdapter.setData(ratingSetList);
        binding.mapContent.rvDetails.setAdapter(detailsAdapter);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if (marker.getTag() == null)
            return false;
        int position = (int) marker.getTag();
        Log.i(TAG, "marker clicked: " + position);
        binding.mapContent.rvDetails.scrollToPosition(position);
        return false;
    }

    private void initMap() {
        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /***
     * Initialize all view stuff
     */
    private void initView() {
        // recycle view
        binding.mapContent.rvDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // recycle view adapter
        detailsAdapter = new MapDetailsAdapter((view, position, bitmap) -> {
            Intent intent = new Intent(MapActivity.this, RatingPageActivity.class);
            // how to get data:
            // getIntent().getSerializableExtra("name")
            PropertyResponse propertyResponse = ratingSetList.get(position).getApartment();
            LatLng latLng = new LatLng(propertyResponse.getLatitude(), propertyResponse.getLongitude());
            intent.putExtra("crimedata", ratingController.getRawCrimeDataByRadius(latLng, RADIUS));
            intent.putExtra("restaurant", ratingController.getFoodByRadius(latLng, RADIUS));
            intent.putExtra("transportation", ratingController.getTransitByRadius(latLng, RADIUS));
            intent.putExtra("store", ratingController.getStoreByRadius(latLng, RADIUS));
            intent.putExtra("rating", ratingSetList.get(position));
            intent.putExtra("apartment", ratingSetList.get(position).getApartment());
            intent.putExtra("bitmap", bitmap);
            Bundle bundle = new Bundle(1);
            // how to get bitmap:
            // Bitmap bitmap = getIntent().getExtras().getParcelable("bitmap");
            // bitmap may be null
            bundle.putParcelable("bitmap", bitmap);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        });
        binding.mapContent.rvDetails.setAdapter(detailsAdapter);
        // Use PagerSnapHelper to change scroll animation
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.mapContent.rvDetails);
        // Show corresponding marker when scroll card view
        binding.mapContent.rvDetails.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState != SCROLL_STATE_IDLE)
                    return;
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // get current cardview position
                int position = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (position >= 0) {
                    // show corresponding marker
                    Marker marker = markerList.get(position);
                    marker.showInfoWindow();
                    // move camera
                    map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                }
            }
        });

        // drawer open button
        binding.mapContent.btDrawer.setOnClickListener(view -> {
            binding.drawerLayout.open();
        });

        // Radius seek bar. Modify radius text when seek bar changes.
        binding.mapSkRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            double pval = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // calculate radius: from 0.5 mi to 2 mi
                pval = progress * 0.1 + 0.5;
                binding.mapTvRadius.setText(String.format(Locale.US, "%.1f MI", pval));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // My location button. Move to my location when clicked
        binding.mapContent.btLocation.setOnClickListener(view -> {
            getDeviceLocation();
        });

        // Filter search button in NavigationView
        binding.btSearch.setOnClickListener(view -> {
            // do search again
            binding.mapContent.svSearch.setQuery(binding.mapContent.svSearch.getQuery(), true);
            binding.drawerLayout.close();
        });

        // search view
        binding.mapContent.svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // IMPORTANT: avoid call onQueryTextSubmit twice
                binding.mapContent.svSearch.clearFocus();
                // 1. Ignore empty String
                if (s == null || s.isEmpty())
                    return false;

                if (!BuildConfig.DEBUG && !s.equalsIgnoreCase("boston university")) {
                    Toast.makeText(MapActivity.this, "Please Input Boston University", Toast.LENGTH_SHORT).show();
                    return false;
                }

                // 2. clear all marker
                map.clear();
                List<Address> addressList = null;
                Geocoder geocoder = new Geocoder(MapActivity.this);
                try {
                    // 3. Convert string to physical address
                    addressList = geocoder.getFromLocationName(s, 1);
                    if (addressList.size() == 0) {
                        Toast.makeText(MapActivity.this, "Can't find the location", Toast.LENGTH_SHORT).show();
                        // Get the current location of the device and set the position of the map.
                        getDeviceLocation();
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 5. Get lat and lng
                Address address = addressList.get(0);
                center = new LatLng(address.getLatitude(), address.getLongitude());
                //  6. show on the map
                map.addMarker(new MarkerOptions().position(center).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(center, DEFAULT_ZOOM));

                // 7. send api request
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requestAPI(address.getLongitude(), address.getLatitude());
                } else {
                    Toast.makeText(MapActivity.this, "Not supported Android Version", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void setNavBar() {
        //check login
        nav_image = binding.navHeader.imageView;
        nav_title = binding.navHeader.title;
        ReentrantLock lock1 = new ReentrantLock();

        firebaseController = new FirebaseController();
        if (firebaseController.checkLogin()) {
            DatabaseReference database = firebaseController.getUserPathReference();
            lock1.lock();
            try {
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(User.class);
                        setNavHeader(user.getName());
                        lock1.unlock();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        lock1.unlock();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //get user from database
    }

    private void setNavHeader(String name) {
        nav_title.setVisibility(View.VISIBLE);
        nav_title.setText(name);
        nav_image.setImageResource(R.drawable.avatar2);
    }

    private void resetNavHeader() {
        nav_title.setVisibility(View.GONE);
        nav_title.setText("");
        nav_image.setImageResource(R.drawable.fedetort_avatar);
    }

    // sign out
    private void setupLogout() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // Sign in logic here.
                    Intent i = new Intent(MapActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };
        binding.tvLogout.setOnClickListener(view -> {
            firebaseController.getAuth().signOut();
            user = null;
            Toast.makeText(MapActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            resetNavHeader();
        });
    }

    /**
     * receive data from welcome activity
     */
    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        // get data
        String search = bundle.getString("SearchVal");
        int minPrice = bundle.getInt("MinPrice");
        int maxPrice = bundle.getInt("MaxPrice");
        int bedroom = bundle.getInt("Bedroom");
        int bathroom = bundle.getInt("Bathroom");
        int radius = bundle.getInt("Radius");

        // if no search string, only show my location
        if (search == null || search.isEmpty())
            getDeviceLocation();

        // show data in map activity
        binding.mapSpMaxPrice.setSelection(maxPrice);
        binding.mapSpMinPrice.setSelection(minPrice);
        binding.mapSpBedroom.setSelection(bedroom);
        binding.mapSpBathroom.setSelection(bathroom);
        binding.mapSkRadius.setProgress(radius);
        binding.mapCbFood.setChecked(bundle.getBoolean("Food"));
        binding.mapCbSecurity.setChecked(bundle.getBoolean("Security"));
        binding.mapCbTransit.setChecked(bundle.getBoolean("Transit"));
        binding.mapCbStore.setChecked(bundle.getBoolean("Store"));
        binding.mapContent.svSearch.setQuery(search, true);
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
        map = googleMap;

        // request location permission
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Show data from intent
        getIntentData();

        // marker click listener
        map.setOnMarkerClickListener(this);
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    /**
     * get current location
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        map.animateCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
//                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}