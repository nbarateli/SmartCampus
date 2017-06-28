package model.managers;

import misc.Utils;
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
                SQL_COLUMN_ROOM_SEAT_TYPE + ") values (\"" +
                room.getRoomName() + "\", " + room.getFloor() + ", '" +
                room.getRoomType().toString().toLowerCase() + "', " +
                room.getCapacity() + ", " + room.isAvailableForStudents() +
                ", '" + room.getSeatType().toString().toLowerCase() + "') ";
        return successfulOperation(insertQuery, connector);
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
        String sql = "SELECT image_url FROM  room_image WHERE room_image.room_id =" + room.getId();
        //TODO
        try (ResultSet rs = connector.executeQuery(sql)) {

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
        String sql = "SELECT * FROM " + SQL_TABLE_LECTURE + " INNER JOIN " + SQL_TABLE_USER + " ON " +
                SQL_TABLE_LECTURE + "." + SQL_COLUMN_LECTURE_LECTURER + " = " + SQL_TABLE_USER + "." +
                SQL_COLUMN_USER_ID +
                " INNER JOIN " + SQL_TABLE_SUBJECT + " ON " + SQL_TABLE_SUBJECT + "." + SQL_COLUMN_SUBJECT_ID +
                "= " + SQL_TABLE_LECTURE + "." + SQL_COLUMN_SUBJECT_ID + " \n " +
                " INNER JOIN " + SQL_TABLE_ROOM + " ON " + SQL_TABLE_LECTURE + "." + SQL_COLUMN_ROOM_ID + "" +
                "= " + SQL_TABLE_ROOM + "." + SQL_COLUMN_ROOM_ID +
                " WHERE lecture.room_id = " + room.getId() +
                (day == null ? "" : " AND lecture.day_of_week = '" + day.toString().toLowerCase() + "'") +
                (start == null ? "" : " AND start_time >= " + toSqlTime(start)) +
                (end == null ? "" : " AND end_time <= " + toSqlTime(end));
        //TODO
        try (ResultSet rs = connector.executeQuery(sql)) {

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
    public boolean isLectureAt(Room room, Date date, Time current) {
        WeekDay day = toWeekDay(date);
        
        String sql = "SELECT * FROM " + SQL_TABLE_LECTURE + " INNER JOIN " + SQL_TABLE_USER + " ON " +
                SQL_TABLE_LECTURE + "." + SQL_COLUMN_LECTURE_LECTURER + " = " + SQL_TABLE_USER + "." +
                SQL_COLUMN_USER_ID +
                " INNER JOIN " + SQL_TABLE_SUBJECT + " ON " + SQL_TABLE_SUBJECT + "." + SQL_COLUMN_SUBJECT_ID +
                "= " + SQL_TABLE_LECTURE + "." + SQL_COLUMN_SUBJECT_ID + " \n " +
                " INNER JOIN " + SQL_TABLE_ROOM + " ON " + SQL_TABLE_LECTURE + "." + SQL_COLUMN_ROOM_ID + "" +
                "= " + SQL_TABLE_ROOM + "." + SQL_COLUMN_ROOM_ID +
                " WHERE lecture.room_id = " + room.getId() +
                " AND lecture.day_of_week = '" + day.toString().toLowerCase() + "'" +
                " AND start_time <= " + toSqlTime(current) +
                " AND end_time >= " + toSqlTime(current);
        //TODO
        
        int count = 0;
        
        try (ResultSet rs = connector.executeQuery(sql)) {
            
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //ignored
        }
        return (count != 0);
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
                room.getId()
        };
        try (ResultSet results = connector.executeQuery(sql, values)) {
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
        String sql = "INSERT INTO room_image (image_url, room_id) VALUES ('" + imageURL + "', " + room.getId() + ")";
        try {
            connector.executeUpdate(sql);
        } catch (SQLException e) {
            //doing nothing
        }
    }

}
