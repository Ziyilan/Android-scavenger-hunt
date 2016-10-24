package jason.scavenger_hunt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainMenuFragment extends Fragment {

    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button manageButton = (Button) view.findViewById(R.id.manageCoursesButton);
        Button competeButton = (Button) view.findViewById(R.id.competeButton);

        manageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity) getActivity()).changeFragment(new ManageCourseFragment());
                //Todo: Stop making these new fragments
            }
        });

        competeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity) getActivity()).changeFragment(new CourseSelectFragment());
                //Todo: another new fragment issue
            }
        });
        return view;
    }

}
