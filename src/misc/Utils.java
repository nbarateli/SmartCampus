package misc;

import model.accounts.User;
import model.lecture.CampusSubject;
import model.lecture.Lecture;
import model.rooms.*;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static model.database.SQLConstants.*;
import static model.rooms.Room.RoomType.*;
import static model.rooms.Room.SeatType.*;

import static model.accounts.User.UserType.*;
import static model.accounts.User.UserStatus.*;
import static model.accounts.User.UserRole.*;
import static model.accounts.User.*;

public class Utils {


    public static String toSqlTime(Time time) {
        DateFormat format = new SimpleDateFormat("hh:mm");

        return "STR_TO_DATE(\'" + format.format(time) + "\', \'%H:%i\')";

    }

    /**
     * Returns a Georgian string representation of the given <code>RoomType</code>
     */
    public static String roomTypeToString(Room.RoomType roomType) {
        switch (roomType) {
            case UTILITY:
                return "სხვა";
            case AUDITORIUM:
                return "აუდიტორია";
        }
        return "";
    }

    /**
     * Returns a Georgian string representation of the given <code>SeatType</code>
     */
    public static String seatTypeToString(Room.SeatType seatType) {
        switch (seatType) {
            case DESKS:
                return "სკამები და მერხები";
            case TABLES:
                return "მაგიდები";
            case COMPUTERS:
                return "კომპიუტერები";
            case WOODEN_CHAIR:
                return "სკამ-მერხები (ხის)";

            case PLASTIC_CHAIR:
                return "სკამ-მერხები (პლასტმასის)";

        }
        return "";
    }

    /**
     * Returns a corresponding <code>RoomType</code> object  that has a value of the passed string
     *
     * @param s a string representation of te room type caller is searching for
     * @return corresponding <code>RoomType</code>
     */
    public static Room.RoomType toRoomType(String s) {

        switch (s.toLowerCase()) {
            case "auditorium":
                return AUDITORIUM;
            case "utility":
                return UTILITY;
            case "any":
                return null;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a corresponding <code>SeatType</code> object  that has a value of the passed string
     *
     * @param s a string representation of te seat type caller is searching for
     * @return corresponding <code>SeatType</code>
     */
    public static Room.SeatType toSeatType(String s) {

        switch (s.toLowerCase()) {
            case "desks":
                return DESKS;
            case "wooden_chair":
                return WOODEN_CHAIR;
            case "plastic_chair":
                return PLASTIC_CHAIR;
            case "computers":
                return COMPUTERS;
            case "tables":
                return TABLES;
            case "any":
                return null;
        }
        throw new IllegalArgumentException();

    }

    /*
    * Converts given String to WeekDay format.
    * */
    public static Lecture.WeekDay toWeekDay(String day) {
        switch (day.toLowerCase()) {
            case "monday":
                return Lecture.WeekDay.MONDAY;
            case "tuesday":
                return Lecture.WeekDay.TUESDAY;
            case "wednesday":
                return Lecture.WeekDay.WEDNESDAY;
            case "thursday":
                return Lecture.WeekDay.THURSDAY;
            case "friday":
                return Lecture.WeekDay.FRIDAY;
            case "saturday":
                return Lecture.WeekDay.SATURDAY;
            case "sunday":
                return Lecture.WeekDay.SUNDAY;
        }
        return null;
    }

    /**
     * Returns a user object from current row of the given <code>ResultSet</code>.
     *
     */
    public static User getUserFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_USER_ID);
        String eMail = rs.getString(SQL_COLUMN_USER_EMAIL);
        String firstName = rs.getString(SQL_COLUMN_USER_FIRST_NAME);
        String lastName = rs.getString(SQL_COLUMN_USER_LAST_NAME);
        User.UserStatus status = toUserStatus(rs.getString(SQL_COLUMN_USER_STATUS));
        User.UserType userType = toUserType(rs.getString(SQL_COLUMN_USER_TYPE));
        User.UserRole role = toUserRole(rs.getString(SQL_COLUMN_USER_ROLE));

        return new User(id, eMail, firstName, lastName, status, userType, role);
    }

    public static RoomProblem getProblemFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_ROOM_PROBLEM_ID);
        User author = getUserFromResults(rs);
        Room room = getRoomFromResults(rs);
        String title = rs.getString(SQL_COLUMN_ROOM_PROBLEM_TITLE);
        String description = rs.getString(SQL_COLUMN_ROOM_PROBLEM_DESCRIPTION);
        Date dateCreated = rs.getDate(SQL_COLUMN_ROOM_PROBLEM_DATE_CREATED);
        return new RoomProblem(id, author, room, title, description, dateCreated);
    }

    /**
     * Returns a <code>Lecture</code> object from current row of the given <code>ResultSet</code>
     */
    public static Lecture getLectureFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_LECTURE_ID);
        User lecturer = getUserFromResults(rs);
        Room room = getRoomFromResults(rs);
        CampusSubject subject = getSubjectFromResults(rs);
        Lecture.WeekDay day = toWeekDay(rs.getString(SQL_COLUMN_LECTURE_DAY));
        Time starTime = rs.getTime(SQL_COLUMN_LECTURE_START_TIME);
        Time endTime = rs.getTime(SQL_COLUMN_LECTURE_END_TIME);
        return new Lecture(id, lecturer, room, subject, day, starTime, endTime);
    }

    private static CampusSubject getSubjectFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_SUBJECT_ID);
        String name = rs.getString(SQL_COLUMN_SUBJECT_NAME);
        return new CampusSubject(id, name);
    }

    /**
     * Returns a room object from current row of the given <code>ResultSet</code>
     */
    public static Room getRoomFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_ROOM_ID);
        int capacity = rs.getInt(SQL_COLUMN_ROOM_CAPACITY);
        int floor = rs.getInt(SQL_COLUMN_ROOM_FLOOR);
        String name = rs.getString(SQL_COLUMN_ROOM_NAME);
        Room.RoomType roomType =
                toRoomType(rs.getString(SQL_COLUMN_ROOM_TYPE));
        Room.SeatType seatType =
                toSeatType(rs.getString(SQL_COLUMN_ROOM_SEAT_TYPE));
        boolean available = rs.getBoolean(SQL_COLUMN_ROOM_AVAILABLE);

        return new Room(id, capacity, name, roomType, seatType, available, floor);
    }

    /**
     * Returns an associated enum value (if exists) of given string.
     */
    private static UserRole toUserRole(String s) {
        switch (s.toLowerCase()) {
            case "student":
                return STUDENT;
            case "lecturer":
                return LECTURER;
            case "staff":
                return STAFF;
        }
        return null;
    }

    /**
     * Returns an associated enum value (if exists) of given string.
     */
    private static UserType toUserType(String s) {
        switch (s.toLowerCase()) {
            case "user":
                return USER;
            case "admin":
                return ADMIN;
        }
        return null;
    }

    /**
     * Returns an associated enum value (if exists) of given string.
     */
    public static UserStatus toUserStatus(String s) {
        switch (s.toLowerCase()) {
            case "active":
                return ACTIVE;
            case "banned":
                return BANNED;
        }
        return null;
    }

    public static String toGeorgian(Lecture.WeekDay day) {
        switch (day) {
            case MONDAY:
                return "ორშაბათი";
            case TUESDAY:
                return "სამშაბათი";
            case WEDNESDAY:
                return "ოთხშაბათი";
            case THURSDAY:
                return "ხუთშაბათი";
            case FRIDAY:
                return "პარასკევი";
            case SATURDAY:
                return "შაბათი";
            default:
                return "კვირა";
        }
    }

    private Utils() {

    }
}
