package jason.scavenger_hunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mafaldaborges on 10/17/16.
 */
public class CompeteCourseAdapter extends ArrayAdapter<Course> {

    public CompeteCourseAdapter(Context context, ArrayList<Course> courseItem, CourseDbHelper courseDbHelper){
        super(context, 0, courseItem);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final Course courseItem = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.compete_course_content, parent, false);
        }
        //populate listview with course data loaded from database
        final TextView courseName = (TextView) convertView.findViewById(R.id.comCourseName);
        courseName.setText("Name: " + courseItem.getName());
        final TextView numOfPoints = (TextView) convertView.findViewById(R.id.comNOP);
        numOfPoints.setText("Checkpoints: " + String.valueOf(courseItem.getNumOfPoints()));
        final TextView bestTime = (TextView) convertView.findViewById(R.id.comBestTime);
        bestTime.setText("Best time: " + String.valueOf(courseItem.getYourTime()));
        return convertView;
    }

}
