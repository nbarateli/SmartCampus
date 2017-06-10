package misc;

import model.rooms.*;

import static model.rooms.Room.RoomType.*;
import static model.rooms.Room.SeatType.*;

public class Utils {
    private Utils() {

    }


    /**
     * Returns a Georgian string representation of the given <code>RoomType</code>
     */
    public static String roomTypeToString(Room.RoomType roomType) {
        switch (roomType) {
            case UTILITY:
                return "სხვა";
            case AUDITORIUM:
                return "აუდიტორია";
        }
        return "";
    }

    /**
     * Returns a Georgian string representation of the given <code>SeatType</code>
     */
    public static String seatTypeToString(Room.SeatType seatType) {
        switch (seatType) {
            case DESKS:
                return "სკამები და მერხები";
            case TABLES:
                return "მაგიდები";
            case COMPUTERS:
                return "კომპიუტერები";
            case WOODEN_CHAIR:
                return "სკამ-მერხები (ხის)";

            case PLASTIC_CHAIR:
                return "სკამ-მერხები (პლასტმასის)";

        }
        return "";
    }

    /**
     * Returns a corresponding <code>RoomType</code> object  that has a value of the passed string
     *
     * @param s a string representation of te room type caller is searching for
     * @return corresponding <code>RoomType</code>
     */
    public static Room.RoomType toRoomType(String s) {

        switch (s.toLowerCase()) {
            case "auditorium":
                return AUDITORIUM;
            case "utility":
                return UTILITY;
            case "any":
                return null;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a corresponding <code>SeatType</code> object  that has a value of the passed string
     *
     * @param s a string representation of te seat type caller is searching for
     * @return corresponding <code>SeatType</code>
     */
    public static Room.SeatType toSeatType(String s) {

        switch (s.toLowerCase()) {
            case "desks":
                return DESKS;
            case "wooden_chair":
                return WOODEN_CHAIR;
            case "plastic_chair":
                return PLASTIC_CHAIR;
            case "computers":
                return COMPUTERS;
            case "tables":
                return TABLES;
            case "any":
                return null;
        }
        throw new IllegalArgumentException();

    }
}
