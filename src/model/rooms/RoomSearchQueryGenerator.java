package model.rooms;

import model.campus.CampusSearchQuery;
import model.campus.CampusSearchQueryGenerator;

import java.util.ArrayList;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * A class responsible for generating valid SQL queries for searching rooms.
 */
public class RoomSearchQueryGenerator implements CampusSearchQueryGenerator<Room> {

    private String name;
    private Integer floor;
    private Integer capacityFrom;
    private Integer capacityTo;
    private Room.RoomType roomType;
    private Room.SeatType seatType;
    private boolean availableForBooking;
    private boolean hasProblems;

    public RoomSearchQueryGenerator(String name, Integer floor, Integer capacityFrom, Integer capacityTo,
                                    Room.RoomType roomType, boolean availableForBooking,
                                    Room.SeatType seatType, boolean hasProblems) {
        this.name = name;
        this.floor = floor;
        this.capacityFrom = capacityFrom;
        this.capacityTo = capacityTo;
        this.roomType = roomType;
        this.seatType = seatType;
        this.availableForBooking = availableForBooking;
        this.hasProblems = hasProblems;
    }

    /**
     * Default constructor:
     */
    public RoomSearchQueryGenerator() {
        this(null, null, null, null,
                null, false, null, true);
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

    public boolean hasProblems() {
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
    public CampusSearchQuery generateQuery() {
        List<Object> values = new ArrayList<>();
        String sql = hasNonNullFields() ? String.format(
                "SELECT * FROM  %s\nWHERE\n%s \nAND %s \nAND %s\nAND %s\nAND %s\nAND %s\nAND %s\n%s",
                SQL_TABLE_ROOM,
                generateLikeQuery(name, SQL_COLUMN_ROOM_NAME, values),
                generateEqualQuery(floor, SQL_COLUMN_ROOM_FLOOR, values),
                generateEqualsOrQuery(capacityFrom, SQL_COLUMN_ROOM_CAPACITY, values, true),
                generateEqualsOrQuery(capacityTo, SQL_COLUMN_ROOM_CAPACITY, values, false),
                generateLikeQuery((roomTypeToString(roomType).equals("") ? null : roomTypeToString(roomType)),
                        SQL_COLUMN_ROOM_TYPE, values),
                generateLikeQuery((seatTypeToString(seatType).equals("") ? null : seatTypeToString(seatType)),
                        SQL_COLUMN_ROOM_SEAT_TYPE, values),
                generateBooleanQuery(availableForBooking ? true : null,
                        SQL_COLUMN_ROOM_AVAILABLE, values),
                hasProblemsQuery()) : " SELECT * from " + SQL_TABLE_ROOM;
        //TODO
        return new CampusSearchQuery(sql, asArray(values));

    }

    /**
     * Returns a segment of SQL query that checks whether the given room has any problems.
     */
    private String hasProblemsQuery() {

        return hasProblems ? "" : String.format(
                "AND !(%s.%s IN (SELECT %s.%s\n" +
                        "                         FROM %s))",
                SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID, SQL_TABLE_ROOM_PROBLEM,
                SQL_COLUMN_ROOM_PROBLEM_ROOM, SQL_TABLE_ROOM_PROBLEM);
    }

    /**
     * If the passed field isn't null, generates "like" query for it.
     *
     * @param field  value being searched
     * @param column name of the column
     * @return an empty string or a valid segment of SQL query.
     */
    private String assertAndGetEqualQuery(String field, String column) {

        return field == null ? "" : " " + column + " like \'%" + field + "%\'";
    }

    /**
     * Returns whether this object has any variable in non-default state.
     */
    private boolean hasNonNullFields() {

        return availableForBooking || !hasProblems || name != null || floor != null ||
                capacityFrom != null || capacityTo != null ||
                roomType != null || seatType != null;

    }


}
