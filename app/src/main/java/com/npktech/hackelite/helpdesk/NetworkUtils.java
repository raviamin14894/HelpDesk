package com.npktech.hackelite.helpdesk;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;



public class NetworkUtils {

    public static String BASEURL = "maps.googleapis.com";
    public static String RADIUS = "2000";
    //public static String APIKEY = "AIzaSyBHkGF5vacoeP_H3UjeEx-AMbU_ul4cKKE";
   // public static String APIKEY = "AIzaSyDSzTxeKb48yni3sJbxY71lwea9Q55ClCI";
    //public static String APIKEY = "AIzaSyBxIXJq-MbthAF9ZPRp9ZFAi65oTvLpv6A";
    //public static String APIKEY = "AIzaSyBdq_5VIHCjYVUg9UmD_CQQHfMEiyTCkWA";
    public static String APIKEY = "AIzaSyB_al57KiieFmcK6-lRigPDosJW6PnbkEY";



    /**
     * getResponseFromHttpUrl -> Get Response from google place nearby search API.
     * @param url
     * @return
     * @throws IOException
     */

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = httpsURLConnection.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");
            boolean hasInput = sc.hasNext();
            if (hasInput) {
                return sc.next();
            } else {
                return null;
            }
        } finally {
            httpsURLConnection.disconnect();
        }
    }

    /**
     * appendURL -> This method
     * to append url of google API
     *
     * @param mBaseUrl
     * @param location
     * @param radius -> set radius of places nearby current location
     * @param placetype -> e.g. policestations, firestations or hospitals
     * @param APIKEY
     * @return
     */

    public static String appendURL(String mBaseUrl,String location,String radius,String placetype,String APIKEY) {
        return new Uri.Builder().scheme("https")
                .authority(mBaseUrl)
                .appendPath("maps")
                .appendPath("api")
                .appendPath("place")
                .appendPath("nearbysearch")
                .appendPath("json")
                .appendQueryParameter("location", location)
                .appendQueryParameter("radius", radius)
                .appendQueryParameter("type", placetype)
                .appendQueryParameter("key", APIKEY)
                .build().toString().trim();
    }

}
