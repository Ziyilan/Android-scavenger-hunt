package jason.scavenger_hunt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;


public class MainActivity extends AppCompatActivity implements jason.scavenger_hunt.CourseMenuFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new MainActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Intent i = getIntent();
        String var = i.getStringExtra("key");

        if (var != null) {
            if (var.equals("1")) {
                transaction.add(R.id.fragment_container, new RunFinishFragment(), "RunFinishFragment");
            }
            else if (var.equals("2")) {
                transaction.add(R.id.fragment_container, new CourseSelectFragment(), "CourseSelectFragment");
            }
        }
        else {
            transaction.add(R.id.fragment_container, fragment, "MainActivityFragment");
        }
        //transaction.add(R.id.fragment_container, new CourseSelectFragment());
        transaction.commit();


    }

    // method for switching between fragment
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
