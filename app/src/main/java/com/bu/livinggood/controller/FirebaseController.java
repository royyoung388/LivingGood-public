package com.bu.livinggood.controller;

import com.bu.livinggood.bean.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseController {
    public static int LOGIN_SUCCESS = 1;
    public static int LOGIN_FAILED = 0;
    public static int PASSWORD_EMPTY = 2;
    private String tag = "Firebase Model";
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private String userName;
    private User user;

    private int login_state;
    public FirebaseController(){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }
    /**
     * function list:
     * boolean Checklogin
     * boolean Signout
     * String getUID
    **/
    public boolean checkLogin(){
        //true if login
        return !(firebaseAuth.getCurrentUser()==null);
    }

    public void signout(){
        if(!checkLogin()) {
            firebaseAuth.signOut();
        }
    }
    public FirebaseAuth getAuth(){return firebaseAuth;}
    public String getUID(){
        //get user id
        return firebaseAuth.getCurrentUser().getUid();
    }

    public DatabaseReference getReviewReference(String tag){
        return database.getReference("Reviews")
                .child(tag);
    }
    public DatabaseReference getUserPathReference(){
        return database.getReference("Users")
                .child(getUID());
    }


    /**
     * ctrl + shift + / to uncomment
     * Login Function Example
     *
        public String login(String email_str,String password_str){

         // 1 input check

             if(email_str.isEmpty() ){
             return EMAIL_EMPTY;
             }
             if(password_str.isEmpty() ){
             return PASSWORD_EMPTY;
             }

         // 2 firebase signin

            firebaseAuth.signInWithEmailAndPassword(email_str,password_str)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this,"signInWithCredential:success",Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }


                    });
        }
    **/

    /**
     * Signup Function Example
     * Step 1 Get a valid email address, non empty name
     *
     * Step 2 Create a User Object to Store to firebase
        * User user = new User(name_str,email_str,null);
     *
     * Step 3 Create Account in firebase authentication
     *
     * firebaseAuth.createUserWithEmailAndPassword(email,password)
     *                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
     *                     @Override
     *                     public void onComplete(@NonNull Task<AuthResult> task) {
     *
     *                //if add to authentication is success
     *                         if(task.isSuccessful()){
     *
     *                        // Step 4 add user object to firebase realtime database
     *
     *                             database.getInstance().getReference("Users")
     *                                     //use firebase user's uid as the key for each user
     *                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
     *                                     //put object
     *                                     .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
     *                                 @Override
     *                                 public void onComplete(@NonNull Task<Void> task) {
     *
     *                           //if add to user is successful
     *                                     if(task.isSuccessful()){
     *                                         Toast.makeText(RegisterActivity.this, "User has been register", Toast.LENGTH_LONG).show();
     *                                         Intent i = new Intent(RegisterActivity.this, WelcomeActivity.class);
     *                                         //i.putExtra("user",user);
     *                                         startActivity(i);
     *                            //if add to user is failed
     *                                     }else{
     *                                         Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
     *                                     }
     *                                 }
     *                             });
     *
     *                    //if add to authentication is failed
     *                         }else{
     *                             Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
     *                         }
     *                     }
     *                 });

     **/

}
