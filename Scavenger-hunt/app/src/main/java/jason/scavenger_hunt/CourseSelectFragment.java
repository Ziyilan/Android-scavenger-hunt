package jason.scavenger_hunt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class CourseSelectFragment extends Fragment {

    CourseDbHelper dbHelper;

    public CourseSelectFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_compete_course_select, container, false);
        dbHelper = new CourseDbHelper((getContext()));

        final ArrayList<Course> listOfCourses = dbHelper.getAll();
        final CompeteCourseAdapter adapter = new CompeteCourseAdapter(getContext(), listOfCourses, dbHelper);
        final ListView listView = (ListView) view.findViewById(R.id.courseListView);
        listView.setAdapter(adapter);

        Button tempButt = (Button) view.findViewById(R.id.tempButton);

        tempButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity) getActivity()).changeToCompeteActivity();
                //Todo: you will delete this later
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                Log.d("CourseSelectFragment", "I've been hit!");
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Ready to Run?");

                alertDialog.setButton(-1, "Let's Start", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                        Intent i = new Intent(getContext(), CompeteMapsActivity.class);
                        i.putExtra("key", id);
                        startActivity(i);
                    }
                });

                alertDialog.setButton(-2, "Please No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });

                alertDialog.show();


            }
        });




        return view;

    }

}
