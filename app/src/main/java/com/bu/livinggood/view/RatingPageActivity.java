package com.bu.livinggood.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bu.livinggood.R;
import com.bu.livinggood.bean.CrimeData;
import com.bu.livinggood.bean.Place;
import com.bu.livinggood.bean.PropertyResponse;
import com.bu.livinggood.bean.RatingSet;
import com.bu.livinggood.bean.RawCrimeData;
import com.bu.livinggood.controller.FirebaseController;
import com.bu.livinggood.databinding.ActivityRatingPageBinding;
import com.bu.livinggood.view.adapter.RatingAdapter;

public class RatingPageActivity extends AppCompatActivity {
    public static int TYPES_NUM = 4;
    private ActivityRatingPageBinding binding;
    private RecyclerView recyclerView;

    private RawCrimeData crimeData;
    private CrimeData incidents;
    private Place restaurant, transportation, store;
    private PropertyResponse propertyResponse;
    private RatingSet ratingSet;
    private FirebaseController firebaseController;
    //image storage
    //Type names
    private String[] types;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRatingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();

        // get string
        types = this.getResources().getStringArray(R.array.rating_type);

        //Set apartment name and overall score
        binding.apartName.setText(propertyResponse.getAddressLine1());

        //find recyclerview
        recyclerView = binding.recyclerView;

        // Top image
        if (bitmap != null) {
            binding.topimage.setImageBitmap(bitmap);
        }

        String overall = String.valueOf(ratingSet.getOverallScore());
        binding.overallScore.setText(overall);
        binding.OverallratingBar.setRating(Float.parseFloat(overall));
        //setup listener for review
        setTextView_Listener();
        //setup recyclerview
        setAdapter();
    }

    //start review activity
    private void setTextView_Listener() {
        firebaseController = new FirebaseController();

        binding.allReview.setOnClickListener(view -> {
            if (firebaseController.checkLogin()) {
                Intent review = new Intent(this, ReviewActivity.class);
                review.putExtra("lat", propertyResponse.getLatitude());
                review.putExtra("long", propertyResponse.getLongitude());
                startActivity(review);
            } else {
                Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getData() {
        Intent intent = getIntent();
        crimeData = (RawCrimeData) intent.getSerializableExtra("crimedata");
        ratingSet = (RatingSet) intent.getSerializableExtra("rating");
        propertyResponse = (PropertyResponse) intent.getSerializableExtra("apartment");

        restaurant = (Place) intent.getSerializableExtra("restaurant");
        transportation = (Place) intent.getSerializableExtra("transportation");
        store = (Place) intent.getSerializableExtra("store");

        incidents = (CrimeData) intent.getSerializableExtra("incidents");

        // Top image
        //Bundle bundle = getIntent().getExtras();
        bitmap = (Bitmap) intent.getExtras().getParcelable("bitmap");

    }

    private void setAdapter() {

        RatingAdapter adapter = new RatingAdapter(this, (view, position) -> {
            if (position == 0) {
                if (crimeData.getIncidents().size() == 0) {
                    Toast.makeText(this, "No crime around here!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(RatingPageActivity.this, SecurityResultActivity.class);
                intent.putExtra("apartment", propertyResponse);
                intent.putExtra("crimedata", crimeData);
                intent.putExtra("incidents", incidents);
                startActivity(intent);
            } else if (position == 1) {
                if (restaurant.getResults().size() == 0) {
                    Toast.makeText(this, "No restaurant around here!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(RatingPageActivity.this, FoodResultActivity.class);
                intent.putExtra("apartment", propertyResponse);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            } else if (position == 2) {
                if (transportation.getResults().size() == 0) {
                    Toast.makeText(this, "No bus station around here!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(RatingPageActivity.this, TransportationResultActivity.class);
                intent.putExtra("apartment", propertyResponse);
                intent.putExtra("transportation", transportation);
                startActivity(intent);
            } else if (position == 3) {
                if (store.getResults().size() == 0) {
                    Toast.makeText(this, "No store around here!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(RatingPageActivity.this, StoreResultActivity.class);
                intent.putExtra("apartment", propertyResponse);
                intent.putExtra("store", store);
                startActivity(intent);
            }
        }, types, ratingSet);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}