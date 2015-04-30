package com.trojanow.sensor;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by pabloivan57 on 4/29/15.
 */
public class Geolocation {

    private Activity context;
    private Location location;
    private LocationManager locationManager;

    final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location loc) {
            location = loc;
        }

        @Override
        public void onStatusChanged (String provider, int status, Bundle extras) {

        }

        public void onProviderEnabled (String provider) {

        }

        public void onProviderDisabled (String provider) {

        }
    };

    public Geolocation(Activity context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String locationProvider = locationManager.getBestProvider(criteria, true);
        this.location = new Location(locationProvider);
        locationManager.requestLocationUpdates(locationProvider, 1000,
                10, mLocationListener);
    }

    public Location getLocation() {
        return location;
    }

}
