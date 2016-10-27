package jason.scavenger_hunt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CourseMenuFragment extends Fragment {

    public CourseMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_course_menu, container, false);
        Button newCourseButton = (Button) view.findViewById(R.id.createCourseButton);
        Button manageCourseButton = (Button) view.findViewById(R.id.manageCoursesButton);

        newCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeActivity();
            }
        });

        manageCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(new ManageCourseFragment());
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        void goToManageCourseMenu(Uri uri);
    }
}
