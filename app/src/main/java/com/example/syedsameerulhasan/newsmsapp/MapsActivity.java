package com.example.syedsameerulhasan.newsmsapp;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<LatLng> ppoints = new ArrayList<LatLng>();
        LatLng karachi2;
        mMap = googleMap;
        mMap.clear();
        DBHandlerPoints finalpoints = new DBHandlerPoints(this);
        ArrayList<Points> MapPoints =  finalpoints.getPointS(getIntent().getExtras().getString("PhoneNumber"));

        mMap.setMyLocationEnabled(true);

        ArrayList<Double> LATITUDE= new ArrayList<Double>();
        ArrayList<Double> LONGITUDE= new ArrayList<Double>();
        ArrayList<LatLng> alphapoints=new ArrayList<LatLng>();

        int IndexCount = 0;
        for(Points plots: MapPoints)
        {
            LATITUDE.add(IndexCount, Double.parseDouble(plots.get_latitude()));
            LONGITUDE.add(IndexCount, Double.parseDouble(plots.get_longitude()));
            IndexCount++;
            alphapoints.add(new LatLng(Double.parseDouble(plots.get_latitude()),Double.parseDouble(plots.get_longitude())));
        }

        MarkerOptions options = new MarkerOptions();

        IconGenerator iconFactory = new IconGenerator(this);


        for ( int i=0;i<alphapoints.size();i++)
        {
            karachi2 = new LatLng(LONGITUDE.get(i), LATITUDE.get(i));
            if(i == 0)
            {
                /* For Star Marker*/
                iconFactory.setRotation(0);
                iconFactory.setContentRotation(90);
                iconFactory.setStyle(IconGenerator.STYLE_GREEN);
                addIcon(iconFactory, "Start",new LatLng(LONGITUDE.get(i), LATITUDE.get(i)));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(karachi2));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karachi2, 16));
            }
            else
            {
                if(i == alphapoints.size()-1)
                {

                }
                else {

                    mMap.addMarker(new MarkerOptions().position(karachi2).title("Point " + i));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(karachi2));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karachi2, 16));
                }
            }
            ppoints.add(karachi2);

        }
        karachi2 = ppoints.get(ppoints.size() - 1);
        if(ppoints.size() > 0)
        {
        /* For Current Point*/
            iconFactory.setRotation(0);
            iconFactory.setContentRotation(90);
            iconFactory.setStyle(IconGenerator.STYLE_BLUE);
            addIcon(iconFactory, "Last Location",new LatLng(LONGITUDE.get(LONGITUDE.size()-1), LATITUDE.get(LATITUDE.size()-1)));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(karachi2));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karachi2, 16));
        }

        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .addAll(ppoints).color(Color.BLUE)
        );
    }

    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        getMap().addMarker(markerOptions);
    }

    public GoogleMap getMap() {
        return mMap;
    }
}
