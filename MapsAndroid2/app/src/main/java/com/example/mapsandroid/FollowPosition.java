package com.example.mapsandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.udb.mapsandroid.R;

public class FollowPosition implements LocationListener {
    private LocationManager locationManager;
    Context context;
    private static int DISTANCE = 1;
    private static int TIME = 3000; //3sec
    private String provider;
    Marker point;
    private GoogleMap mMap;

    public FollowPosition(GoogleMap mMap, Context context) {
        this.setmMap(mMap);
        this.context = context;
/********** Asigna Gps location service LocationManager object
 ***********/
        locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else {
            provider = LocationManager.NETWORK_PROVIDER;
        }
    }

    public void register(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.locationManager.requestLocationUpdates(provider,
                TIME, // 30 segundos
                10, this); // 10 metros
    }

    public void unRegister(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(),
                location.getLongitude());
        MarkerOptions currentPosition = new MarkerOptions();
        currentPosition.position(latLng);
        currentPosition.title("Aquí estoy");
        currentPosition.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pointer)
        );
//Quitamos el viejo punto

        if (point != null) point.remove();
//Agregamos el nuevo punto
        point = getmMap().addMarker(currentPosition);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }
}