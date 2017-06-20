package model.rooms.manager;

import model.database.DBConnector;
import model.lectures.Lecture;

import static model.database.SQLConstants.*;

import model.rooms.Room;
import model.rooms.RoomProblem;
import model.rooms.RoomSearchQuery;

import java.sql.*;
import java.util.*;

import static misc.Utils.*;

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
     * */
    public static RoomManager getInstance() {
        return instance == null ? instance = new DefaultRoomManager() : instance;
    }

    /**
     * Returns a list of rooms fetched by given SQL query
     */
    public static List<Room> findRooms(String sql) {
        List<Room> rooms = new ArrayList<>();
        try (ResultSet matches = DBConnector.executeQuery(sql)) {
            while (matches.next()) {
                rooms.add(getRoomFromResults(matches));
            }
        } catch (SQLException e) {
            //doing nothing
        }
        return rooms;
    }


    private DefaultRoomManager() {
    }
    
    @Override
    public List<Room> find(RoomSearchQuery query) {

        return findRooms(query.generateQuery());
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
            DBConnector.executeUpdate(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int roomID) {
        String deleteQuery = "DELETE  FROM  " + SQL_TABLE_ROOM +
                " WHERE  " + SQL_COLUMN_ROOM_ID + " = " + roomID;
        try {
            DBConnector.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addImage(Room room, String imageName) {
        String sql = "INSERT INTO room_image (image_url, room_id) VALUES ('" + imageName + "', " + room.getID() + ")";
        try {
        	DBConnector.executeUpdate(sql);
        } catch (SQLException e) {
            //doing nothing
        }
    }
    
    public void remove(Room room) {
        String sql = "DELETE FROM room_image where room_image.room_id = " + room.getID();
        try {
        	DBConnector.executeUpdate(sql);
        } catch (SQLException e) {
            //doing nothing
        }
    }

    @Override
    public List<String> getAllImagesOf(Room room) {
        List<String> images = new LinkedList<>();
        String sql = "SELECT image_url FROM  room_image WHERE room_image.room_id = " + room.getID();
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
    public Room getRoomByID(int id) {
        List<Room> rooms = findRooms("SELECT * FROM room where room_id = " + id);
        return rooms == null || rooms.size() == 0 ? null : rooms.get(0);
    }
    
	@Override
	public Room getRoomByName(String roomName) {
		List<Room> rooms = findRooms("SELECT * FROM room WHERE room_name = " + roomName);
		return rooms == null || rooms.size() == 0 ? null : rooms.get(0);
	}

    @Override
    public List<RoomProblem> findAllProblemsOf(Room room) {
        String sql = "SELECT * FROM room_problem " +
                " JOIN room ON room_problem.room_id = room.room_id " +
                " JOIN campus_user ON room_problem.reported_by = campus_user.user_id " +
                " WHERE room_problem.room_id = " + room.getID();
        List<RoomProblem> problems = new ArrayList<>();
        try (ResultSet results = DBConnector.executeQuery(sql)) {
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
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

}
