package serve.rooms;

import static misc.WebConstants.ROOM_MANAGER;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misc.Utils;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import model.rooms.RoomSearchQuery;
import model.rooms.manager.RoomManager;

/**
 * Servlet implementation class RoomAdder
 */
@WebServlet("/RoomAdder")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RoomManager manager = 
                (RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER);
        String name = request.getParameter("room_name");
        int floor = Integer.parseInt(request.getParameter("room_floor"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        RoomType roomType = Utils.toRoomType(request.getParameter("room_type"));
        SeatType seatType = Utils.toSeatType(request.getParameter("seat_type").toUpperCase());
        boolean available = (request.getParameter("can_be_booked") != null);
        
        Room room = new Room(-1, capacity, name, roomType, seatType, 
                available, floor);
        manager.add(room);
        
        response.sendRedirect("data/addingData.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
