package model.database;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * Contains names of tables and rows of SmartCampus database.
 */
public class SQLConstants {
    public static final String SQL_TABLE_USER = "campus_user";

    public static final String SQL_TABLE_ROOM = "room";

    public static final String SQL_TABLE_BOOKING = "booking";

    public static final String SQL_TABLE_LECTURE = "lecture";

    public static final String SQL_TABLE_ROOM_PROBLEM = "room_problem";

    public static final String SQL_TABLE_USER_PROBLEM = "user_problem";

    public static final String SQL_TABLE_CORRIDOR_PROBLEM = "corridor_problem";

    public static final String SQL_TABLE_ITEM_REPORT = "item_report";

    public static final String SQL_TABLE_ITEM_IMAGE = "item_image";

    public static final String SQL_TABLE_ROOM_IMAGE = "room_image";

    public static final String SQL_TABLE_SUBJECT = "campus_subject";


    public static final String SQL_COLUMN_USER_ID = "user_id";
    public static final String SQL_COLUMN_USER_FIRST_NAME = "first_name";
    public static final String SQL_COLUMN_USER_LAST_NAME = "last_name";
    public static final String SQL_COLUMN_USER_EMAIL = "user_email";
    public static final String SQL_COLUMN_USER_TYPE = "user_type";
    public static final String SQL_COLUMN_USER_ROLE = "user_role";
    public static final String SQL_COLUMN_USER_STATUS = "user_status";
    public static final String SQL_COLUMN_USER_IMAGE = "img_url";

    public static final String SQL_COLUMN_ROOM_ID = "room_id";
    public static final String SQL_COLUMN_ROOM_NAME = "room_name";
    public static final String SQL_COLUMN_ROOM_FLOOR = "room_floor";
    public static final String SQL_COLUMN_ROOM_CAPACITY = "capacity";
    public static final String SQL_COLUMN_ROOM_AVAILABLE = "available_for_students";
    public static final String SQL_COLUMN_ROOM_TYPE = "room_type";
    public static final String SQL_COLUMN_ROOM_SEAT_TYPE = "seat_type";

    public static final String SQL_COLUMN_BOOKING_ID = "booking_id";
    public static final String SQL_COLUMN_BOOKING_ROOM = "room_id";
    public static final String SQL_COLUMN_BOOKING_BOOKER = "booker_id";
    public static final String SQL_COLUMN_BOOKING_START_DATE = "start_date";

    public static final String SQL_COLUMN_LECTURE_ID = "lecture_id";
    public static final String SQL_COLUMN_LECTURE_LECTURER = "lecturer";
    public static final String SQL_COLUMN_LECTURE_ROOM = "room_id";
    public static final String SQL_COLUMN_LECTURE_SUBJECT = "subject_id";
    public static final String SQL_COLUMN_LECTURE_DAY = "day_of_week";
    public static final String SQL_COLUMN_LECTURE_START_TIME = "start_time";
    public static final String SQL_COLUMN_LECTURE_END_TIME = "end_time";

    public static final String SQL_COLUMN_ROOM_PROBLEM_ID = "problem_id";
    public static final String SQL_COLUMN_ROOM_PROBLEM_ROOM = "room_id";
    public static final String SQL_COLUMN_ROOM_PROBLEM_REPORTED_BY = "reported_by";
    public static final String SQL_COLUMN_ROOM_PROBLEM_SOLVED_BY = "solved_by";
    public static final String SQL_COLUMN_ROOM_PROBLEM_TITLE = "title";
    public static final String SQL_COLUMN_ROOM_PROBLEM_DESCRIPTION = "description";
    public static final String SQL_COLUMN_ROOM_PROBLEM_DATE_CREATED = "date_created";

    public static final String SQL_COLUMN_CORRIDOR_PROBLEM_ID = "problem_id";
    public static final String SQL_COLUMN_CORRIDOR_PROBLEM_REPORTED_BY = "reported_by";
    public static final String SQL_COLUMN_CORRIDOR_PROBLEM_SOLVED_BY = "solved_by";
    public static final String SQL_COLUMN_CORRIDOR_PROBLEM_FLOOR = "floor";
    public static final String SQL_COLUMN_CORRIDOR_PROBLEM_TITLE = "title";
    public static final String SQL_COLUMN_CORRIDOR_PROBLEM_DESCRIPTION = "description";

    public static final String SQL_COLUMN_ITEM_REPORT_ID = "report_id";
    public static final String SQL_COLUMN_ITEM_REPORT_AUTHOR_ID = "author_id";
    public static final String SQL_COLUMN_ITEM_REPORT_NAME = "item_name";
    public static final String SQL_COLUMN_ITEM_REPORT_DESCRIPTION = "item_description";
    public static final String SQL_COLUMN_ITEM_REPORT_TYPE = "report_type";
    public static final String SQL_COLUMN_ITEM_REPORT_DATE_ADDED = "date_added";

    public static final String SQL_COLUMN_ITEM_IMAGE_ID = "image_id";
    public static final String SQL_COLUMN_ITEM_IMAGE_URL = "image_url";
    public static final String SQL_COLUMN_ITEM_IMAGE_REPORT_ID = "report_id";

    public static final String SQL_COLUMN_SUBJECT_ID = "subject_id";
    public static final String SQL_COLUMN_SUBJECT_NAME = "subject_name";

    public static final String SQL_COLUMN_ROOM_IMAGE_ID = "image_id";
    public static final String SQL_COLUMN_ROOM_IMAGE_URL = "image_url";
    public static final String SQL_COLUMN_ROOM_IMAGE_ROOM_ID = "room_id";
}
