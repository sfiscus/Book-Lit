package com.sfiscusdevelopment.sfiscus.booklit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.sfiscusdevelopment.sfiscus.booklit.bookLibrary.BookLibraryActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

/**
 * Create a class with an id LoginActivity and extends AppCompatActivity. This class contains
 * methods that result in the user being able to login to the app.
 */
public class LoginActivity extends AppCompatActivity {

    // Create a static final int instance variable with an id of RC_SIGN_IN with a value of 25927
    public static final int RC_SIGN_IN = 25927;

    // Create a static final String instance variable with an id of TAG and a value of LoginActivity
    private static final String TAG = "LoginActivity";

    // Create a boolean instance variable with an id of errorShown and a value of false
    private boolean errorShown = false;

    // Create a View instance variable with an id of container
    private View container;

    private String mErrorOne;

    private String mErrorTwo;

    private String mErrorThree;

    /**
     * This is the main processing method for the LoginActivity class. If this wasn't a non common
     * dispatch activity I would change the theme with setTheme() for removing the splashScreen
     * and setting the correct one within this method
     * @param savedInstanceState argument variable of Bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        container = findViewById(R.id.login_container);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth != null && auth.getCurrentUser() != null) {

        // User signed in
        Intent startMainActivityIntent = new Intent(this, BookLibraryActivity.class);
        startMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(startMainActivityIntent);

        } else {

        // User not signed in
        goToLoginActivity();

        }
    }

    /**
     * Create a method with an id of onActivityResult which requires two ints and an Intent
     * argument. This method is called when the activity that is launched is exited giving
     * the requestCode that the application started with, the resultCode it returned, and any
     * additional data from it.
     * @param requestCode the integer request code originally supplied to startActivityForResult
     * @param resultCode the integer result code returned by the child activity
     * @param data an Intent that returns the result data to the caller
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mErrorOne = getResources().getString(R.string.sign_in_error_one);
        mErrorTwo = getResources().getString(R.string.sign_in_error_two);
        mErrorThree = getResources().getString(R.string.sign_in_error_three);

        if (requestCode == RC_SIGN_IN) {

        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == ResultCodes.OK) {
            Intent startMainActivityIntent = new Intent(this, BookLibraryActivity.class);
            startActivity(startMainActivityIntent);
            finish();
            return;
        } else {
            // Sign in failed
            if (response == null) {
            // User pressed back button
            showErrorMessage(mErrorOne);
            }

            assert response != null;
            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
            showErrorMessage(mErrorTwo);
            }

            if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
            showErrorMessage(mErrorThree);
            }
        }

        if (!errorShown) {
            showErrorMessage(mErrorThree);
        }

        new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            finish();
            }
        }, Snackbar.LENGTH_SHORT);

        }
    }

    /**
     * Create a method with an id of showErrorMessage which requires a String argument.
     * This method will be called by the method onActivityResult and display the appropriate error
     * message to the user.
     * @param message a string message that will appear to the user if there is a sign in error
     */
    private void showErrorMessage(String message) {
        errorShown = true;
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
    }

    // Create a method with an id goToLoginActivity with the purpose of allowing the user to the login
    private void goToLoginActivity() {
        errorShown = false;

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.LoginTheme)
                .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                .setLogo(R.drawable.book_logo)
                .build(),
            RC_SIGN_IN);
    }
}

