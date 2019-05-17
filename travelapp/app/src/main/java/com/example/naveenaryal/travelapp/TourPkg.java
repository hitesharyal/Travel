package com.example.naveenaryal.travelapp;

import android.widget.ImageView;

/**
 * Created by Naveen Aryal on 1/7/2018.
 */

public class TourPkg {

    public  String place ;
    public String duration;
    public String price;
    public int imaget;

    public TourPkg(String mplace, String mduration, String mprice, int mimaget) {
        place=mplace;
        duration=mduration;
        price=mprice;
        imaget=mimaget;

    }
}
