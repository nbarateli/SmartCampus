package serve.rooms;

import misc.ModelConstants;
import model.rooms.RoomSearchQuery;
import model.rooms.manager.RoomManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static misc.WebConstants.ROOM_MANAGER;

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
        manager.remove(manager.find(query).size() == 0 ?
                ModelConstants.SENTINEL_INT : manager.find(query).get(0).getID());
        response.sendRedirect("/data/addingData.jsp");
        doGet(request, response);
    }
}
