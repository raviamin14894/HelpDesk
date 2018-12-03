package com.npktech.hackelite.helpdesk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;


public class Introduction_Page extends AppCompatActivity {

    FragmentManager fragmentManager=getSupportFragmentManager();
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intropage);

        button=(Button)findViewById(R.id.skip);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomePageHelpDesk.class);
                startActivity(i);
                finish();
            }
        });

        /**
         * PaperOnboardingPage is a third party or custom library used to set
         * introduction of page
         */

        PaperOnboardingPage scr2 = new PaperOnboardingPage("HelpDesk",
                "Welcome to HelpDesk."+"\n"+"If you stuck somewhere, We are there.",
                Color.parseColor("#FF252424"), R.drawable.ser1, R.drawable.ser1);


        PaperOnboardingPage scr3 = new PaperOnboardingPage("Police",
                "You can get directions of police stations near by you..",
                Color.parseColor("#FF174D71"), R.drawable.pol5, R.drawable.pol5);

        PaperOnboardingPage scr4 = new PaperOnboardingPage("Hospitals",
                "You can get directions hospitals near by you...",
                Color.parseColor("#5f7a87"), R.drawable.hosp5, R.drawable.hosp5);

        PaperOnboardingPage scr5 = new PaperOnboardingPage("Fire-stations",
                "You can get directions fire stations near by you...",
                Color.parseColor("#131a41"), R.drawable.fire_station, R.drawable.fire_station);


        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr2);
        elements.add(scr3);
        elements.add(scr4);
        elements.add(scr5);

        PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.linear, onBoardingFragment);
        fragmentTransaction.commit();

        onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                Intent i = new Intent(getApplicationContext(),HomePageHelpDesk.class);
                startActivity(i);
                finish();
            }
        });


    }
}
