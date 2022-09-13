package com.bu.livinggood.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bu.livinggood.bean.User;
import com.bu.livinggood.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ResultActivity extends AppCompatActivity {
    private ActivityResultBinding binding;
    private User user;
    private FirebaseAuth auth;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        logout = binding.button;

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click to logout
                auth.signOut();
            }
        });
        //not received user
        if(getIntent().getExtras() == null){

            binding.Title.setText("Nothing pass to current activity");
            return;
        }

        //receive and extract
        user =  (User)getIntent().getParcelableExtra("user");
        if (user!=null){
            binding.Title.setText(user.getEmail());
            binding.userName.setText(user.getName());
            binding.user.setText("user is here");
        }else{
            Toast.makeText(this, "user is not here", Toast.LENGTH_SHORT).show();
        }

    }
}