package com.npktech.hackelite.helpdesk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SplashActivity extends AppCompatActivity {

    Animation animation,animation1,animation2;
    LinearLayout linearLayout;
    private Handler handler;
    private Runnable callback;

    ImageView image;
    TextView wlcm,wb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        linearLayout=(LinearLayout)findViewById(R.id.text);
        image=(ImageView)findViewById(R.id.image);
        wlcm=(TextView)findViewById(R.id.wlcm);
        wb=(TextView)findViewById(R.id.wb);

        /**
         * Set animations to view.
         */

        animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_bottom);
        animation1 = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_bottom1);
        animation2 = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_bottom2);

        image.setAnimation(animation);
        wlcm.setAnimation(animation1);
        wb.setAnimation(animation2);

        /**
         * handler for delayed screen 8 secs
         * splash activity to Introduction page
         */
        handler = new Handler();
        callback = new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(getApplicationContext(),Introduction_Page.class);
                startActivity(i);
                finish();

            }
        };
        handler.postDelayed(callback, 8000);

    }



}
