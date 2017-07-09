package serve.rooms;

import model.rooms.Room;
import model.rooms.RoomManager;
import serve.managers.ManagerFactory;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static misc.WebConstants.*;

/**
 * Created by Niko on 09.07.2017.
 * <p>
 * A servlet that is responsible for handling image uploads on rooms.
 * Takes two parameters: room_id - which is the int id of the room in the database,
 * and image_url that is the url of the image that's going to be added.
 */
@WebServlet(name = "RoomImageAdder", urlPatterns = {"/rooms/add_image"})
public class RoomImageAdder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String roomId = request.getParameter("room_id");
        String imageURL = request.getParameter("image_url");
        ManagerFactory factory = (ManagerFactory) getServletContext().getAttribute(MANAGER_FACTORY);
        RoomManager manager = factory.getRoomManager();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        try {
            Room room = manager.getRoomById(Integer.parseInt(roomId));
            manager.addImage(room, imageURL);
            objectBuilder.add(JSON_ERROR, false);
        } catch (NumberFormatException e) {
            objectBuilder.add(JSON_ERROR, true);
            objectBuilder.add(JSON_ERROR_REASON, JSON_ROOM_ERROR_BAD_PARAM);
        } catch (Exception e) {
            objectBuilder.add(JSON_ERROR, true);
            objectBuilder.add(JSON_ERROR_REASON, JSON_ROOM_ERROR_NOT_FOUND);
        }
        Json.createWriter(response.getWriter()).write(objectBuilder.build());
    }


}
