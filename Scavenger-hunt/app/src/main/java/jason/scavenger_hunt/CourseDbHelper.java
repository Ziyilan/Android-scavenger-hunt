package jason.scavenger_hunt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mafaldaborges on 10/24/16.
 */
public class CourseDbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CourseDatabase.db";

    public CourseDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CompeteCourseTable.FeedEntryCourse.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(CompeteCourseTable.FeedEntryCourse.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addToCourseList(Course course){
        SQLiteDatabase dbw = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_NAME, course.getName());
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_BEST_TIME, String.valueOf(course.getYourTime()));
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_NOP, String.valueOf(course.getNumOfPoints()));
        //Todo: Does that work to cast to string?
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_LATITUDE, arrayLatToString(course.getLatitude()));
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_LONGITUDE, arrayLonToString(course.getLongitude()));
        long newRowId = dbw.insert(CompeteCourseTable.FeedEntryCourse.COURSE_TABLE_NAME, null, values);

        //Todo: Finish this method
    }

    public void deleteRow(Course course){
        SQLiteDatabase dbw = this.getWritableDatabase();
        String selection = CompeteCourseTable.FeedEntryCourse._ID + " =?";
        String[] selectionArgs = {String.valueOf(course.getId())};
        dbw.delete(CompeteCourseTable.FeedEntryCourse.COURSE_TABLE_NAME, selection, selectionArgs);
        dbw.close();
    }

    public ArrayList<Course> getAll(){
        SQLiteDatabase dbr = this.getReadableDatabase();
        ArrayList<Course> courseArray = new ArrayList<>();
        Cursor c = dbr.rawQuery("select * from " + CompeteCourseTable.FeedEntryCourse.COURSE_TABLE_NAME, null);
        c.moveToFirst();

        while (!c.isAfterLast()){
            long readID = c.getLong(0);
            String readName = c.getString(1);
            String readTime = c.getString(2);
            String readNOP = c.getString(3);
            String readLat = c.getString(4);
            String readLon = c.getString(5);

            Course courseInput = new Course(readName, Integer.parseInt(readTime), Integer.parseInt(readNOP), stringToLatArray(readLat), stringToLonArray(readLon));
            courseInput.setId(readID);
            courseArray.add(courseInput);

            c.moveToNext();
        }
        dbr.close();
        return courseArray;
    }

    public Course getCourse(long id) {
        String query = "select * from " + CompeteCourseTable.FeedEntryCourse.COURSE_TABLE_NAME + " where " + CompeteCourseTable.FeedEntryCourse._ID
                + " = " + id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        long readID = c.getLong(0);
        String readName = c.getString(1);
        String readTime = c.getString(2);
        String readNOP = c.getString(3);
        String readLat = c.getString(4);
        String readLon = c.getString(5);

        Course courseInput = new Course(readName, Integer.parseInt(readTime), Integer.parseInt(readNOP), stringToLatArray(readLat), stringToLonArray(readLon));
        courseInput.setId(readID);

        c.close();
        db.close();

        return courseInput;
    }

    public void updateArray(long id, Course course){
        ArrayList<Course> courseArray = new ArrayList<>();
        SQLiteDatabase dbr = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_NAME, course.getName());
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_BEST_TIME, String.valueOf(course.getYourTime()));
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_NOP, String.valueOf(course.getNumOfPoints()));
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_LATITUDE,arrayLatToString(course.getLatitude()));
        values.put(CompeteCourseTable.FeedEntryCourse.COURSE_LONGITUDE, arrayLonToString(course.getLongitude()));

        String selection = CompeteCourseTable.FeedEntryCourse._ID+ " Like ?";
        String[] selectionArgs = {Long.toString(id)};

        dbr.update(CompeteCourseTable.FeedEntryCourse.COURSE_TABLE_NAME, values, selection, selectionArgs);


    }


    private String arrayLatToString (ArrayList<Latitude> latitudes){
        StringBuilder sb = new StringBuilder();
        for (Latitude s : latitudes){
            sb.append(s.getLatitude());
            sb.append(",");
        }
        return sb.toString();
    }

    private String arrayLonToString(ArrayList<Longitude> longitudes){
        StringBuilder sb = new StringBuilder();
        for (Longitude s : longitudes){
            sb.append(s.getLongitude());
            sb.append(",");
        }
        return sb.toString();
    }

    private ArrayList<Latitude> stringToLatArray (String string){
        String[] strings = string.split(",");
        ArrayList<Latitude> newArray = new ArrayList<>();

        if (string.isEmpty()) {
            return newArray;
        }


        for (int i = 0; i < strings.length; i++){
            Latitude latitude = new Latitude(Double.parseDouble(strings[i]));
            newArray.add(latitude);
        }
        return newArray;
    }

    private ArrayList<Longitude> stringToLonArray (String string){
        String[] strings = string.split(",");
        ArrayList<Longitude> newArray = new ArrayList<>();

        if (string.isEmpty()) {
            return newArray;
        }

        for (int i = 0; i < strings.length; i++){
            String stringVersionofLon = strings[i];
            Longitude longitude = new Longitude(Double.parseDouble(strings[i]));
            newArray.add(longitude);
        }
        return newArray;
    }

}
