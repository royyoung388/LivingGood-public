package com.bu.livinggood.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bu.livinggood.bean.User;
import com.bu.livinggood.controller.FirebaseController;
import com.bu.livinggood.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity   {
    private Button register_btn;
    private TextInputLayout email,password,name;
    private AutoCompleteTextView autoCompleteTextView;
    private String email_str,password_str,preference_str,name_str;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private  static ProgressBar progressBar;
    private ActivityRegisterBinding binding;
    private User user;
    private FirebaseController model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //view binding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //initialization
        name = binding.name;

        register_btn = binding.register;
        email = binding.emailIn;
        password = binding.passwordIn;
        progressBar = binding.progressBar;
        model = new FirebaseController();
        firebaseAuth = FirebaseAuth.getInstance();
        //click button to register user
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }
//function for input check
    private void registerUser(){
        name_str = name.getEditText().getText().toString().trim();
        email_str = email.getEditText().getText().toString().trim();
        password_str= password.getEditText().getText().toString().trim();
        //preference_str= preference.getEditText().getText().toString().trim();
        //input checking
        if(email_str.isEmpty()){
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if (password_str.isEmpty()){
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if (name_str.isEmpty()){
            name.setError("Name required");
            name.requestFocus();
            return;
        }
        if(password_str.length()<=8){
            password.setError("Password too short");
            password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_str).matches()){
            email.setError("Please Provide a Valid Email");
            email.requestFocus();
            return;

        }
        //create User object to store in firebase
        User user = new User(name_str,email_str,null);

        CreateAuth(email_str,password_str,user);

    }
    private void CreateAuth(String email,String password,User user){
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //create table for Users
                            database.getInstance().getReference("Users")
                                    //use firebase user's uid as the key for each user
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    //put object
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been register", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisterActivity.this, WelcomeActivity.class);
                                        //i.putExtra("user",user);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
    }

//    public static void setProgressBarView(Boolean status){
//        if(status){
//            progressBar.setVisibility(View.VISIBLE);
//        }else {
//            progressBar.setVisibility(View.GONE);
//        }
//    }
    public  void toWelcomePage(Boolean status){
        if(status){
            Toast.makeText(RegisterActivity.this, "User has been register", Toast.LENGTH_LONG).show();
            Intent i = new Intent(RegisterActivity.this, WelcomeActivity.class);
            //i.putExtra("user",user);
            startActivity(i);
        }else {
            progressBar.setVisibility(View.GONE);
        }

    }
    //For user preference dropdown menu setup
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //setup display values in preference
//        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.preference, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        autoCompleteTextView.setAdapter(adapter);
//    }

}