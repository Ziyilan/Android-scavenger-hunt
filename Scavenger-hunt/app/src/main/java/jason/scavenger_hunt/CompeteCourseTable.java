package jason.scavenger_hunt;

import android.provider.BaseColumns;

/**
 * Created by mafaldaborges on 10/24/16.
 */
public class CompeteCourseTable {
    private CompeteCourseTable(){}

    public static class FeedEntryCourse implements BaseColumns {
        public static final String COURSE_TABLE_NAME = "course_table";
        public static final String COURSE_NAME = "course_name";
        public static final String COURSE_BEST_TIME = "best_time";
        public static final String COURSE_NOP = "number_of_points";
        public static final String COURSE_LATITUDE = "latitude";
        public static final String COURSE_LONGITUDE = "longitude";


        public static final String TEXT_TYPE = " TEXT";
        public static final String INT_TYPE = " INTEGER";
        public static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + COURSE_TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COURSE_NAME + TEXT_TYPE + COMMA_SEP +
                        COURSE_BEST_TIME + TEXT_TYPE + COMMA_SEP +
                        COURSE_NOP + TEXT_TYPE + COMMA_SEP +
                        COURSE_LATITUDE + TEXT_TYPE + COMMA_SEP +
                        COURSE_LONGITUDE + TEXT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + COURSE_TABLE_NAME;
    }
}
