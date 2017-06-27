package misc;

import model.accounts.User;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.managers.DBConnector;
import model.rooms.Room;
import model.rooms.RoomProblem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static model.accounts.User.UserRole;
import static model.accounts.User.UserRole.*;
import static model.database.SQLConstants.*;
import static model.rooms.Room.RoomType.AUDITORIUM;
import static model.rooms.Room.RoomType.UTILITY;
import static model.rooms.Room.SeatType.*;

public final class Utils {

    /**
     * converts Date object to respective string in dd.MM.yyyy HH:mm format
     *
     * @param date Date object to convert
     * @return string representation of date
     */
    public static String exactDateToString(Date date) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return format.format(date);
    }

    /**
     * converts Time object to respective string in HH:mm format
     *
     * @param time Time object to be converted
     * @return string representation of time
     */
    public static String toHHMM(Time time) {
        DateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time);
    }

    /**
     * returns the Time object corresponding
     * to its String representation
     */
    public static Time toHHMM(String time) {
        if (!isValidFormat(time)) {
            return null;
        }

        int hour = Integer.valueOf(time.substring(0, 2));
        int min = Integer.valueOf(time.substring(3, 5));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, min);
        return new Time(calendar.getTimeInMillis());
    }

    /**
    * Converts given String to WeekDay format.
    */
    public static Lecture.WeekDay toWeekDay(String day) {
        switch (day.toLowerCase()) {
            case "ორშაბათი":
            case "monday":
                return Lecture.WeekDay.MONDAY;
            case "სამშაბათი":
            case "tuesday":
                return Lecture.WeekDay.TUESDAY;
            case "ოთხშაბათი":
            case "wednesday":
                return Lecture.WeekDay.WEDNESDAY;
            case "ხუთშაბათი":
            case "thursday":
                return Lecture.WeekDay.THURSDAY;
            case "პარასკევი":
            case "friday":
                return Lecture.WeekDay.FRIDAY;
            case "შაბათი":
            case "saturday":
                return Lecture.WeekDay.SATURDAY;
            case "კვირა":
            case "sunday":
                return Lecture.WeekDay.SUNDAY;

        }
        return null;
    }
    
    /**
     * Given a date returns respective weekday
     */
     public static Lecture.WeekDay toWeekDay(Date date) {
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         int day = cal.get(Calendar.DAY_OF_WEEK);
         
         switch (day) {
             case 2:
                 return Lecture.WeekDay.MONDAY;
             case 3:
                 return Lecture.WeekDay.TUESDAY;
             case 4:
                 return Lecture.WeekDay.WEDNESDAY;
             case 5:
                 return Lecture.WeekDay.THURSDAY;
             case 6:
                 return Lecture.WeekDay.FRIDAY;
             case 7:
                 return Lecture.WeekDay.SATURDAY;
             case 1:
                 return Lecture.WeekDay.SUNDAY;

         }
         return null;
     }

    /**
     * return sql format of given time
     */
    public static String toSqlTime(Time time) {

        return "STR_TO_DATE(\'" + toHHMM(time) + "\', \'%H:%i\')";

    }

    /**
     * Returns a Georgian string representation of the given <code>RoomType</code>
     */
    public static String roomTypeToString(Room.RoomType roomType) {
        if (roomType == null) {
            return "";
        }
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
        if (seatType == null) {
            return "";
        }
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

    /**
     * Returns a user object from current row of the given <code>ResultSet</code>.
     */
    public static User getUserFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_USER_ID);
        String eMail = rs.getString(SQL_COLUMN_USER_EMAIL);
        String firstName = rs.getString(SQL_COLUMN_USER_FIRST_NAME);
        String lastName = rs.getString(SQL_COLUMN_USER_LAST_NAME);
        UserRole role = toUserRole(rs.getString(SQL_COLUMN_USER_ROLE));

        return new User(id, eMail, firstName, lastName, role);
    }

    /**
     * Returns a <code>RoomProblem</code> object from current row of the given <code>ResultSet</code>
     * <p>
     * NOTE: The given result set must also contain columns from user and room tables.
     */
    public static RoomProblem getProblemFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_ROOM_PROBLEM_ID);
        User author = getUserFromResults(rs);
        Room room = getRoomFromResults(rs);
        String title = rs.getString(SQL_COLUMN_ROOM_PROBLEM_TITLE);
        String description = rs.getString(SQL_COLUMN_ROOM_PROBLEM_DESCRIPTION);
        java.sql.Date dateCreated = rs.getDate(SQL_COLUMN_ROOM_PROBLEM_DATE_CREATED);
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
     * Returns a subject from current row of the given <code>ResultSet</code>
     */
    public static CampusSubject getSubjectFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_SUBJECT_ID);
        String name = rs.getString(SQL_COLUMN_SUBJECT_NAME);
        return new CampusSubject(id, name);
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

    /**
     * Returns an associated enum value (if exists) of given string.
     */
    public static UserRole toUserRole(String s) {
        switch (s.toLowerCase()) {
            case "student":
                return STUDENT;
            case "lecturer":
                return LECTURER;
            case "staff":
                return STAFF;
            case "admin":
                return ADMIN;
            case "sys_admin":
                return SYS_ADMIN;
        }
        return null;
    }

    /**
     * Runs an SQL query and returns whether it was successful
     *
     * @param insertQuery a query to be executed
     * @param connector   a database connector
     * @return status of the operation
     */
    public static boolean successfulOperation(String insertQuery, DBConnector connector) {
        try {
            connector.executeUpdate(insertQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Generates and returns a part of SQL query. Returns  1 = 1 if the passed field is null.
     *
     * @param field  an object of a field. Can be null.
     * @param column name of the column in the database
     * @param values a List of objects that contain actual values in the SQL query
     */
    public static String generateEqualQuery(Object field, String column, List<Object> values) {
        if (field == null) return " 1 = 1 ";
        values.add(field);
        return column + " = ?";
    }

    /**
     * Generates and returns a part of SQL query. Returns  1 = 1 if the passed field is null.
     *
     * @param field  an object of a field. Can be null.
     * @param column name of the column in the database
     * @param values a List of objects that contain actual values in the SQL query
     * @param larger whether the returned query should yield >= in it.
     */
    public static String generateEqualsOrQuery(Object field, String column, List<Object> values, boolean larger) {
        if (field == null) return " 1 = 1 ";
        values.add(field instanceof Time ? Utils.toSqlTime((Time) field) : field);
        return column + " " + (larger ? ">" : "<") + "= ?";
    }

    /**
     * Generates and returns a part of SQL query. Returns  1 = 1 if the passed field is null.
     *
     * @param field  an object of a field. Can be null.
     * @param column name of the column in the database
     * @param values a List of objects that contain actual values in the SQL query
     */
    public static String generateLikeQuery(Object field, String column, List<Object> values) {
        if (field == null) return " 1 = 1 ";
        values.add("%" + field + "%");
        return column + " Like ?";
    }

    /**
     * Returns the exact list in the format of java array.
     */
    public static Object[] asArray(List<Object> values) {
        Object[] result = new Object[values.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    /**
     * Generates an SQL query line for given boolean value and adds it to the list of values, if it isn't null.
     *
     * @param field  a boolean object for a field. Can be null.
     * @param column name of the column in the database
     * @param values a List of objects that contain actual values in the SQL query
     */
    public static String generateBooleanQuery(Boolean field, String column, List<Object> values) {
        if (field == null) return " 1 = 1 ";
        values.add(field ? "TRUE" : "FALSE");
        return column + " = ?";
    }

    /**
     * checks if given string is valid for representing hour or minute
     */
    private static boolean numberStringIsValid(String s, boolean checkHour) {
        try {
            int numValue = Integer.parseInt(s);
            //capacity and floor can't be negative
            //their input types are "number" but some old browsers don't have support for
            //that and we don't won't any exceptions
            if (numValue < 0 || (checkHour && numValue >= 24) || (!checkHour && numValue > 59)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false; //the text wasn't a number
        }

        return true;
    }

    /**
     * Returns whether the first 5 characters of passed {@code String} form a valid time format.
     *
     * @param time a {@code String} value of a time to be checked.
     * @return whether the first 5 characters of the argument form a valid time format.
     */
    private static boolean isValidFormat(String time) {
        return time.length() >= 5 && time.charAt(2) == ':' &&
                numberStringIsValid(time.substring(0, 2), true) &&
                numberStringIsValid(time.substring(3, 5), false);
    }

    private Utils() {

    }
}
