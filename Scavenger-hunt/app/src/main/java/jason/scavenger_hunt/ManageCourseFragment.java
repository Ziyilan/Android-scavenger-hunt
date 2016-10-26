package jason.scavenger_hunt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageCourseFragment extends Fragment {

    CourseDbHelper dbHelper;

    public ManageCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbHelper = new CourseDbHelper((getContext()));
        View view = inflater.inflate(R.layout.fragment_manage_course, container, false);
        final ArrayList<Course> arrayofCourses = dbHelper.getAll();
        final ListView courseListView = (ListView) view.findViewById(R.id.ManagelistView);
        final ManageCourseAdapter adapter = new ManageCourseAdapter(getContext(),arrayofCourses,dbHelper);
        courseListView.setAdapter(adapter);
        return view;
    }

}
