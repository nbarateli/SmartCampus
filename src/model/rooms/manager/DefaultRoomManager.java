package model.rooms.manager;

import model.database.DBConnector;

import static model.database.SQLConstants.*;

import model.campus.CampusSearchQuery;
import model.rooms.Room;

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

    /***/
    public static RoomManager getInstance() {
        return instance == null ? instance = new DefaultRoomManager() : instance;
    }

    private DefaultRoomManager() {

    }

    @Override
    public List<Room> find(CampusSearchQuery query) {
        List<Room> rooms = new ArrayList<>();
        Connection con = null;
        try {
            ResultSet matches = DBConnector.executeQuery(query.generateQuery());
            con = matches.getStatement().getConnection();
            while (matches.next()) {
                int id = matches.getInt(SQL_COLUMN_ROOM_ID);
                int capacity = matches.getInt(SQL_COLUMN_ROOM_CAPACITY);
                int floor = matches.getInt(SQL_COLUMN_ROOM_FLOOR);
                String name = matches.getString(SQL_COLUMN_ROOM_NAME);
                Room.RoomType roomType =
                        toRoomType(matches.getString(SQL_COLUMN_ROOM_TYPE));
                Room.SeatType seatType =
                        toSeatType(matches.getString(SQL_COLUMN_ROOM_SEAT_TYPE));
                boolean available = matches.getBoolean(SQL_COLUMN_ROOM_AVAILABLE);

                Room room = new Room(id, capacity, name, roomType, seatType, available, floor);
                rooms.add(room);
            }
        } catch (SQLException e) {
            //doing nothing
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException e) {
                //doing nothing
            }
        }

        return rooms;
    }

    @Override
    public void add(Room room) {
        String insertQuery = "insert into " + SQL_TABLE_ROOM + " (" +
                SQL_COLUMN_ROOM_NAME + ", " + SQL_COLUMN_ROOM_FLOOR + ", " +
                SQL_COLUMN_ROOM_TYPE + ", " + SQL_COLUMN_ROOM_CAPACITY +
                ", " + SQL_COLUMN_ROOM_AVAILABLE + ", " +
                SQL_COLUMN_ROOM_SEAT_TYPE + ") values (" +
                room.getRoomName() + ", " + room.getFloor() + ", " + room.getRoomType() + ", " +
                room.getRoomName() + ", " + room.getCapacity() + ", " + room.isAvailableForStudents() +
                ", " + room.getSeatType() + ") ";
        try {
            DBConnector.executeUpdate(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Room room) {

        String deleteQuery = "delete from " + SQL_TABLE_ROOM + " where room_id = " + room.getID();
        try {
            DBConnector.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> getAllImagesOf(Room room) {
        List<String> images = new LinkedList<>();
        String sql = "SELECT image_url FROM  room_image WHERE room_image.room_id = room." + room.getID();
        Connection con = null;
        try {
            ResultSet rs = DBConnector.executeQuery(sql);
            con = rs.getStatement().getConnection();
            while (rs.next()) {
                images.add(rs.getString(SQL_COLUMN_ROOM_IMAGE_URL));
            }
        } catch (SQLException e) {
            //doing nothing
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return images;
    }

}
