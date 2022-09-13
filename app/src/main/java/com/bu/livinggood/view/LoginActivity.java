package com.bu.livinggood.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bu.livinggood.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn, forget_btn, register_btn;
    private TextInputLayout email,password;
    private String email_str,password_str;
    private FirebaseAuth firebaseAuth;
    private ActivityLoginBinding binding;
    private DatabaseReference databaseReference;
    private static final String TAG = "MAIN_ACTIVITY";
//    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        login_btn = binding.register;
        //forget_btn = binding.forgetBtn;
        register_btn = binding.registerBtn;
        email = binding.emailIn;
        password = binding.passwordIn;



        firebaseAuth = FirebaseAuth.getInstance();
        //login function
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fields check
                email_str = email.getEditText().getText().toString();
                password_str = password.getEditText().getText().toString();
                if(email_str.isEmpty() ){
                    email.setError("Cannot be empty");
                    return;
                }
                if(password_str.isEmpty() ){
                    password.setError("Cannot be empty");
                    return;
                }
                //firebase interaction
                firebaseAuth.signInWithEmailAndPassword(email_str,password_str)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this,"signInWithCredential:success",Toast.LENGTH_SHORT).show();
                                    //FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                    updateUI();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                }
                            }


                        });

            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start register page
                startRegister();
            }
        });
    }
    private void startRegister(){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            //if user is login then go to next page
            updateUI();
//            currentUser
        }



    }
    //forward user to the next activity
    public void updateUI( ) {
        Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();

       //put user info in database with uid as key
        Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(i);

        //get user from database

//        String uid = firebaseUser.getUid();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                user = snapshot.getValue(User.class);
//                Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
//                i.putExtra("user",user);
//                startActivity(i);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}