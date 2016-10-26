package jason.scavenger_hunt;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
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

import java.util.ArrayList;

/**
 * The activity for the competing map.
 */
public class CompeteMapsActivity
    extends FragmentActivity
    implements OnMapReadyCallback,
    LocationListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener
    {

        private LocationRequest mLocationRequest;
        private GoogleMap mMap;
        private GoogleApiClient mGoogleApiClient;
        private String TAG = "CompeteMapsActivity";
        private Button doneButton;
        private Button backButton;
        private CameraPosition mCameraPosition;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_compete_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            Chronometer chronometer = (Chronometer) findViewById(R.id.mChronometer);
            long timeWhenStopped = 0;

            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chronometer.start();

            // lots of helpful code from Shvet and Sishin on StackOverflow
            // http://stackoverflow.com/questions/27504606/how-to-implement-draggable-map-like-uber-android-update-with-change-location

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();

            mGoogleApiClient.connect();

            doneButton = (Button) findViewById(R.id.mRunDone);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("key", "1");
                    startActivity(i);

                }
            });

            backButton = (Button) findViewById(R.id.mRunCancel);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("key", "2");
                    startActivity(i);
                }
            });

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            final ArrayList<Double> lats = new ArrayList<>();
            final ArrayList<Double> lngs = new ArrayList<>();
            lats.add(42.2932);
            lats.add(42.2937);
            lngs.add(-71.2637);
            lngs.add(-71.2637);

            for (int i = 0; i < lats.size(); i++) {
                LatLng marker = new LatLng(lats.get(i), lngs.get(i));
                mMap.addMarker(new MarkerOptions().position(marker).title("Marker at Olin"));
            }

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(42.2932, -71.2637)).zoom(19f).tilt(0).build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    mCameraPosition = cameraPosition;

                    for (int i = 0; i < lats.size(); i++) {
                        double dist = DistanceCalculator.distance(mCameraPosition.target.latitude, mCameraPosition.target.longitude, lats.get(i), lngs.get(i));
//

                        if (dist < .01) {
                            //do something
                            Toast toast2 = Toast.makeText(getApplicationContext(), "You made it to point #" + Integer.toString(i), Toast.LENGTH_SHORT);
                            toast2.show();
                        }
                    }

                }
            });
        }

        @Override
        public void onLocationChanged(Location location) {

        }

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
