package com.bu.livinggood.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bu.livinggood.bean.Review;
import com.bu.livinggood.bean.User;
import com.bu.livinggood.controller.FirebaseController;
import com.bu.livinggood.databinding.ActivityReviewBinding;
import com.bu.livinggood.view.adapter.ReviewAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ReviewActivity extends AppCompatActivity {
    private ActivityReviewBinding binding;
    private RatingBar ratingBar;
    private Button submit_button;
    private FirebaseController firebaseController;
    private DatabaseReference database;
    private ArrayList<Review> reviewList;
    private ReviewAdapter adapter;
    private RecyclerView recyclerView;
    private String username,reviewTag;
    private ReentrantLock lock;
    private double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lock = new ReentrantLock();

        receiveExtras();
        setDatabase();
        setComment();
        setAdapter();

        //
    }
    //1
    private void receiveExtras() {
        Intent i = getIntent();
        longitude = i.getDoubleExtra("long",0);
        latitude = i.getDoubleExtra("lat",0);
        reviewTag =  toReviewTag(longitude,latitude);
    }
    private String toReviewTag(double longitude,double latitude){
        int log = (int)(longitude*10000);
        int lat = (int)(latitude*10000);
        return String.valueOf(lat)+"+"+String.valueOf(log);
    }


    //2
    private void setDatabase(){
        firebaseController = new FirebaseController();
        //find user name to display
        database = firebaseController.getUserPathReference();

        lock.lock();
        try {
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    username = user.getName();

                    lock.unlock();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    lock.unlock();
                }
            });
        }catch (Exception e){e.printStackTrace();}



        update_commentView();
    }
    //3
    private void update_commentView(){
        //get review from remote side
        binding.reviewProgress.setVisibility(View.VISIBLE);
        database = firebaseController.getReviewReference(reviewTag);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Review review = dataSnapshot.getValue(Review.class);
                    reviewList.add(review);
                }
                adapter.notifyDataSetChanged();
                binding.reviewProgress.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //4
    private void setComment() {
        binding.submitBtn.setOnClickListener(view -> {
            //1 input get and check
            TextInputLayout review_layout = binding.userReview;
            String review_txt = review_layout.getEditText().getText().toString();
            if(review_txt.length()<2){
                //set error msg
                review_layout.setError("Comment is too short");
            }else{
                //2 Create review object and upload review to database

                Review review = new Review(username,binding.ratingBar.getRating(),review_txt);

                DatabaseReference review_table = firebaseController.getReviewReference(reviewTag).push();

                review_table.setValue(review).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ReviewActivity.this, "Comment posted", Toast.LENGTH_SHORT).show();
                        //delete comment
                        binding.userReview.getEditText().setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReviewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                         Comment Failed to post

                    }
                });
                //3 update UI
                update_commentView();


            }
        });
    }
    private void setAdapter(){
        recyclerView = binding.reviewView;
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));
//        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        reviewList = new ArrayList<Review>();
        adapter = new ReviewAdapter(reviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
//        recyclerView.setHasFixedSize(true);
    }


}