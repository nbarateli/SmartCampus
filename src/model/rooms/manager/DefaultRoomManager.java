package model.rooms.manager;

import model.DBConnector;
import model.SQLConstants;
import model.rooms.Room;
import model.rooms.RoomSearchQuery;

import java.sql.*;
import java.util.*;

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
    public List<Room> findRooms(RoomSearchQuery query) {
        List<Room> rooms = new ArrayList<>();
        try {
            ResultSet matches = DBConnector.executeQuery(query.generateQuery());
            while (matches.next()) {
                int id = matches.getInt(SQLConstants.SQL_COLUMN_ROOM_ID);
                int capacity = matches.getInt(SQLConstants.SQL_COLUMN_ROOM_CAPACITY);
                int floor = matches.getInt(SQLConstants.SQL_COLUMN_ROOM_FLOOR);
                String name = matches.getString(SQLConstants.SQL_COLUMN_ROOM_NAME);
                Room.RoomType roomType =
                        Room.RoomType.valueOf(matches.getString(SQLConstants.SQL_COLUMN_ROOM_TYPE));
                Room.SeatType seatType =
                        Room.SeatType.valueOf(matches.getString(SQLConstants.SQL_COLUMN_ROOM_SEAT_TYPE));
                boolean available = matches.getBoolean(SQLConstants.SQL_COLUMN_ROOM_AVAILABLE);

                Room room = new Room(id, capacity, name, roomType, seatType, available, floor);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    @Override
    public void addRoom(Room room) {
        String insertQuery = "insert into " + SQLConstants.SQL_TABLE_ROOM + " (" +
                SQLConstants.SQL_COLUMN_ROOM_NAME + ", " + SQLConstants.SQL_COLUMN_ROOM_FLOOR + ", " +
                SQLConstants.SQL_COLUMN_ROOM_TYPE + ", " + SQLConstants.SQL_COLUMN_ROOM_CAPACITY +
                ", " + SQLConstants.SQL_COLUMN_ROOM_AVAILABLE + ", " +
                SQLConstants.SQL_COLUMN_ROOM_SEAT_TYPE + ") values (" +
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
    public void removeRoom(Room room) {
        if (roomExists(room)) {
            String deleteQuery = "delete from " + SQLConstants.SQL_TABLE_ROOM + " where room_id = " +
                    room.getRoomID();
            try {
                DBConnector.executeUpdate(deleteQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns whether the given room exists in database
     */
    private boolean roomExists(Room room) {
        return !findRooms(new RoomSearchQuery(room.getRoomName(), room.getFloor(), room.getCapacity(),
                room.getCapacity(), room.getRoomType(), room.isAvailableForStudents(),
                room.getSeatType(), true)).isEmpty() || !findRooms(new RoomSearchQuery(room.getRoomName(),
                room.getFloor(), room.getCapacity(), room.getCapacity(), room.getRoomType(),
                room.isAvailableForStudents(), room.getSeatType(), false)).isEmpty();
    }
}
