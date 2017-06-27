package model.rooms;

import model.campus.CampusManager;
import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;

import static misc.Utils.toSqlTime;
import static misc.Utils.toWeekDay;
import static model.database.SQLConstants.SQL_COLUMN_LECTURE_LECTURER;
import static model.database.SQLConstants.SQL_COLUMN_ROOM_ID;
import static model.database.SQLConstants.SQL_COLUMN_SUBJECT_ID;
import static model.database.SQLConstants.SQL_COLUMN_USER_ID;
import static model.database.SQLConstants.SQL_TABLE_LECTURE;
import static model.database.SQLConstants.SQL_TABLE_ROOM;
import static model.database.SQLConstants.SQL_TABLE_SUBJECT;
import static model.database.SQLConstants.SQL_TABLE_USER;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
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
     * at given day and start within the range of start and end times.
     *
     * @param room  a room where the lectures take place
     * @param day   the day on which the lectures take place
     * @param start start of the time range when the lectures start
     * @param end   end of the time range when the lectures start
     */
    List<Lecture> findAllLecturesAt(Room room, Lecture.WeekDay day, Time start, Time end);
    
    /**
     * Tells if there is a lecture that takes place in the room
     * at given date and on given current time.
     *
     * @param room  a room we are interested in
     * @param date  the date we are interested in
     * @param current time we are interested in
     */
    boolean isLectureAt(Room room, Date date, Time current);

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
