package com.npktech.hackelite.helpdesk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Aboutus extends AppCompatActivity {

    ImageView ser;
    TextView text1,text2;

    Animation animation,animation1,animation2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);


        animation = AnimationUtils.loadAnimation(Aboutus.this, R.anim.slide_bottom);
        animation1 = AnimationUtils.loadAnimation(Aboutus.this, R.anim.slide_bottom1);
        animation2 = AnimationUtils.loadAnimation(Aboutus.this, R.anim.slide_bottom2);


        ser=(ImageView)findViewById(R.id.ser1);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);


        ser.startAnimation(animation);
        text1.startAnimation(animation1);
        text2.startAnimation(animation2);

    }
}
