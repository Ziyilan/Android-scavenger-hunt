package jason.scavenger_hunt;

import android.content.Intent;
import android.net.Uri;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements jason.scavenger_hunt.CourseMenuFragment.OnFragmentInteractionListener {

    private Button button;
    private TextView text;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Chronometer chronometer;
    private long timeWhenStopped = 0;
    private boolean stopClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new MainActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragment, "MainActivityFragment");
        //transaction.add(R.id.fragment_container, new CourseSelectFragment());
        transaction.commit();


//        chronometer = (Chronometer) findViewById(R.id.chronometer);
//        button = (Button) findViewById(R.id.gpsDataButton);
//        text = (TextView) findViewById(R.id.textView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                text.append("\n" + location.getLatitude() + "  " + location.getLongitude());
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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.INTERNET,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                }, 10);
//                return;
//            }
//        }
//        else {
//            configueButton();
//        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case 10:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                    configueButton();
//                return;
//        }
//    }

//    private void configueButton(){
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
//
//            }
//        });
//    }
//
//    // the method for when we press the 'reset' button
//    public void resetButtonClick(View v) {
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        timeWhenStopped = 0;
//        TextView secondsText = (TextView) findViewById(R.id.hmsTekst);
//        secondsText.setText("0 seconds");
//    }
//
//    // the method for when we press the 'start' button
//    public void startButtonClick(View v) {
//        chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
//        chronometer.start();
//        stopClicked = false;
//
//    }
//
//    // the method for when we press the 'stop' button
//    public void stopButtonClick(View v){
//        if (!stopClicked)  {
//            TextView secondsText = (TextView) findViewById(R.id.hmsTekst);
//            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
//            int seconds = (int) timeWhenStopped / 1000;
//            secondsText.setText( Math.abs(seconds) + " seconds");
//            chronometer.stop();
//            stopClicked = true;
//        }
//    }

    public void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeActivity() {

        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(i);
    }

    public void goToManageCourseMenu(Uri uri) {
        CourseMenuFragment courseMenuFragment = new CourseMenuFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, courseMenuFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.action_manage) {
            Fragment courseMenuFragment = new CourseMenuFragment();

            fragmentTransaction.replace(R.id.fragment_container, courseMenuFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        if (id == R.id.action_compete) {
            Fragment courseSelectFragment = new CourseSelectFragment();

            fragmentTransaction.replace(R.id.fragment_container, courseSelectFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        return true;
    }


}
