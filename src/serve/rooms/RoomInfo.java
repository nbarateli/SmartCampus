package serve.rooms;

import model.bookings.Booking;
import model.rooms.Room;
import model.rooms.RoomManager;
import model.rooms.RoomProblem;
import serve.managers.ManagerFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static misc.Utils.exactDateToString;
import static misc.Utils.toHHMM;
import static misc.WebConstants.*;

/**
 * This servlet takes a single parameter - the id of the room and if such exists
 * and prints its data in a JSON format.
 */
@WebServlet(name = "RoomInfo", urlPatterns = {"/rooms/room"})
public class RoomInfo extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> params = request.getParameterNames();
        List<String> p = new ArrayList<>();
        while (params.hasMoreElements()) {
            p.add(params.nextElement());

        }
        response.setContentType("application/json");
        String s = p.toString();
        response.setCharacterEncoding("UTF-8");
        JsonObjectBuilder builder = Json.createObjectBuilder();
        RoomManager manager = ((ManagerFactory) request.getServletContext()
                .getAttribute(MANAGER_FACTORY)).getRoomManager();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        Room room;
        try {
            room = id == null ? manager.getRoomByName(name) : manager.getRoomById(Integer.valueOf(id));
            buildJson(builder, room, manager);
        } catch (NumberFormatException | NullPointerException e) {
            builder.add(JSON_ERROR, JSON_ROOM_ERROR_BAD_PARAM);
        }
        JsonWriter writer = Json.createWriter(response.getWriter());
//        System.out.println(builder.build().toString());
        writer.writeObject(builder.build());
    }

    /***/
    private void buildJson(JsonObjectBuilder builder, Room room, RoomManager manager) {
        if (room == null) {
            builder.add(JSON_ERROR, JSON_ROOM_ERROR_NOT_FOUND);
            return;
        }
        addBasicInfo(builder, room);
        addImages(builder, room, manager);
        addBookings(builder, room, manager);
        addProblems(builder, room, manager);
    }


    /**
     * Adds basic info of the room to the json object builder.
     */
    private void addBasicInfo(JsonObjectBuilder objectBuilder, Room room) {
        objectBuilder.add(JSON_ROOM_ID, room.getId());
        objectBuilder.add(JSON_ROOM_NAME, room.getRoomName());
        objectBuilder.add(JSON_ROOM_FLOOR, room.getFloor());
        objectBuilder.add(JSON_ROOM_CAPACITY, room.getCapacity());
        objectBuilder.add(JSON_ROOM_TYPE, room.getRoomType().name());
        objectBuilder.add(JSON_ROOM_SEAT_TYPE, room.getSeatType().name());
        objectBuilder.add(JSON_ROOM_AVAILABLE, room.isAvailableForStudents());

    }

    /**
     * Adds images associated with the given room to the json object.
     * The first image will be added as its main image.
     * The rest will be added in the format of the array.
     */
    private void addImages(JsonObjectBuilder builder, Room room, RoomManager manager) {
        List<String> images = manager.getAllImagesOf(room);
        String mainImage = images.size() > 0 ? images.get(0) : null;
        builder.add(JSON_ROOM_MAIN_IMAGE, mainImage == null ? NO_IMAGE : mainImage);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 1; i < images.size(); i++) {
            arrayBuilder.add(images.get(i));
        }
        builder.add(JSON_ROOM_IMAGES, arrayBuilder.build());
    }

    /**
     * Appends the list of the current problems of the room to the json builder in the format
     * of the json array.
     */
    private void addProblems(JsonObjectBuilder builder, Room room, RoomManager manager) {
        JsonArrayBuilder problemArrayBuilder = Json.createArrayBuilder();
        for (RoomProblem problem : manager.findAllProblemsOf(room)) {
            JsonObjectBuilder problemBuilder = Json.createObjectBuilder();
            problemBuilder.add(JSON_ROOM_PROBLEM_TITLE, problem.getTitle());
            problemBuilder.add(JSON_ROOM_PROBLEM_DESCR, problem.getDescription());
            problemBuilder.add(JSON_ROOM_PROBLEM_AUTHOR,
                    problem.getAuthor().getFirstName() + " " + problem.getAuthor().getLastName());
            problemBuilder.add(JSON_ROOM_PROBLEM_DATE, exactDateToString(problem.getDateCreated()));
            problemArrayBuilder.add(problemBuilder.build());
        }
        builder.add(JSON_ROOM_PROBLEMS, problemArrayBuilder.build());
    }

    /**
     * Appends the list of the all lectures of the room to the json builder in the format
     * of the json array.
     */
    private void addBookings(JsonObjectBuilder builder, Room room, RoomManager manager) {
        JsonArrayBuilder bookingArrayBuilder = Json.createArrayBuilder();
        for (Booking booking : manager.findAllBookingsAt(room)) {
            JsonObjectBuilder bookingBuilder = Json.createObjectBuilder();
            bookingBuilder.add(JSON_LECTURE_SUBJECT, booking.getSubject().getName());
            bookingBuilder.add(JSON_LECTURE_LECTURER, booking.getBooker().getFirstName() + " " +
                    booking.getBooker().getLastName());
            bookingBuilder.add(JSON_LECTURE_START_TIME, toHHMM(booking.getStartTime()));
            bookingBuilder.add(JSON_LECTURE_END_TIME, toHHMM(booking.getEndTime()));
            bookingArrayBuilder.add(bookingBuilder.build());
        }
        builder.add(JSON_ROOM_LECTURES, bookingArrayBuilder.build());
    }
}
