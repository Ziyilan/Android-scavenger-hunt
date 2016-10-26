package jason.scavenger_hunt;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class CourseSelectFragment extends Fragment {

    public CourseSelectFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_compete_course_select, container, false);

        final ArrayList<Course> listOfCourses = new ArrayList<Course>();
        final CompeteCourseAdapter adapter = new CompeteCourseAdapter(getContext(), listOfCourses);
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Ready to Run?");

                alertDialog.setButton(-1, "Let's Start", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        ((MainActivity) getActivity()).changeFragment(new RunFragment());
                        //Todo: this will need to be changed to a non-new fragment
                    }
                });

                alertDialog.setButton(-2, "Please No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });


            }
        });




        return view;

    }

}
