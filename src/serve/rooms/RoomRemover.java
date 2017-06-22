package serve.rooms;

import misc.ModelConstants;
import model.rooms.Room;
import model.rooms.RoomSearchQuery;
import model.rooms.manager.RoomManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static misc.WebConstants.FAILED;
import static misc.WebConstants.ROOM_MANAGER;
import static misc.WebConstants.SUCCESS;

/**
 * Servlet implementation class RoomRemover
 */
@WebServlet(urlPatterns = {"/rooms/removeroom"})
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
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RoomManager manager =
                (RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER);
        String name = request.getParameter("room_name");
        RoomSearchQuery query = new RoomSearchQuery();
        query.setName(name);
        List<Room> foundRooms = manager.find(query);
        if(foundRooms.size() != 0) {
            manager.remove(foundRooms.get(0).getID());
            response.getWriter().println(SUCCESS);
        } else {
            response.getWriter().println(FAILED);
        }
    }
}
