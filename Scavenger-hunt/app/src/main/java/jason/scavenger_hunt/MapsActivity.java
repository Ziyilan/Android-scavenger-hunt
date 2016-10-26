package jason.scavenger_hunt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private Button addPointButton;
    private Button savePointsButton;
    private CameraPosition mCameraPosition;
    CourseDbHelper dbHelper;
    Course course;
    CompeteCourseAdapter courseAdapter;
    ArrayList<Latitude> arrayLatitude;
    ArrayList<Longitude> arrayLongitude;


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

        savePointsButton = (Button) findViewById(R.id.manageSavePointsButton);
        savePointsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showInputDialog();

            }
        });

    }


     @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at Olin and move the camera
        LatLng olin = new LatLng(42.2932, -71.2637);
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

    protected void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(MapsActivity.this);
         View promptView = layoutInflater.inflate(R.layout.alert_layout, null);
         AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
         builder.setView(promptView);


         final EditText editText = (EditText) promptView.findViewById(R.id.alertEditText);
         builder.setCancelable(false)
                 .setPositiveButton("Just Say Yes...", new DialogInterface.OnClickListener(){
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
