package serve.rooms;

import model.rooms.RoomManager;
import serve.managers.ManagerFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static misc.WebConstants.MANAGER_FACTORY;

@WebServlet(name = "RoomNameLister", urlPatterns = {"/rooms/allroomnames"})
public class RoomNameLister extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ManagerFactory managerFactory = (ManagerFactory)
                request.getServletContext().getAttribute(MANAGER_FACTORY);
        RoomManager roomManager = managerFactory.getRoomManager();
        List<String> roomNames = roomManager.getAllRoomNames();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String roomName : roomNames) {
            arrayBuilder.add(roomName);
        }
        JsonWriter writer = Json.createWriter(response.getWriter());
        writer.writeArray(arrayBuilder.build());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
