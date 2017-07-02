package serve.managers;

import misc.Utils;
import model.bookings.Booking;
import model.campus.CampusSearchQuery;
import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;
import model.rooms.Room;
import model.rooms.RoomManager;
import model.rooms.RoomProblem;
import model.rooms.RoomSearchQueryGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

/**
 * Created by Niko on 10.06.2017.
 * <p>
 * The default implementation of the <code>RoomManager</code> interface.
 * Uses Database Connection to store and retrieve requested information.
 */
public class DefaultRoomManager implements RoomManager {
    private static RoomManager instance;


    private final DBConnector connector;


    DefaultRoomManager(DBConnector connector) {
        this.connector = connector;
    }

    @Override
    public List<Room> find(RoomSearchQueryGenerator queryGenerator) {
        List<Room> rooms = new ArrayList<>();
        CampusSearchQuery query = queryGenerator.generateQuery();
        try (ResultSet matches = connector.executeQuery(query.getQuery(), query.getValues())) {
            while (matches.next()) {
                rooms.add(getRoomFromResults(matches));
            }
        } catch (SQLException e) {
            //doing nothing
        }
        return rooms;

    }

    @Override
    public boolean add(Room room) {
        String insertQuery = "insert into " + SQL_TABLE_ROOM + " (" +
                SQL_COLUMN_ROOM_NAME + ", " + SQL_COLUMN_ROOM_FLOOR + ", " +
                SQL_COLUMN_ROOM_TYPE + ", " + SQL_COLUMN_ROOM_CAPACITY +
                ", " + SQL_COLUMN_ROOM_AVAILABLE + ", " +
                SQL_COLUMN_ROOM_SEAT_TYPE + ") values (?,?,?,?,?,?) ";
        return successfulOperation(insertQuery, connector, "'" + room.getRoomName() + "'", room.getFloor(),
                "'" + room.getRoomType().toString().toLowerCase() + "'", room.getCapacity(),
                room.isAvailableForStudents(), "'" + room.getSeatType().toString().toLowerCase() + "'");
    }

    @Override
    public boolean remove(int entityId) {
        String deleteQuery = "DELETE  FROM  " + SQL_TABLE_ROOM +
                " WHERE  " + SQL_COLUMN_ROOM_ID + " = " + entityId;
        return successfulOperation(deleteQuery, connector);
    }


    @Override
    public List<String> getAllImagesOf(Room room) {
        List<String> images = new LinkedList<>();
        String sql = "SELECT " + SQL_COLUMN_ROOM_IMAGE_URL + " FROM " + SQL_TABLE_ROOM_IMAGE +
                " WHERE " + SQL_COLUMN_ROOM_IMAGE_ROOM_ID + " = ?";
        //TODO
        try (ResultSet rs = connector.executeQuery(sql, room.getId())) {

            while (rs.next()) {
                images.add(rs.getString(SQL_COLUMN_ROOM_IMAGE_URL));
            }
        } catch (SQLException e) {
            //doing nothing
        }
        return images;
    }

    @Override
    public List<Booking> findAllBookingsAt(Room room) {
        return findAllBookingsAt(room, null, null, null);
    }

    @Override
    public List<Booking> findAllBookingsAt(Room room, Lecture.WeekDay day) {
        return findAllBookingsAt(room, day, null, null);
    }

