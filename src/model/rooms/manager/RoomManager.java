package model.rooms.manager;

import java.util.*;

import model.rooms.Room;
import model.rooms.RoomSearchQuery;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The room manager DAO.
 * Responsible for pulling and pushing information related to user accounts
 * from and into the database.
 */
public interface RoomManager {

    /**
     * Finds and returns all the rooms that match passed parameters.
     *
     * @param query a container of all search parameters
     * @return a list of all the rooms that match parameters
     */
    List<Room> findRooms(RoomSearchQuery query);

    /**
     * Adds a room to the database.
     *
     * @param room new room to be added
     */
    void addRoom(Room room);


    /**
     * If exists, removes room from database.
     *
     * @param room a room to be deleted
     */
    void removeRoom(Room room);


    /**
     * Returns a list of URL-s of all the images of given room.
     *
     * @return a list of <code>String</code> URL-s
     */
    List<String> getAllImagesOf(Room room);
}
