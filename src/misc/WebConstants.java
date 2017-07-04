package misc;

import model.accounts.User;

/**
 * Created by Niko on 10.06.2017.
 * <p>
 * Stores all necessary constants for Web side of the project.
 */
public class WebConstants {
    public static final String MANAGER_FACTORY = "managerFactory";

    public static final String SUCCESS = "success";
    public static final String FAILED = "failure";

    public static final String NO_IMAGE = "http://i.imgur.com/wpwWazp.png";

    public static final String JSON_ROOM_ID = "id";
    public static final String JSON_ROOM_NAME = "name";
    public static final String JSON_ROOM_FLOOR = "floor";
    public static final String JSON_ROOM_CAPACITY = "capacity";
    public static final String JSON_ROOM_TYPE = "roomtype";
    public static final String JSON_ROOM_SEAT_TYPE = "seattype";
    public static final String JSON_ROOM_AVAILABLE = "available";
    public static final String JSON_ROOM_MAIN_IMAGE = "mainimage";
    public static final String JSON_ROOM_IMAGES = "images";
    public static final String JSON_ROOM_PROBLEMS = "problems";
    public static final String JSON_ROOM_LECTURES = "lectures";
    public static final String JSON_ROOM_TAKEN = "booked";
    public static final String JSON_ROOM_BOOKINGS = "bookings";
    public static final String JSON_ROOM_BOOKING_ID = "bookingID";


    public static final String JSON_ROOM_PROBLEM_ID = "id";
    public static final String JSON_ROOM_PROBLEM_AUTHOR = "author";
    public static final String JSON_ROOM_PROBLEM_ROOM_ = "room";
    public static final String JSON_ROOM_PROBLEM_TITLE = "title";
    public static final String JSON_ROOM_PROBLEM_DESCR = "description";
    public static final String JSON_ROOM_PROBLEM_DATE = "datecreated";

    public static final String JSON_BOOKING_ID = "id";
    public static final String JSON_BOOKING_BOOKER = "lecturer";
    public static final String JSON_BOOKING_ROOM = "room";
    public static final String JSON_BOOKING_SUBJECT = "subject";
    public static final String JSON_BOOKING_DAY = "day";
    public static final String JSON_BOOKING_START_TIME = "starttime";
    public static final String JSON_BOOKING_END_TIME = "endtime";

    public static final String JSON_ROOM_ERROR_BAD_PARAM = "bad parameter";
    public static final String JSON_ROOM_ERROR_NOT_FOUND = "room not found";

    public static final String JSON_ERROR = "error";
    public static final String SIGNED_ACCOUNT = "signed_account";
    public static final User.UserRole DEFAULT_USER_ROLE = User.UserRole.STUDENT;
}


