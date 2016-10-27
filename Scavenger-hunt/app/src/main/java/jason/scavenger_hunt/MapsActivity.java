package jason.scavenger_hunt;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = "MapsActivity";
//     private Button addPointButton;
//     private CameraPosition mCameraPosition;
     private LocationManager locationManager;
     private android.location.LocationListener locationListener;
     private double currentLatitude;
     private double currentLongitude;
    private Button addPointButton;
    private Button savePointsButton;
    private CameraPosition mCameraPosition;
    CourseDbHelper dbHelper;
    Course course;
    CompeteCourseAdapter courseAdapter;
    ArrayList<Latitude> arrayLatitude;
    ArrayList<Longitude> arrayLongitude;
    Location mLastLocation;
    Marker mCurrLocationMarker;


    public void setCourseItem(Course course){
         this.course = course;
     }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        arrayLatitude = new ArrayList<>();
        arrayLongitude = new ArrayList<>();
        dbHelper = new CourseDbHelper(getApplicationContext());
        course = new Course("name", 0,0,arrayLatitude,arrayLongitude);
        ArrayList<Course> arrayOfCourses = new ArrayList<>();
        courseAdapter = new CompeteCourseAdapter(getApplicationContext(),arrayOfCourses,dbHelper);


        // lots of helpful code from Shvet and Sishin on StackOverflow
        // http://stackoverflow.com/questions/27504606/how-to-implement-draggable-map-like-uber-android-update-with-change-location

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();

            mGoogleApiClient.connect();

        addPointButton = (Button) findViewById(R.id.manageAddPointButton);
        addPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Latitude newLatitude = new Latitude(mCameraPosition.target.latitude);
                Longitude newLongitude = new Longitude(mCameraPosition.target.longitude);
                course.addLatitude(newLatitude);
                course.addLongitude(newLongitude);
                courseAdapter.notifyDataSetChanged();
                Toast toast = Toast.makeText(getApplicationContext(), Double.toString(mCameraPosition.target.latitude) + ", " + Double.toString(mCameraPosition.target.longitude), Toast.LENGTH_SHORT );
                toast.show();

            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        locationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 10);
                return;
            }
        }
        else {
            locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
        }
        savePointsButton = (Button) findViewById(R.id.manageSavePointsButton);
        savePointsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showInputDialog();

            }
        });
    }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         switch (requestCode){
             case 10:
                 if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                     locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
                 return;
         }
     }


     @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                 == PackageManager.PERMISSION_GRANTED) {
             mMap.setMyLocationEnabled(true);
         } else {
             // Show rationale and request permission.
             Log.d(TAG, "fuck you");
         }
//         currentLatitude = mMap.getMyLocation().getLatitude();
//         currentLongitude = mMap.getMyLocation().getLongitude();

        // Add a marker at Olin and move the camera
//        LatLng olin = new LatLng(42.2932, -71.2637);

         LatLng olin = new LatLng(currentLongitude, currentLatitude);
         mMap.addMarker(new MarkerOptions().position(olin).title("Marker at Olin"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(olin).zoom(19f).tilt(0).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

         googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
             @Override
             public void onCameraChange(CameraPosition cameraPosition) {
                 mCameraPosition = cameraPosition;

                 Log.i("centerLat",Double.toString(cameraPosition.target.latitude));

                 Log.i("centerLong",Double.toString(cameraPosition.target.longitude));
             }
         });
    }

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
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
            currentLatitude = mLastLocation.getLatitude();
            currentLongitude = mLastLocation.getLongitude();
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

    protected void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(MapsActivity.this);
         View promptView = layoutInflater.inflate(R.layout.alert_layout, null);
         AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
         builder.setView(promptView);


         final EditText editText = (EditText) promptView.findViewById(R.id.alertEditText);
         builder.setCancelable(false)
                 .setPositiveButton("Set Your Course", new DialogInterface.OnClickListener(){
                     public void onClick(DialogInterface dialog, int id){
                         course.setName(editText.getText().toString());

                         int k = course.getLatitude().size();
                         course.setNumOfPoints(k);

                         CourseDbHelper dbHelper1 = new CourseDbHelper(getApplicationContext());
                         dbHelper1.addToCourseList(course);
                         courseAdapter.notifyDataSetChanged();


                         Toast toast = Toast.makeText(getApplicationContext(), editText.getText(), Toast.LENGTH_SHORT);
                         toast.show();
                         changeActivity();
                     }
                 })
                 .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                     public void onClick(DialogInterface dialog, int id){
                         dialog.cancel();
                     }
                 });
         AlertDialog alert = builder.create();
         alert.show();


    }

    public void changeActivity() {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
