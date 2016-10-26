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
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements jason.scavenger_hunt.CourseMenuFragment.OnFragmentInteractionListener{

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

    }




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

    public void goToManageCourseMenu(Uri uri){
        CourseMenuFragment courseMenuFragment = new CourseMenuFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, courseMenuFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

}
