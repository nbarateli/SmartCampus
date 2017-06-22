package serve.rooms;

import model.rooms.Room;
import model.rooms.manager.RoomManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static misc.Utils.toRoomType;
import static misc.Utils.toSeatType;
import static misc.WebConstants.ROOM_MANAGER;

/**
 * Servlet implementation class RoomAdder
 */
@WebServlet(name = "RoomAdder", urlPatterns = "/rooms/addroom")
public class RoomAdder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomAdder() {
        super();
        // TODO Auto-generated constructor stub
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RoomManager manager = (RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER);
        try {
            String name = request.getParameter("room_name");
            int floor = Integer.parseInt(request.getParameter("room_floor"));
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            Room.RoomType roomType = toRoomType(request.getParameter("room_type"));
            Room.SeatType seatType = toSeatType(request.getParameter("seat_type"));
            boolean canBeBooked = "true".equalsIgnoreCase(request.getParameter("can_be_booked"));
            Room newRoom = new Room(-1, capacity, name, roomType, seatType, canBeBooked, floor);
            manager.add(newRoom);
            response.getWriter().print("success");
        } catch (Exception e) {
            response.getWriter().print("failure");
        }

    }

}
