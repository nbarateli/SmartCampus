package model.rooms;


import java.sql.*;
import java.util.*;

import model.DBConnector;
import model.SQLConstants;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The room manager DAO.
 * Responsible for pulling and pushing information related to user accounts
 * from and into the database.
 */
public class RoomManager {

    /**
     * Finds and returns all the rooms that match passed parameters.
     *
     * @param query a container of all search parameters
     * @return a list of all the rooms that match parameters
     */
    public List<Room> findRooms(RoomSearchQuery query) {
        List<Room> rooms = new ArrayList<Room>();
        try {
            ResultSet matches = DBConnector.executeQuery(query.generateQuery());
            while(matches.next()) {
                int id = matches.getInt(SQLConstants.SQL_COLUMN_ROOM_ID);
                int capacity = matches.getInt(SQLConstants.SQL_COLUMN_ROOM_CAPACITY);
                int floor = matches.getInt(SQLConstants.SQL_COLUMN_ROOM_FLOOR);
                String name = matches.getString(SQLConstants.SQL_COLUMN_ROOM_NAME);
                RoomType roomType = 
                        RoomType.valueOf(matches.getString(SQLConstants.SQL_COLUMN_ROOM_TYPE));
                SeatType seatType = 
                        SeatType.valueOf(matches.getString(SQLConstants.SQL_COLUMN_ROOM_SEAT_TYPE));
                boolean available = matches.getBoolean(SQLConstants.SQL_COLUMN_ROOM_AVAILABLE);
                
                Room room = new Room(id, capacity, name, roomType, seatType, available, floor);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rooms;   
    }

    /**
     * Adds a room to the database.
     *
     * @param room new room to be added
     */
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
    
    private boolean roomExists(Room room) {
        return !findRooms(new RoomSearchQuery(room.getRoomName(), room.getFloor(), room.getCapacity(), 
                room.getCapacity(), room.getRoomType(), room.isAvailableForStudents(), 
                room.getSeatType(), true)).isEmpty() || !findRooms(new RoomSearchQuery(room.getRoomName(), 
                room.getFloor(), room.getCapacity(), room.getCapacity(), room.getRoomType(), 
                room.isAvailableForStudents(), room.getSeatType(), false)).isEmpty();
    }
    
    /**
     * If exists, removes room from database.
     *
     * @param room a room to be deleted
     */
    public void removeRoom(Room room) {
        if(roomExists(room)) {
            String deleteQuery = "delete from " +  SQLConstants.SQL_TABLE_ROOM + " where room_id = " +
                    room.getRoomID();
            try {
                DBConnector.executeUpdate(deleteQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }   
    }
}
