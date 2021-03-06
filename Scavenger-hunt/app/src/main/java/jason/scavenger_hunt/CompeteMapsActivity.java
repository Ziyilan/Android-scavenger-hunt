package jason.scavenger_hunt;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
        private Button backButton;
        private ArrayList<String> visited;
        CourseDbHelper dbHelper;
        ArrayList<Latitude> lats;
        ArrayList<Longitude> lngs;
        Chronometer chronometer;
        Course course;
        Long id;
        Location mLastLocation;
        Marker mCurrLocationMarker;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_compete_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            Intent i = getIntent();
            id = i.getLongExtra("key", 1);
            //load course checkpoints
            dbHelper = new CourseDbHelper(getApplicationContext());
            course = dbHelper.getCourse(id);
            lats = course.getLatitude();
            lngs = course.getLongitude();
            //start the timer for competition
            chronometer = (Chronometer) findViewById(R.id.mChronometer);
            final long timeWhenStopped = 0;
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chronometer.start();

            // lots of helpful code from Shvet and Sishin on StackOverflow
            // http://stackoverflow.com/questions/27504606/how-to-implement-draggable-map-like-uber-android-update-with-change-location
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            mGoogleApiClient.connect();
            //back button to end race
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
            //create a list for storing visited checkpoints
            visited = new ArrayList<>();
            for (int i = 0; i < lats.size(); i++) {
                LatLng coords = new LatLng(lats.get(i).getLatitude(), lngs.get(i).getLongitude());
                mMap.addMarker(new MarkerOptions().position(coords).title("Marker at Olin"));
            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lats.get(0).getLatitude(), lngs.get(0).getLongitude())).zoom(19f).tilt(0).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }

            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = mMap.addMarker(markerOptions);

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            //stop location updates
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
            // iterate through all the checkpoints in the course and if the distance to a point is less
            // than 10m, add it to visited list. when visited list is full, prompt to course complete page
            for (int i = 0; i < lats.size(); i++) {
                double dist = DistanceCalculator.distance(location.getLatitude(), location.getLongitude(),
                        lats.get(i).getLatitude(), lngs.get(i).getLongitude());
                if (dist < .01) {
                    LatLng pnt = new LatLng(lats.get(i).getLatitude(), lngs.get(i).getLongitude());
                    final Marker marker = mMap.addMarker(new MarkerOptions().position(pnt).title("Marker 2 at Olin")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    if (!visited.contains(Integer.toString(i))){
                        visited.add(Integer.toString(i));
                    }
                    if (visited.size() == lats.size()) {
                        Long elapsedTime = (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
                        Toast toast = Toast.makeText(getApplicationContext(), Long.toString(elapsedTime), Toast.LENGTH_SHORT);
                        toast.show();
                        course.setYourTime(elapsedTime.intValue());
                        dbHelper.updateArray(id, course);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("key", "2");
                        startActivity(intent);
                    }
                }
            }
        }

        @Override
        public void onConnected(Bundle arg0) {
            // required method
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }

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

        protected void showInputDialogWinner(final int yourTime) {
            LayoutInflater layoutInflater = LayoutInflater.from(CompeteMapsActivity.this);
            View promptView = layoutInflater.inflate(R.layout.compete_alert_layout, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(CompeteMapsActivity.this);
            builder.setView(promptView);


            final TextView showTime = (TextView) promptView.findViewById(R.id.finishTime);
            showTime.setText(String.valueOf(yourTime));
            final TextView finishStatement = (TextView) promptView.findViewById(R.id.finishTestView);
            finishStatement.setText("New Best Time");

            builder.setCancelable(false)
                    .setPositiveButton("Save Your Time", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("key", "2");
                            startActivity(intent);
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();


        }

        protected void showInputDialogLoser(final int yourTime) {
            LayoutInflater layoutInflater = LayoutInflater.from(CompeteMapsActivity.this);
            View promptView = layoutInflater.inflate(R.layout.compete_alert_layout, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(CompeteMapsActivity.this);
            builder.setView(promptView);


            final TextView showTime = (TextView) promptView.findViewById(R.id.finishTime);
            showTime.setText(String.valueOf(yourTime));
            final TextView finishStatement = (TextView) promptView.findViewById(R.id.finishTestView);
            finishStatement.setText("You were too slow");

            builder.setCancelable(false)
                    .setPositiveButton("Have Another Go", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("key", "2");
                            startActivity(intent);
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();


        }


    }
