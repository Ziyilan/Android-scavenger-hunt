package jason.scavenger_hunt;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
 {

    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // lots of helpful code from Shvet and Sishin on StackOverflow
        // http://stackoverflow.com/questions/27504606/how-to-implement-draggable-map-like-uber-android-update-with-change-location

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();

            mGoogleApiClient.connect();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng olin = new LatLng(42.2932, -71.2637);
        mMap.addMarker(new MarkerOptions().position(olin).title("Marker at Olin"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(olin).zoom(19f).tilt(70).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onLocationChanged(Location location) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//
//        // check if map is created successfully or not
//        if (mMap != null) {
//            mMap.getUiSettings().setZoomControlsEnabled(false);
//            LatLng latLong;
//
//
//            latLong = new LatLng(location.getLatitude(), location.getLongitude());
//
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(latLong).zoom(19f).tilt(70).build();
//
//            mMap.setMyLocationEnabled(true);
//            mMap.getUiSettings().setMyLocationButtonEnabled(true);
//            mMap.animateCamera(CameraUpdateFactory
//                    .newCameraPosition(cameraPosition));
//
////            mLocationMarkerText.setText("Lat : " + location.getLatitude() + "," + "Long : " + location.getLongitude());
//            startIntentService(location);
//
//
//        } else {
//            Toast.makeText(getApplicationContext(),
//                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
//                    .show();
//        }

    }

//     protected void startIntentService(Location mLocation) {
//         // Create an intent for passing to the intent service responsible for fetching the address.
//         Intent intent = new Intent(this, FetchAddressIntentService.class);
//
//         // Pass the result receiver as an extra to the service.
//         intent.putExtra(AppUtils.LocationConstants.RECEIVER, mResultReceiver);
//
//         // Pass the location data as an extra to the service.
//         intent.putExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA, mLocation);
//
//         // Start the service. If the service isn't already running, it is instantiated and started
//         // (creating a process for it if needed); if it is running then it remains running. The
//         // service kills itself automatically once all intents are processed.
//         startService(intent);
//     }

    @Override
    public void onConnected(Bundle arg0) {
        // required method

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            changeMap(mLastLocation);
            Log.d(TAG, "ON connected");

        } else
            try {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //stupMap();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // do some stuff

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // more stuff

    }

    private void changeMap(Location location) {

    }


}
