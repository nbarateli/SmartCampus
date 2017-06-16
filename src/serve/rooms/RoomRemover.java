package serve.rooms;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.rooms.Room;
import model.rooms.RoomSearchQuery;
import model.rooms.manager.RoomManager;

import static misc.WebConstants.ROOM_MANAGER;

/**
 * Servlet implementation class RoomRemover
 */
@WebServlet("/RoomRemover")
public class RoomRemover extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomRemover() {
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
	    RoomSearchQuery query = new RoomSearchQuery();
	    query.setName(name);
	    manager.remove(manager.find(query).get(0));
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
