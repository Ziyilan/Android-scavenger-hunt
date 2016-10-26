package jason.scavenger_hunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zlan on 10/24/16.
 */
public class ManageCourseAdapter extends ArrayAdapter<Course> {

    private ArrayList<Course> courseList;

    public ManageCourseAdapter(Context context, ArrayList<Course> courseItem){
        super(context, 0, courseItem);
        this.courseList = courseItem;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        final Course courseItem = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_item, parent, false);
        }
        TextView courseName = (TextView) convertView.findViewById(R.id.courseNameManagePage);
        ImageButton courseDeleteButton = (ImageButton) convertView.findViewById(R.id.deleteCourseButton);
        courseName.setText(courseList.get(position).getName());
        courseDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }


}
