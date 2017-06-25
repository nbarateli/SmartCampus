package serve.rooms;

import model.lectures.Lecture;
import model.rooms.Room;
import model.rooms.RoomManager;
import model.rooms.RoomProblem;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static misc.Utils.exactDateToString;
import static misc.Utils.toHHMM;
import static misc.WebConstants.*;

/**
 * This servlet takes a single parameter - the id of the room and if such exists
 * and prints its data in a JSON format.
 */
//@WebServlet(name = "RoomInfo", urlPatterns = {"/rooms/room"})
public class RoomInfo extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObjectBuilder builder = Json.createObjectBuilder();
        RoomManager manager = ((RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER));
        String id = request.getParameter("id");
        Room room;
        try {
            room = manager.getRoomById(Integer.valueOf(id));
            buildJson(builder, room, manager);
        } catch (NumberFormatException | NullPointerException e) {
            builder.add(JSON_ERROR, JSON_ROOM_ERROR_BAD_PARAM);
        }
        JsonWriter writer = Json.createWriter(response.getWriter());
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
        addLectures(builder, room, manager);
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
    private void addLectures(JsonObjectBuilder builder, Room room, RoomManager manager) {
        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
        for (Lecture lecture : manager.findAllLecturesAt(room)) {
            JsonObjectBuilder lectureBuilder = Json.createObjectBuilder();
            lectureBuilder.add(JSON_LECTURE_SUBJECT, lecture.getSubject().getName());
            lectureBuilder.add(JSON_LECTURE_LECTURER, lecture.getLecturer().getFirstName() + " " +
                    lecture.getLecturer().getLastName());
            lectureBuilder.add(JSON_LECTURE_START_TIME, toHHMM(lecture.getStartTime()));
            lectureBuilder.add(JSON_LECTURE_END_TIME, toHHMM(lecture.getEndTime()));
            lectureArrayBuilder.add(lectureBuilder.build());
        }
        builder.add(JSON_ROOM_LECTURES, lectureArrayBuilder.build());
    }
}
