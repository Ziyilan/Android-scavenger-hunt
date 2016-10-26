package jason.scavenger_hunt;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        private CameraPosition mCameraPosition;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_compete_maps);
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

            doneButton = (Button) findViewById(R.id.mRunDone);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getApplicationContext(), Double.toString(mCameraPosition.target.latitude) + ", " + Double.toString(mCameraPosition.target.longitude), Toast.LENGTH_SHORT );
                    toast.show();

                }
            });

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // Add a marker at Olin and move the camera
            LatLng olin1 = new LatLng(42.2932, -71.2637);
            LatLng olin2 = new LatLng(42.2933, -71.2637);

            mMap.addMarker(new MarkerOptions().position(olin1).title("Marker at Olin"));
            mMap.addMarker(new MarkerOptions().position(olin2).title("Marker at Olin"));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(olin1).zoom(19f).tilt(0).build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    mCameraPosition = cameraPosition;

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