    @Override
    public List<Booking> findAllBookingsAt(Room room, Lecture.WeekDay day, Time start, Time end) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM " + SQL_TABLE_BOOKING + " INNER JOIN " + SQL_TABLE_USER + " ON " +
                SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_BOOKER + " = " + SQL_TABLE_USER + "." +
                SQL_COLUMN_USER_ID +
                " INNER JOIN " + SQL_TABLE_SUBJECT + " ON " + SQL_TABLE_SUBJECT + "." + SQL_COLUMN_SUBJECT_ID +
                " = " + SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_SUBJECT_ID + " \n " +
                " INNER JOIN " + SQL_TABLE_ROOM + " ON " + SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_ROOM +
                " = " + SQL_TABLE_ROOM + "." + SQL_COLUMN_ROOM_ID +
                " WHERE " + SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_ROOM + " = ? " +
                (start == null ? "" : " AND " + SQL_COLUMN_BOOKING_START_TIME + " >= ? ") +
                (end == null ? "" : " AND " + SQL_COLUMN_BOOKING_END_TIME + " <= ? ") +
                (day == null ? "" : " AND " + SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_WEEK_DAY + " = ? ");
        //TODO
        try (ResultSet rs = connector.executeQuery(sql, room.getId(),
                (start == null ? null : toSqlTime(start)),
                (end == null ? null : toSqlTime(end)),
                (day == null ? null : "'" + day.toString().toLowerCase() + "'"))) {

            while (rs.next()) {
                bookings.add(getBookingFromResults(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //ignored
        }
        return bookings;
    }

    @Override
    public Booking findBookingAt(Room room, Date date, Time current) {
        WeekDay day = toWeekDay(date);
        Booking booking = null;

        String sql = "SELECT * FROM " + SQL_TABLE_BOOKING + " INNER JOIN " + SQL_TABLE_USER + " ON " +
                SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_BOOKER + " = " + SQL_TABLE_USER + "." +
                SQL_COLUMN_USER_ID +
                " INNER JOIN " + SQL_TABLE_SUBJECT + " ON " + SQL_TABLE_SUBJECT + "." + SQL_COLUMN_SUBJECT_ID +
                " = " + SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_SUBJECT_ID + " \n " +
                " INNER JOIN " + SQL_TABLE_ROOM + " ON " + SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_ROOM +
                " = " + SQL_TABLE_ROOM + "." + SQL_COLUMN_ROOM_ID +
                " WHERE " + SQL_TABLE_BOOKING + "." + SQL_COLUMN_BOOKING_ROOM + " = ? " +
                " AND " + SQL_COLUMN_BOOKING_WEEK_DAY + " = ? " +
                " AND " + SQL_COLUMN_BOOKING_START_TIME + " <= ? AND " + SQL_COLUMN_BOOKING_END_TIME + " >= ?";

        try (ResultSet rs = connector.executeQuery(sql, room.getId(),
                "'" + day.toString().toLowerCase() + "'", toSqlTime(current), toSqlTime(current))) {
            rs.next();
            booking = getBookingFromResults(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            //ignored
        }
        return booking;
    }

    @Override
    public boolean isBooked(Room room, Date date, Time current) {

        return (findBookingAt(room, date, current) == null);
    }

    @Override
    public Room getRoomById(int id) {
        String sql = "SELECT * FROM " + SQL_TABLE_ROOM + " WHERE " +
                SQL_COLUMN_ROOM_ID + " = ?";
        try {
            ResultSet results = connector.executeQuery(sql, id);
            if (results.next()) {
                return Utils.getRoomFromResults(results);
            }
        } catch (SQLException e) {
            //ignored
        }
        return null;
    }

    @Override
    public Room getRoomByName(String roomName) {

        String sql = "SELECT * FROM " + SQL_TABLE_ROOM + " WHERE " +
                SQL_COLUMN_ROOM_NAME + " like ?";
        try {
            ResultSet results = connector.executeQuery(sql, roomName);
            if (results.next()) {
                return Utils.getRoomFromResults(results);
            }
        } catch (SQLException e) {
            //ignored
        }
        return null;
    }

    @Override
    public List<RoomProblem> findAllProblemsOf(Room room) {
        String sql = String.format("SELECT * FROM %s " +
                        " JOIN %s ON %s.%s = %s.%s " +
                        " JOIN %s ON %s.%s = %s.%s " +
                        " WHERE %s.%s = ?", SQL_TABLE_ROOM_PROBLEM, SQL_TABLE_ROOM, SQL_TABLE_ROOM_PROBLEM,
                SQL_COLUMN_ROOM_PROBLEM_ROOM, SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID, SQL_TABLE_USER,
                SQL_TABLE_ROOM_PROBLEM, SQL_COLUMN_ROOM_PROBLEM_REPORTED_BY, SQL_TABLE_USER,
                SQL_COLUMN_USER_ID, SQL_TABLE_ROOM_PROBLEM, SQL_COLUMN_ROOM_PROBLEM_ROOM);
        List<RoomProblem> problems = new ArrayList<>();

        try (ResultSet results = connector.executeQuery(sql, room.getId())) {
            while (results.next()) {
                problems.add(getProblemFromResults(results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //ignored
        }
        return problems;
    }

    @Override
    public void addImage(Room room, String imageURL) {
        String sql = "INSERT INTO " + SQL_TABLE_ROOM_IMAGE + " (" + SQL_COLUMN_ROOM_IMAGE_URL + "," +
                SQL_COLUMN_ROOM_IMAGE_ROOM_ID + ") VALUES (?,?)";
        try {
            connector.executeUpdate(sql, "'" + imageURL + "'", room.getId());
        } catch (SQLException e) {
            //doing nothing
        }
    }

}
