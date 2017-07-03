package misc;

import model.accounts.User;
import model.bookings.Booking;
import model.bookings.Booking.WeekDay;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.rooms.Room;
import model.rooms.RoomProblem;
import serve.managers.DBConnector;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static model.accounts.User.UserPermission.*;
import static model.accounts.User.UserRole;
import static model.accounts.User.UserRole.*;
import static model.database.SQLConstants.*;
import static model.rooms.Room.RoomType.*;
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
        calendar.set(Calendar.SECOND, 0);
        return new Time(calendar.getTimeInMillis());
    }

    /**
     * Converts given String to WeekDay format.
     */
    public static WeekDay toWeekDay(String day) {
        switch (day.toLowerCase()) {
            case "ორშაბათი":
            case "monday":
                return WeekDay.MONDAY;
            case "სამშაბათი":
            case "tuesday":
                return WeekDay.TUESDAY;
            case "ოთხშაბათი":
            case "wednesday":
                return WeekDay.WEDNESDAY;
            case "ხუთშაბათი":
            case "thursday":
                return WeekDay.THURSDAY;
            case "პარასკევი":
            case "friday":
                return WeekDay.FRIDAY;
            case "შაბათი":
            case "saturday":
                return WeekDay.SATURDAY;
            case "კვირა":
            case "sunday":
                return WeekDay.SUNDAY;

        }
        return null;
    }

    /**
     * Given a date returns respective weekday
     */
    public static WeekDay toWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case 2:
                return WeekDay.MONDAY;
            case 3:
                return WeekDay.TUESDAY;
            case 4:
                return WeekDay.WEDNESDAY;
            case 5:
                return WeekDay.THURSDAY;
            case 6:
                return WeekDay.FRIDAY;
            case 7:
                return WeekDay.SATURDAY;
            case 1:
                return WeekDay.SUNDAY;

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
     * Returns a string representation of the given <code>RoomType</code>
     */
    public static String roomTypeToString(Room.RoomType roomType, boolean isGeorgian) {
        if (roomType == null) {
            return "";
        }
        switch (roomType) {
            case UTILITY:
                return isGeorgian ? "სხვა" : "utility";
            case AUDITORIUM:
                return isGeorgian ? "აუდიტორია" : "auditorium";
            case LABORATORY:
                return isGeorgian ? "ლაბორატორია" : "laboratory";
        }
        return "";
    }

    /**
     * Returns a Georgian string representation of the given <code>SeatType</code>
     */
    public static String seatTypeToString(Room.SeatType seatType, boolean isGeorgian) {
        if (seatType == null) {
            return "";
        }
        switch (seatType) {

            case DESKS:
                return isGeorgian ? "სკამები და მერხები" : "desks";
            case TABLES:
                return isGeorgian ? "მაგიდები" : "tables";
            case COMPUTERS:
                return isGeorgian ? "კომპიუტერები" : "computers";
            case CHAIRS:
                return isGeorgian ? "სკამები" : "chairs";

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
            case "lab":
            case "laboratory":
                return LABORATORY;
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
            case "chairs":
                return CHAIRS;
            case "computers":
                return COMPUTERS;
            case "tables":
                return TABLES;
            case "any":
                return null;
        }
        throw new IllegalArgumentException("caused by: " + s);

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

        return new User(id, eMail, firstName, lastName, role, "");
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
     * Returns a <code>Booking</code> object from current row of the given <code>ResultSet</code>
     */
    public static Booking getBookingFromResults(ResultSet rs) throws SQLException {
        int id = rs.getInt(SQL_COLUMN_BOOKING_ID);
        User booker = getUserFromResults(rs);
        Room room = getRoomFromResults(rs);
        CampusSubject subject = getSubjectFromResults(rs);
        WeekDay day = toWeekDay(rs.getString(SQL_COLUMN_BOOKING_WEEK_DAY));
        Time starTime = rs.getTime(SQL_COLUMN_BOOKING_START_TIME);
        Time endTime = rs.getTime(SQL_COLUMN_BOOKING_END_TIME);
        String description = rs.getString(SQL_COLUMN_BOOKING_DESCRIPTION);
        Date date = rs.getDate(SQL_COLUMN_BOOKING_DATE);
        return new Booking(id, booker, room, subject, day, starTime, endTime, description, date);
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

    public static String toGeorgian(WeekDay day) {
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
    public static boolean successfulOperation(String insertQuery, DBConnector connector, Object... values) {
        try {
            connector.executeUpdate(insertQuery, values);
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
     * Returns content of a file in passed path
     */
    public static String getContent(String path) {
        StringBuilder contents = new StringBuilder();
        File f = new File(path);
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(new FileInputStream(f), "UTF-8"))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                contents.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents.toString();

    }

    /**
     * Returns a {@code UserPermission} value of a given string
     */
    public static User.UserPermission toUserPermission(String permission) {
        switch (permission.toLowerCase()) {
            case "book_a_room":
                return BOOK_A_ROOM;
          /*  ('book_a_room', 'request_booked_room', 'cancel_booking', 'report_room_problem',
                    'delete_problem', 'lost_found_post', 'lost_found_delete', 'warn_user',
                    'view_user_warnings', 'delete_user_warnings', 'remove_permission', 'insert_data')*/
            case "request_booked_room":
                return REQUEST_BOOKED_ROOM;
            case "cancel_booking":
                return CANCEL_BOOKING;
            case "report_room_problem":
                return REPORT_ROOM_PROBLEM;
            case "delete_problem":
                return DELETE_PROBLEM;
            case "lost_found_post":
                return LOST_FOUND_POST;
            case "lost_found_delete":
                return LOST_FOUND_DELETE;
            case "warn_user":
                return WARN_USER;
            case "view_user_warnings":
                return VIEW_USER_WARNINGS;
            case "delete_user_warnings":
                return DELETE_USER_WARNINGS;
            case "remove_permission":
                return REMOVE_PERMISSION;
            case "insert_data":
                return INSERT_DATA;
        }
        return null;
    }
    
    /**
     * converts given string in MM/dd/yyyy format to Date
     * @param toConvert string needed to be converted
     * @return respective date
     */
    public static Date stringToDate(String toConvert){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            return format.parse(toConvert);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * checks if given string is a valid number and falls in given range
     * @param number string needed to be parsed
     * @param min minimum value this integer should be
     * @param max maximum value this integer should be
     * @return integer representation of this string if it does, else null 
     */
    public static Integer validateNumber(String number, int min, int max) {
        int numValue;
        try {
            numValue = Integer.parseInt(number);
            if(numValue < min && numValue > max)
                return null;
        } catch (NumberFormatException e) {
            return null; //the text wasn't a number
        }
        
        return numValue;
    }
    
    /**
     * adds given number of days to given date
     * @param date date to add days to
     * @param numDays number of days to add
     * @return date gotten by adding days
     */
    public static Date addDaysToDate(Date date, int numDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, numDays);
        
        return c.getTime();
    }

    public static String roleToString(UserRole role) {
        switch (role) {
            case STUDENT:
                return "student";
            case ADMIN:
                return "admin";
            case STAFF:
                return "staff";
            case LECTURER:
                return "lecturer";
            case SYS_ADMIN:
                return "sys_admin";
        }
        return "";
    }

    /**
     * checks if given string is valid for representing hour or minute
     */
    private static boolean numberStringIsValid(String s, boolean checkHour) {
        if(!checkHour && validateNumber(s, 0, 59) == null)
            return false;
        if(checkHour && validateNumber(s, 0, 23) == null)
            return false;

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
