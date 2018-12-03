package com.npktech.hackelite.helpdesk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;




public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    Marker marker;
    LatLng a;
    private Map<Marker, Integer> mMarkerMap;
    private Map<Marker, String> mMarkerMap1;
    String placeType = "", response = "";
    Button callbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_nearbyplace);
        callbutton = (Button) findViewById(R.id.callbtn);


        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = "911";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);



            }
        });

        Log.e("MAPPPPPPPPPP", "onCreate: " + getIntent().getStringExtra("lat") + "," + getIntent().getStringExtra("lng"));

        if (getIntent().getStringExtra("placetype").trim().equalsIgnoreCase("Police Stations")) {
            placeType = "police";
        }
        if (getIntent().getStringExtra("placetype").trim().equalsIgnoreCase("Hospitals")) {
            placeType = "hospital";
        }
        if (getIntent().getStringExtra("placetype").trim().equalsIgnoreCase("Fire_stations")) {
            placeType = "fire_station";
        }
        mMarkerMap = new HashMap<Marker, Integer>();
        mMarkerMap1 = new HashMap<>();
        intializeMap();


    }

    /**
     * Intialize Map method
     * used to intialize goodle map-v2
     */
    private void intializeMap() {

        if (googleMap == null) {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            // getPlacesMarkers();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     * Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //  map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }

        double lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        double lng = Double.parseDouble(getIntent().getStringExtra("lng"));
        a = new LatLng(lat, lng);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(a, 14));
        new callGooglePlaceApi().execute();
    }

    /**
     * callGooglePlaceApi -> AsyncTask used for
     * fetch data from Google nearbyserch API
     */
    class callGooglePlaceApi extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String location = getIntent().getStringExtra("lat") + "," + getIntent().getStringExtra("lng");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MapsActivity.this);
            progressDialog.setTitle("Finding " + getIntent().getStringExtra("placetype") + " near by you..");
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                Log.d("URL", "doInBackground: " + NetworkUtils.appendURL(NetworkUtils.BASEURL,
                        location, NetworkUtils.RADIUS, placeType, NetworkUtils.APIKEY));

                response = NetworkUtils.getResponseFromHttpUrl(new URL(NetworkUtils.appendURL(NetworkUtils.BASEURL,
                        location, NetworkUtils.RADIUS, placeType, NetworkUtils.APIKEY)));
            } catch (IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(MapsActivity.this, "Data Received Fail, " +
                        "Please try again.", Toast.LENGTH_SHORT).show();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.trim().isEmpty()) {

                Log.e("MApsact", "onPostExecute: "+s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() != 0) {
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject1 = results.getJSONObject(i);
                            JSONObject geometry = jsonObject1.getJSONObject("geometry");
                            JSONObject location = geometry.getJSONObject("location");
                            //Place location marker which is fetch by google API
                            createMarker(i, i + "", String.valueOf(location.getDouble("lat")),
                                    "" + String.valueOf(location.getDouble("lng")),
                                    jsonObject1.getString("name"));
                        }
                        //Place current location marker
                        createMarker(999999999, 999999999 + "",
                                getIntent().getStringExtra("lat"),
                                getIntent().getStringExtra("lng"),
                                "You are here");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MapsActivity.this, "Data Received Fail, " +
                                "Please try again after some time", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(MapsActivity.this, "Data Received Fail, " +
                            "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
            progressDialog.dismiss();
        }
    }


    /**
     *
     * create marker method used to put marker specific marker on
     * specific latitutde and longtitude
     * @param a
     * @param id1
     * @param lat
     * @param lng
     * @param title
     * @return
     */

    private Marker createMarker(final int a, final String id1, String lat, String lng, final String title) {

        mMarkerMap.put(marker, a);
        mMarkerMap1.put(marker, title);

        Log.e("Data", "" + lat + "-->>>>" + lng + "--->>" + title);

        if (a == 999999999) {

            marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(lat), Double.valueOf(lng))).title(title)
                    .anchor(0.5f, 0.5f));

        } else if (getIntent().getStringExtra("placetype").equalsIgnoreCase("Hospitals")) {

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.hosp);

            marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(lat), Double.valueOf(lng))).title(title).icon(icon)
                    .anchor(0.5f, 0.5f));


        } else if (getIntent().getStringExtra("placetype").equalsIgnoreCase("Fire_stations")) {

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.fire11515);
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(lat), Double.valueOf(lng))).title(title).icon(icon)
                    .anchor(0.5f, 0.5f));


        } else if (getIntent().getStringExtra("placetype").equalsIgnoreCase("Police Stations")) {

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.police55);
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(lat), Double.valueOf(lng))).title(title).icon(icon)
                    .anchor(0.5f, 0.5f));

        }

        marker.showInfoWindow();

        return marker;

    }

}