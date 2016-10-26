package jason.scavenger_hunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by mafaldaborges on 10/25/16.
 */
public class ManageCourseAdapter extends ArrayAdapter<Course> {


    private ArrayList<Course> courseList;
    private Context context;
    private CourseDbHelper dbHelper;

    public ManageCourseAdapter(Context context, ArrayList<Course> courseItem, CourseDbHelper courseDbHelper) {
        super(context, 0, courseItem);
        this.courseList = courseItem;
        this.context = context;
        this.dbHelper = courseDbHelper;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Course courseItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.manage_course_content, parent, false);

        }

        final TextView courseName = (TextView) convertView.findViewById(R.id.manCourseName);
        courseName.setText(courseItem.getName());
        final TextView numOfPoints = (TextView) convertView.findViewById(R.id.manNOP);
        numOfPoints.setText(String.valueOf(courseItem.getNumOfPoints()));
        final TextView bestTime = (TextView) convertView.findViewById(R.id.manBestTime);
        bestTime.setText(String.valueOf(courseItem.getYourTime()));

        ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.manDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseList.remove(position);
                dbHelper.deleteRow(courseItem);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
