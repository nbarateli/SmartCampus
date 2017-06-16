package model.rooms;

import model.campus.CampusObject;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The SmartCampus Room ADT (Immutable)
 */
public class Room implements CampusObject {
    private final int roomID;
    private final int capacity;
    private final String roomName;
    private final RoomType roomType;
    private final SeatType seatType;
    private final boolean isAvailableForStudents;
    private final int floor;

    /**
     * Constructs a new Room object.
     */
    public Room(int roomID, int capacity, String roomName, RoomType roomType,
                SeatType seatType, boolean isAvailableForStudents, int floor) {
        this.roomID = roomID;
        this.capacity = capacity;
        this.roomName = roomName;
        this.roomType = roomType;
        this.seatType = seatType;
        this.isAvailableForStudents = isAvailableForStudents;
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public boolean isAvailableForStudents() {
        return isAvailableForStudents;
    }

    @Override
    public int getID() {
        return roomID;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getRoomName() {
        return roomName;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public enum RoomType {
        AUDITORIUM, UTILITY
    }

    @Override
    public String toString() {
        return getRoomName();
    }

    public enum SeatType {
        DESKS, WOODEN_CHAIR, PLASTIC_CHAIR, COMPUTERS, TABLES
    }
}
