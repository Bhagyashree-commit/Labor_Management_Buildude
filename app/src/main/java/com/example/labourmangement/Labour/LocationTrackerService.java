package com.example.labourmangement.Labour;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LocationTrackerService extends Service {
    private FusedLocationProviderClient mLocationProviderClient;
    private LocationCallback locationUpdatesCallback;
    private LocationRequest locationRequest;
    public LocationTrackerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setUpLocationRequest();
    }


    private void setUpLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(50000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String keyValue = intent.getStringExtra("key");
        if(keyValue!=null && keyValue.equals("stop")){
            stopSelf();
        }else {
            setUpLocationUpdatesCallback();
            mLocationProviderClient.requestLocationUpdates(locationRequest, locationUpdatesCallback, null);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationNotification.cancel(this);
        mLocationProviderClient.removeLocationUpdates(locationUpdatesCallback);
    }

    private void setUpLocationUpdatesCallback() {
        locationUpdatesCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult!=null){

                    Location lastLocation = locationResult.getLastLocation();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference().child("location").child("device1");


                    Map<String, Object> data = new HashMap<>();
                    data.put("latitude", lastLocation.getLatitude());
                    data.put("longitude", lastLocation.getLongitude());
                    data.put("time", lastLocation.getTime());
                    ref.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //
                            Log.i("tag", "Location update saved");
                           /* Toast.makeText(getApplicationContext(),
                                    "Your Location Is Lat:" + lastLocation.getLatitude() + " - Lng:" + lastLocation.getLongitude(), Toast.LENGTH_LONG)
                                    .show();
*/

                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder(LocationTrackerService.this, Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();
                                Toast.makeText(getApplicationContext(),
                                        "Your Location :" +address , Toast.LENGTH_LONG)
                                        .show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                        }
                    });
                    LocationNotification.notify(LocationTrackerService.this, "BuilDude Is Tracking your Location",
                            "Lat:" + lastLocation.getLatitude() + " - Lng:" + lastLocation.getLongitude());
                }else{
                    Log.i("tag", "Location null");
                }
            }
        };
    }
}
