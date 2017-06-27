package serve.rooms;

import model.rooms.Room;
import model.rooms.RoomManager;
import model.rooms.RoomSearchQueryGenerator;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static misc.Utils.toRoomType;
import static misc.Utils.toSeatType;
import static misc.WebConstants.*;

/**
 * The sole function of this class is to return a JSON array of
 * <code>{@link model.rooms.Room}</code> objects that match passed parameters
 */
@WebServlet(name = "RoomFinder", urlPatterns = {"/rooms/findrooms"})
public class RoomFinder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        RoomManager manager = ((RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER));

        List<Room> rooms = manager.find(getQuery(request));
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Room room : rooms) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add(JSON_ROOM_ID, room.getId());
            objectBuilder.add(JSON_ROOM_NAME, room.getRoomName());
            objectBuilder.add(JSON_ROOM_FLOOR, room.getFloor());
            objectBuilder.add(JSON_ROOM_CAPACITY, room.getCapacity());
            objectBuilder.add(JSON_ROOM_TYPE, room.getRoomType().name());
            objectBuilder.add(JSON_ROOM_SEAT_TYPE, room.getSeatType().name());
            objectBuilder.add(JSON_ROOM_AVAILABLE, room.isAvailableForStudents());
            List<String> images = manager.getAllImagesOf(room);
            objectBuilder.add(JSON_ROOM_MAIN_IMAGE, images.size() > 0 ? images.get(0) : NO_IMAGE);
            arrayBuilder.add(objectBuilder.build());
        }
        JsonArray array = arrayBuilder.build();
        JsonWriter writer = Json.createWriter(response.getWriter());
        writer.writeArray(array);

    }


    private Integer nullIfNoLength(String s) {
        return s == null || s.length() < 1 ? null : Integer.valueOf(s) < 0 ? 0 : Integer.valueOf(s);
    }

    private RoomSearchQueryGenerator getQuery(HttpServletRequest request) {
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        if (request.getParameter("search") == null) return query;
        String name = request.getParameter("room_name");
        String floor = request.getParameter("room_floor");
        String roomType = request.getParameter("room_type");
        String seatType = request.getParameter("seat_type");
        boolean canBeBooked = request.getParameter("can_be_booked") != null;
        query.setName(name.length() > 0 ? name : null);
        query.setFloor(nullIfNoLength(floor));
        query.setCapacityFrom(nullIfNoLength(request.getParameter("capacity_from")));
        query.setCapacityTo(nullIfNoLength(request.getParameter("capacity_to")));
        query.setRoomType(roomType.length() > 0 ? toRoomType(roomType) : null);
        query.setSeatType(seatType.length() > 0 ? toSeatType(seatType) : null);
        query.setAvailableForBooking(canBeBooked);
        return query;
    }

}