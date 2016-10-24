package jason.scavenger_hunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mafaldaborges on 10/17/16.
 */
public class CompeteCourseAdapter extends ArrayAdapter<Course> {

    private ArrayList<Course> courseList;
    private Context context;

    public CompeteCourseAdapter(Context context, ArrayList<Course> courseItem){
        super(context, 0, courseItem);
        this.courseList = courseItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final Course courseItem = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.compete_course_content, parent, false);

        }

        final TextView courseName = (TextView) convertView.findViewById(R.id.comCourseName);
        final TextView distance = (TextView) convertView.findViewById(R.id.comDistance);
        final TextView bestTime = (TextView) convertView.findViewById(R.id.comBestTime);


        return convertView;
    }

}
