package com.sfiscusdevelopment.sfiscus.booklit.splashScreen;


/*
 * Copyright 2014 Flavio Faria
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sfiscusdevelopment.sfiscus.booklit.LoginActivity;
import com.sfiscusdevelopment.sfiscus.booklit.R;

/**
 * Custom splash screen that's displayed only during the initial runtime thread.
 */
public class SplashScreenActivity extends Activity {

    // Create a static in with an id of SPLASH_TIME_OUT with a value of 4000
    private static int SPLASH_TIME_OUT = 4000;

    // Create a KenBurnsView with an id of mKenBurns
    private KenBurnsView mKenBurns;

    /**
     * Create a method with an id of onCreate that requires a Bundle argument. This is the main
     * processing method for the class
     * @param savedInstanceState argument variable of Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setAnimation();

        TextView text = (TextView) findViewById(R.id.welcome_text);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        text.setTypeface(typeface);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mKenBurns.setImageResource(R.drawable.background4);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);

        RelativeLayout splash = (RelativeLayout) findViewById(R.id.splash_layout);
        splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    // Create a method with an id of setAnimation. This method determines animation and duration
    private void setAnimation() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleX", 1.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(500);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleY", 1.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(500);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "alpha", 1.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(500);
        ObjectAnimator photoAnimation = ObjectAnimator.ofFloat(findViewById(R.id.imagelogo), "alpha", 1.0F, 1.0F);
        photoAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        photoAnimation.setDuration(500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation).with(photoAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();

    }
}
