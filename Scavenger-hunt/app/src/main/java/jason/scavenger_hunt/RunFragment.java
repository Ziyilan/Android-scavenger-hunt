package jason.scavenger_hunt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RunFragment extends Fragment {

    public RunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_compete_run, container, false);

        Button doneButton = (Button) view.findViewById(R.id.runDone);
        Button cancelButton = (Button) view.findViewById(R.id.runCancel);

        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity) getActivity()).changeFragment(new RunFinishFragment());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity) getActivity()).changeFragment(new CourseSelectFragment());
                //Todo: In future I will not want a new fragment but pull from list
            }
        });



        return view;
    }


}
