package model.rooms.manager;

import misc.Utils;
import model.database.DBConnector;
import model.lectures.Lecture;
import model.rooms.Room;
import model.rooms.RoomProblem;
import model.rooms.RoomSearchQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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

    /**
     * Returns the instance of RoomManager
     */
    public static RoomManager getInstance() {
        return instance == null ? instance = new DefaultRoomManager() : instance;
    }


    private DefaultRoomManager() {
    }

    @Override
    public List<Room> find(RoomSearchQuery query) {
        List<Room> rooms = new ArrayList<>();
        try (ResultSet matches = DBConnector.executeQuery(query.generateQuery())) {
            while (matches.next()) {
                rooms.add(getRoomFromResults(matches));
            }
        } catch (SQLException e) {
            //doing nothing
        }
        return rooms;

    }

    @Override
    public void add(Room room) {
        String insertQuery = "insert into " + SQL_TABLE_ROOM + " (" +
                SQL_COLUMN_ROOM_NAME + ", " + SQL_COLUMN_ROOM_FLOOR + ", " +
                SQL_COLUMN_ROOM_TYPE + ", " + SQL_COLUMN_ROOM_CAPACITY +
                ", " + SQL_COLUMN_ROOM_AVAILABLE + ", " +
                SQL_COLUMN_ROOM_SEAT_TYPE + ") values (\"" +
                room.getRoomName() + "\", " + room.getFloor() + ", '" +
                room.getRoomType().toString().toLowerCase() + "', " +
                room.getCapacity() + ", " + room.isAvailableForStudents() +
                ", '" + room.getSeatType().toString().toLowerCase() + "') ";
        try {
            //TODO
            DBConnector.executeUpdate(insertQuery, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int roomID) {
        String deleteQuery = "DELETE  FROM  " + SQL_TABLE_ROOM +
                " WHERE  " + SQL_COLUMN_ROOM_ID + " = " + roomID;
        try {
            //TODO
            DBConnector.executeUpdate(deleteQuery, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<String> getAllImagesOf(Room room) {
        List<String> images = new LinkedList<>();
        String sql = "SELECT image_url FROM  room_image WHERE room_image.room_id = room." + room.getID();
        //TODO
        try (ResultSet rs = DBConnector.executeQuery(sql)) {

            while (rs.next()) {
                images.add(rs.getString(SQL_COLUMN_ROOM_IMAGE_URL));
            }
        } catch (SQLException e) {
            //doing nothing
        }
        return images;
    }

    @Override
    public List<Lecture> findAllLecturesAt(Room room) {
        return findAllLecturesAt(room, null, null, null);
    }

    @Override
    public List<Lecture> findAllLecturesAt(Room room, Lecture.WeekDay day) {
        return findAllLecturesAt(room, day, null, null);
    }

    @Override
    public List<Lecture> findAllLecturesAt(Room room, Lecture.WeekDay day, Time start, Time end) {
        List<Lecture> lectures = new ArrayList<>();
        String sql = "SELECT * FROM " + SQL_TABLE_LECTURE + " JOIN " + SQL_TABLE_USER + " ON " +
                SQL_TABLE_LECTURE + "." + SQL_COLUMN_LECTURE_LECTURER + " = " + SQL_TABLE_USER + "." +
                SQL_COLUMN_USER_ID +
                " JOIN " + SQL_TABLE_SUBJECT + " ON " + SQL_TABLE_SUBJECT + "." + SQL_COLUMN_SUBJECT_ID +
                "= " + SQL_TABLE_LECTURE + "." + SQL_COLUMN_SUBJECT_ID + " \n " +
                " JOIN " + SQL_TABLE_ROOM + " ON " + SQL_TABLE_LECTURE + "." + SQL_COLUMN_ROOM_ID + "" +
                "= " + SQL_TABLE_ROOM + "." + SQL_COLUMN_ROOM_ID +
                " WHERE lecture.room_id = " + room.getID() +
                (day == null ? "" : " AND lecture.day_of_week = " + day.toString()) +
                (start == null ? "" : " AND start_time >= " + toSqlTime(start)) +
                (end == null ? "" : " AND end_time <= " + toSqlTime(end));
        //TODO
        try (ResultSet rs = DBConnector.executeQuery(sql)) {

            while (rs.next()) {
                lectures.add(getLectureFromResults(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //ignored
        }
        return lectures;
    }

    @Override
    public Room getRoomById(int id) {
        String sql = "SELECT * FROM " + SQL_TABLE_ROOM + " WHERE " +
                SQL_COLUMN_ROOM_ID + " = ?";
        try {
            ResultSet results = DBConnector.executeQuery(sql, id);
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
                SQL_COLUMN_ROOM_NAME + " = ?";
        try {
            ResultSet results = DBConnector.executeQuery(sql, roomName);
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
        String sql = "SELECT * FROM ? " +
                " JOIN ? ON ?.? = ?.? " +
                " JOIN ? ON ?.? = ?.? " +
                " WHERE ?.? = ?";
        List<RoomProblem> problems = new ArrayList<>();
        Object[] values = {
                SQL_TABLE_ROOM_PROBLEM, SQL_TABLE_ROOM, SQL_TABLE_ROOM_PROBLEM,
                SQL_COLUMN_ROOM_PROBLEM_ROOM, SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID, SQL_TABLE_USER,
                SQL_TABLE_ROOM_PROBLEM, SQL_COLUMN_ROOM_PROBLEM_REPORTED_BY, SQL_TABLE_USER,
                SQL_COLUMN_USER_ID, SQL_TABLE_ROOM_PROBLEM, SQL_COLUMN_ROOM_PROBLEM_ROOM,
                room.getID()
        };
        try (ResultSet results = DBConnector.executeQuery(sql, values)) {
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
        String sql = "INSERT INTO room_image (image_url, room_id) VALUES ('" + imageURL + "', " + room.getID() + ")";
        try {
            DBConnector.executeUpdate(sql, null);
        } catch (SQLException e) {
            //doing nothing
        }
    }

}
