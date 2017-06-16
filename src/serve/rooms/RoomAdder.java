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
    
    /*
     * checks if given string is a positive number
     */
    private boolean numberStringIsValid(String s) {
        try {
            int numValue = Integer.parseInt(s);
            //capacity and floor can't be negative
            //their input types are "number" but some old browsers don't have support for
            //that and we don't won't any exceptions
            if(numValue <= 0)
                return false;
        } catch (NumberFormatException e) {
            return false; //the text wasn't a number
        }
        
        return true;
    }
    
    /*
     * checks if room with given name already exists
     */
    private boolean roomExists(String name, RoomManager manager) {
        RoomSearchQuery query = new RoomSearchQuery();
        query.setName(name);
        if(manager.find(query).isEmpty())
            return false;
        return true;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RoomManager manager = 
                (RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER);
        String name = request.getParameter("room_name");
        String floorStr = request.getParameter("room_floor");
        String capacityStr = request.getParameter("capacity");
        RoomType roomType = Utils.toRoomType(request.getParameter("room_type"));
        SeatType seatType = Utils.toSeatType(request.getParameter("seat_type").toUpperCase());
        boolean available = (request.getParameter("can_be_booked") != null);
        
        //add to database only if every field has valid input
        if (!name.equals("") && numberStringIsValid(floorStr) 
                && numberStringIsValid(capacityStr) && !roomExists(name, manager)) {
            int floor = Integer.parseInt(floorStr);
            int capacity = Integer.parseInt(capacityStr);

            Room room = new Room(-1, capacity, name, roomType, seatType, 
                    available, floor);
            manager.add(room);
         
        }
          
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
