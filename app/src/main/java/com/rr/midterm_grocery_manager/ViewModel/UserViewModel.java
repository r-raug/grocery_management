package com.rr.midterm_grocery_manager.ViewModel;


import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rr.midterm_grocery_manager.MainActivity;


public class UserViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loginFailed = new MutableLiveData<>();


    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.setValue(firebaseAuth.getCurrentUser());
                        loginFailed.setValue(false);
                    }
                    else{
                        user.setValue(null);
                        loginFailed.setValue(true);
                    }
                });
    }

    public void registerUser(String email, String password, MainActivity activity){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Toast.makeText(activity, "Sign Up Successful: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<Boolean> getLoginFailed(){
        return loginFailed;
    }
}