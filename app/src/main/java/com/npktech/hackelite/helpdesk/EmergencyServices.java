package com.npktech.hackelite.helpdesk;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class EmergencyServices extends AppCompatActivity {
    ImageButton imageViewP,imageViewA,imageViewF,imageViewW,imageViewM,imageViewAb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        imageViewP = (ImageButton) findViewById(R.id.imageButtonpolice);
        imageViewA = (ImageButton) findViewById(R.id.imageButtonfirestation);
        imageViewF = (ImageButton) findViewById(R.id.imageButtonfire);
        imageViewW = (ImageButton) findViewById(R.id.womenbtn);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        imageViewP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                if(ActivityCompat.checkSelfPermission(EmergencyServices.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;//change the number
                }
                startActivity(callIntent);
                Toast.makeText(getApplicationContext(), "call forwarding to 911-police", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                if(ActivityCompat.checkSelfPermission(EmergencyServices.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(callIntent);
                Toast.makeText(getApplicationContext(), "call forwarding to 911-ambulance", Toast.LENGTH_SHORT).show();

            }
        });

        imageViewF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                if(ActivityCompat.checkSelfPermission(EmergencyServices.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(callIntent);
                Toast.makeText(getApplicationContext(), "call forwarding to 911-fire brigade", Toast.LENGTH_SHORT).show();
            }
        });
        imageViewW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                if(ActivityCompat.checkSelfPermission(EmergencyServices.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;//change the number
                }
                startActivity(callIntent);
                Toast.makeText(getApplicationContext(), "call forwarding to 911-Women Safety", Toast.LENGTH_SHORT).show();
            }
        });


    }


}