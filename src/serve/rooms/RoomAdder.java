package serve.rooms;

import model.rooms.Room;
import model.rooms.RoomManager;
import serve.managers.ManagerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static misc.ModelConstants.SENTINEL_INT;
import static misc.Utils.toRoomType;
import static misc.Utils.toSeatType;
import static misc.WebConstants.*;

/**
 * Servlet implementation class RoomAdder
 */
@WebServlet(name = "RoomAdder", urlPatterns = "/rooms/addroom")
public class RoomAdder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        RoomManager manager = ((ManagerFactory) request.getServletContext().
                getAttribute(MANAGER_FACTORY)).getRoomManager();

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        String name = request.getParameter("room_name");
        Integer floor = null;
        Integer capacity = null;
        try {
            floor = Integer.parseInt(request.getParameter("room_floor"));
        } catch (NumberFormatException e) {
            objectBuilder.add(JSON_ROOM_FLOOR, "შეიყვანეთ ოთახის სართული!");
        }
        try {
            capacity = Integer.parseInt(request.getParameter("capacity"));
        } catch (NumberFormatException e) {
            objectBuilder.add(JSON_ROOM_CAPACITY, "შეიყვანეთ ადგილების რაოდენობა!");
        }
            Room.RoomType roomType = toRoomType(request.getParameter("room_type"));
            Room.SeatType seatType = toSeatType(request.getParameter("seat_type"));
        boolean canBeBooked = ("true".equals(request.getParameter("can_be_booked")));
        if (isValid(manager, name, roomType, seatType, floor, capacity, objectBuilder)) {
            manager.add(new Room(SENTINEL_INT, capacity, name, roomType, seatType, canBeBooked, floor));
            response.getWriter().print(SUCCESS);
        }
        JsonObject obj = objectBuilder.build();
        if (!obj.isEmpty()) {
            JsonWriter jwriter = Json.createWriter(response.getWriter());
            jwriter.writeObject(obj);
        }
    }

    private boolean isValid(RoomManager manager, String name, Room.RoomType roomType,
                            Room.SeatType seatType, Integer floor, Integer capacity, JsonObjectBuilder builder) {
        boolean result = true;
        if (manager.getRoomByName(name) != null) {
            builder.add(JSON_ROOM_NAME, "ასეთი ოთახი უკვე არსებობს!");
            result = false;
        } else if (name == "") {
            builder.add(JSON_ROOM_NAME, "გთხოვთ შეიყვანოთ ოთახის სახელი!");
            result = false;
        }
        if (floor == null) result = false;
        if (capacity == null) result = false;
        if (roomType == null) result = false;
        if (seatType == null) result = false;
        return result;
    }
}
