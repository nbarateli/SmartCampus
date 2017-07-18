package serve.rooms;

import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
import model.rooms.Room;
import model.rooms.RoomManager;
import model.rooms.RoomSearchQueryGenerator;
import serve.managers.ManagerFactory;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static misc.Utils.*;
import static misc.WebConstants.*;

/**
 * The sole function of this class is to return a JSON array of
 * <code>{@link model.rooms.Room}</code> objects that match passed parameters
 */
@WebServlet(name = "RoomFinder", urlPatterns = {"/rooms/findrooms"})
public class RoomFinder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ManagerFactory managerFactory = (ManagerFactory)
                request.getServletContext().getAttribute(MANAGER_FACTORY);
        RoomManager roomManager = managerFactory.getRoomManager();
        BookingManager bookingManager = managerFactory.getBookingManager();
        List<Room> rooms = roomManager.find(getQuery(request));


        BookingSearchQueryGenerator bookingSearchQueryGenerator = getTimeQuery(request);
        boolean searchByBooking = searchesByBooking(request);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Room room : rooms) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add(JSON_ROOM_ID, room.getId());
            objectBuilder.add(JSON_ROOM_NAME, room.getRoomName());
            objectBuilder.add(JSON_ROOM_FLOOR, room.getFloor());
            objectBuilder.add(JSON_ROOM_CAPACITY, room.getCapacity());
            objectBuilder.add(JSON_ROOM_TYPE, room.getRoomType().name());
            objectBuilder.add(JSON_ROOM_SEAT_TYPE, room.getSeatType().name());
            objectBuilder.add(JSON_ROOM_AVAILABLE, room.isAvailableForStudents());
            List<String> images = roomManager.getAllImagesOf(room);


            List<Booking> bookings = null;
            if (searchByBooking) {
                bookingSearchQueryGenerator.setRoom(room);
                bookings = bookingManager.find(bookingSearchQueryGenerator);
            }
            objectBuilder.add(JSON_ROOM_MAIN_IMAGE, images.size() > 0 ? images.get(0) : NO_IMAGE);
            objectBuilder.add(JSON_ROOM_TAKEN, searchByBooking && bookings.size() > 0);
            arrayBuilder.add(objectBuilder.build());
        }
        JsonArray array = arrayBuilder.build();
        JsonWriter writer = Json.createWriter(response.getWriter());
        writer.writeArray(array);

    }

    /**
     * checks if searching rooms by booking is necessary
     * @param request passed HttpServletRequest
     * @return true if at least one parameter associated with booking is not null
     */
    private boolean searchesByBooking(HttpServletRequest request) {
        String startDate = request.getParameter("start_date");
        String startTime = request.getParameter("start_time");
        String endDate = request.getParameter("end_date");
        String endTime = request.getParameter("end_time");
        return !"".equals(startDate) || !"".equals(startTime) || !"".equals(endDate) || !"".equals(endTime);
    }

    /**
     * builds BookingSearchQueryGenerator according to booking date and time parameters passed
     * @param request passed HttpServletRequest
     * @return BookingSearchQueryGenerator object which has all the needed parameters set
     */
    private BookingSearchQueryGenerator getTimeQuery(HttpServletRequest request) {
        BookingSearchQueryGenerator generator = new BookingSearchQueryGenerator();
        String dateFrom = request.getParameter("start_date");
        String dateTo = request.getParameter("end_date");
        String timeFrom = request.getParameter("start_time");
        String timeTo = request.getParameter("end_time");

        generator.setDateFrom(dateFrom == null ? null : stringToDate(dateFrom, "dd.MM.yyyy"));
        generator.setDateTo(dateTo == null ? null : stringToDate(dateTo, "dd.MM.yyyy"));
        generator.setStartTime(timeFrom == null ? null : stringToTime(timeFrom));
        generator.setEndTime(timeTo == null ? null : stringToTime(timeTo));
        return generator;
    }

    /**
     * returns integer value of passed string, null if it's empty or null and 0 if it's value is negative
     * @param s string we need to get value of
     * @return positive integer value of passed string
     */
    private Integer nullIfNoLength(String s) {
        return s == null || s.length() < 1 ? null : Integer.valueOf(s) < 0 ? 0 : Integer.valueOf(s);
    }

    /**
     * builds RoomSearchQueryGenerator according to parameters associated with rooms
     * @param request passed HttpServletRequest
     * @return RoomSearchQueryGenerator object which has all the needed parameters set
     */
    private RoomSearchQueryGenerator getQuery(HttpServletRequest request) {
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        if (request.getParameter("search") == null) return query;
        String name = request.getParameter("room_name");
        String floor = request.getParameter("room_floor");
        String roomType = request.getParameter("room_type");
        String seatType = request.getParameter("seat_type");
        boolean canBeBooked = request.getParameter("can_be_booked") != null;
        query.setName(name.length() > 0 ? name : null);
        query.setFloor(nullIfNoLength(floor));
        query.setCapacityFrom(nullIfNoLength(request.getParameter("capacity_from")));
        query.setCapacityTo(nullIfNoLength(request.getParameter("capacity_to")));
        query.setRoomType(roomType.length() > 0 ? toRoomType(roomType) : null);
        query.setSeatType(seatType.length() > 0 ? toSeatType(seatType) : null);
        query.setAvailableForBooking(canBeBooked);

        return query;
    }

}