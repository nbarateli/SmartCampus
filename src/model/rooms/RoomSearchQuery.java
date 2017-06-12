package model.rooms;

import static model.SQLConstants.*;

import model.Interfaces.SearchQuery;
import model.accounts.User;
import model.lecture.Lecture;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * A class responsible for generating valid SQL queries for searching rooms.
 */
public class RoomSearchQuery implements SearchQuery{

    private String name;
    private Integer floor;
    private Integer capacityFrom;
    private Integer capacityTo;
    private Room.RoomType roomType;
    private Room.SeatType seatType;
    private boolean availableForBooking;
    private boolean hasProblems;
    private User lecturer;
    private Lecture lecture;
    public RoomSearchQuery(String name, Integer floor, Integer capacityFrom, Integer capacityTo,
                           Room.RoomType roomType, boolean availableForBooking,
                           Room.SeatType seatType, boolean hasProblems,
                           User lecturer, Lecture lecture) {
        this.name = name;
        this.floor = floor;
        this.capacityFrom = capacityFrom;
        this.capacityTo = capacityTo;
        this.roomType = roomType;
        this.seatType = seatType;
        this.availableForBooking = availableForBooking;
        this.hasProblems = hasProblems;
        this.lecturer = lecturer;
    }

    /**
     * Default constructor:
     */
    public RoomSearchQuery() {
        this(null, null, null, null,
                null, false, null, true, null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCapacityFrom() {
        return capacityFrom;
    }

    public void setCapacityFrom(Integer capacityFrom) {
        if (capacityFrom != null) {
            if ((capacityTo != null && capacityFrom > capacityTo) || capacityFrom < 0) {
                throw new IllegalArgumentException();
            }
        }
        this.capacityFrom = capacityFrom;
    }

    public Integer getCapacityTo() {
        return capacityTo;
    }

    public void setCapacityTo(Integer capacityTo) {
        if (capacityTo != null) {
            if ((capacityFrom != null && capacityFrom > capacityTo) || capacityTo < 0) {
                throw new IllegalArgumentException();
            }
        }
        this.capacityTo = capacityTo;
    }

    public Room.RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(Room.RoomType roomType) {
        this.roomType = roomType;
    }

    public Room.SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(Room.SeatType seatType) {
        this.seatType = seatType;
    }

    public boolean isHasProblems() {
        return hasProblems;
    }

    public void setHasProblems(boolean hasProblems) {
        this.hasProblems = hasProblems;
    }

    public boolean isAvailableForBooking() {
        return availableForBooking;
    }

    public void setAvailableForBooking(boolean availableForBooking) {
        this.availableForBooking = availableForBooking;
    }

    /**
     * Generates and returns corresponding SQL query
     *
     * @return a valid sql query.
     */
    public String generateQuery() {
        return hasNonNullFields() ? String.format(
                "SELECT * FROM room  WHERE %s%s%s%s%s%s%s%s%s%s%s;", assertAndGetEqualQuery(name, SQL_COLUMN_ROOM_NAME),
                name == null ? "" : " \nAND ",
                assertAndGetEqualQuery(floor == null ? null : floor.toString(), SQL_COLUMN_ROOM_FLOOR),
                floor == null ? "" : " AND ",
                SQL_COLUMN_ROOM_CAPACITY, capacityFrom == null ? " > 0 " : " >= " + capacityFrom + "\n",
                capacityTo != null ? " AND " + SQL_COLUMN_ROOM_CAPACITY + " <= " + capacityTo + "\n" : "",
                roomType != null ? " AND " +
                        assertAndGetEqualQuery(roomType.toString().toLowerCase(), SQL_COLUMN_ROOM_TYPE) + "\n" : "",
                seatType != null ? " AND " +
                        assertAndGetEqualQuery(seatType.toString(), SQL_COLUMN_ROOM_SEAT_TYPE) + "\n" : "",
                availableForBooking ? " AND " + SQL_COLUMN_ROOM_AVAILABLE + " = TRUE \n" : "", hasProblemsQuery())
                :
                "SELECT * FROM room  ";

    }

    private String hasProblemsQuery() {


        return hasProblems ? "" : " AND COUNT(SELECT * FROM room_problem\n" +
                " WHERE room_problem.room_id = ) < 1";
    }


    private String assertAndGetEqualQuery(String field, String rowName) {

        return field == null ? "" : " " + rowName + " like \'%" + field + "%\'";
    }

    private boolean hasNonNullFields() {

        return availableForBooking || !hasProblems || name != null || floor != null ||
                capacityFrom != null || capacityTo != null ||
                roomType != null || seatType != null || 
                lecture != null || lecturer !=null;

    }

    /**
     * @return the lecturer
     */
    public User getLecturer() {
        return lecturer;
    }

    /**
     * @param lecturer the lecturer to set
     */
    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    /**
     * @return the lecture
     */
    public Lecture getLecture() {
        return lecture;
    }

    /**
     * @param lecture the lecture to set
     */
    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
