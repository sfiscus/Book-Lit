package com.sfiscusdevelopment.sfiscus.booklit;

import android.app.Application;

import com.sfiscusdevelopment.sfiscus.booklit.repositories.FirebaseActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Create a class with an id of LoginCredentials. This class extends Application. This class contains
 * the neccessary data for user login.
 */
public class LoginCredentials extends Application {

    // Create a RetroFit instance variable with an id of mRetrofit
    private Retrofit mRetrofit = null;

    // This is the main processing method for the LoginCredentials class
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseActivity.getmInstance().setmContext(this);
        FirebaseActivity.getmInstance().setFirebaseAnalytics(FirebaseAnalytics.getInstance(this));
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5949676227120501~8601500878");
    }

    /**
     * Create a Retrofit method with an if of getRetrofitClient and require a String argument
     * @param baseUrl the base url for the website
     * @return a statement that returns the value of the mRetrofit instance variable
     */
    public Retrofit getRetrofitClient(String baseUrl) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    /**
     * Create a String method with an id of getGoodreadsBaseUrl. This method gets the Goodreads
     * URL
     * @return a statement that returns the GoodReads website address
     */
    public String getGoodreadsBaseUrl() {
        return "https://www.goodreads.com";
    }
}
