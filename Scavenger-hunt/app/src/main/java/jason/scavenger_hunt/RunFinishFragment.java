package jason.scavenger_hunt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RunFinishFragment extends Fragment {


    public RunFinishFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_compete_run_finish, container, false);

        final EditText yourName = (EditText) view.findViewById(R.id.runName);

        final TextView yourTime = (TextView) view.findViewById(R.id.runTime);
        Button submitButton = (Button) view.findViewById(R.id.runSubmit);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity) getActivity()).changeFragment(new CourseSelectFragment());
                //Todo: You don't want this to remain a new instance
            }
        });





        return view;
    }
}

