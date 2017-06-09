package model.rooms;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The SmartCampus Room ADT (Immutable)
 */
public class Room {
    private final int roomID, capacity;
    private final String roomName;
    private final RoomType roomType;
    private final SeatType seatType;
    private final boolean isAvailableForStudents;

    /**
     * Constructs a new Room object.
     */
    public Room(int roomID, int capacity, String roomName, RoomType roomType, SeatType seatType, boolean isAvailableForStudents) {
        this.roomID = roomID;
        this.capacity = capacity;
        this.roomName = roomName;
        this.roomType = roomType;
        this.seatType = seatType;
        this.isAvailableForStudents = isAvailableForStudents;
    }

    public boolean isAvailableForStudents() {
        return isAvailableForStudents;
    }

    public int getRoomID() {
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

    public enum SeatType {
        DESKS, WOODEN_CHAIR, PLASTIC_CHAIR, COMPUTERS, TABLES
    }
}
