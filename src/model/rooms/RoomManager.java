package model.rooms;

import model.campus.CampusManager;
import model.lectures.Lecture;

import java.sql.Time;
import java.util.List;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The room manager DAO.
 * Responsible for pulling and pushing information related to user accounts
 * from and into the database.
 */
public interface RoomManager extends CampusManager<Room, RoomSearchQueryGenerator> {

    /**
     * Returns a list of URL-s of all the images of given room.
     *
     * @return a list of <code>String</code> URL-s
     */
    List<String> getAllImagesOf(Room room);

    /**
     * Returns the list of all lectures that take place in the room
     *
     * @param room a room where the lectures take place
     */
    List<Lecture> findAllLecturesAt(Room room);

    /**
     * Returns a list of all the lectures that take place in the room
     * at given day.
     *
     * @param room a room where the lectures take place
     * @param day  the day on which the lectures take place
     */
    List<Lecture> findAllLecturesAt(Room room, Lecture.WeekDay day);

    /**
     * Returns a list of all the lectures that take place in the room
     * at given day and start within he range of start and end times.
     *
     * @param room  a room where the lectures take place
     * @param day   the day on which the lectures take place
     * @param start start of the time range when the lectures start
     * @param end   end of the time range when the lectures start
     */
    List<Lecture> findAllLecturesAt(Room room, Lecture.WeekDay day, Time start, Time end);

    /**
     * returns room with given id from the database
     *
     * @param id id of the room we're searching for
     */
    Room getRoomById(int id);

    /**
     * returns room with given name from the database
     *
     * @param roomName the name of the room to be found
     */
    Room getRoomByName(String roomName);

    /**
     * Returns the list of all the infrastructural problems this room currently has.
     *
     * @return a <code>List</code> of <code>RoomProblem</code> s
     */
    List<RoomProblem> findAllProblemsOf(Room room);

    /**
     * Adds new image for the room object in the database
     *
     * @param room     a room to which said image belongs to
     * @param imageURL an URL to the image
     */
    void addImage(Room room, String imageURL);

}