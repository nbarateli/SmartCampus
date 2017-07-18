package serve.rooms;

import model.rooms.Room;
import model.rooms.RoomManager;
import serve.managers.ManagerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static misc.WebConstants.*;

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
        RoomManager manager = ((ManagerFactory) request.getServletContext()
                .getAttribute(MANAGER_FACTORY)).getRoomManager();
        String name = request.getParameter("room_name");
        Room room = manager.getRoomByName(name);
        if (room != null) {
            manager.remove(room.getId());
            response.getWriter().print(SUCCESS);
        } else {
            response.getWriter().print(FAILED);
        }
    }
}
