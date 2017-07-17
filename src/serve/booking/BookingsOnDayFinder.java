package serve.booking;


import model.accounts.User;
import model.bookings.Booking;
import model.bookings.BookingManager;
import model.subjects.CampusSubject;
import model.rooms.Room;
import model.rooms.RoomManager;
import serve.managers.ManagerFactory;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static misc.Utils.dateToString;
import static misc.Utils.toHHMM;
import static misc.WebConstants.*;

@WebServlet(name = "BookingsOnDayFinder", urlPatterns = {"/bookings/bookings_on"})
public class BookingsOnDayFinder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObjectBuilder builder = Json.createObjectBuilder();
        ManagerFactory factory = (ManagerFactory) getServletContext().getAttribute(MANAGER_FACTORY);
        BookingManager bookingManager = factory.getBookingManager();
        RoomManager roomManager = factory.getRoomManager();

        buildJson(builder, request, bookingManager, roomManager);
        Json.createWriter(response.getWriter()).writeObject(builder.build());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * builds JsonObject with all the bookings that match information sent in request
     * @param builder JsonObjectBuilder which is used to build JsonObject for these bookings
     * @param request passed HttpServletRequest
     * @param bookingManager DAO needed to find specific bookings in database
     * @param roomManager DAO needed to find specific rooms in database
     */
    private void buildJson(JsonObjectBuilder builder, HttpServletRequest request, BookingManager bookingManager,
                           RoomManager roomManager) {
        try {
            String id = request.getParameter("room_id");
            Date day = getDay(request);
            Room room = roomManager.getRoomById(Integer.parseInt(id));
            if (room == null) {
                builder.add(JSON_ERROR, JSON_ROOM_ERROR_NOT_FOUND);
                return;
            }
            JsonArray bookings = toBookingJsonArray(bookingManager.getAllBookingsOn(day, room));
            builder.add(JSON_ROOM_BOOKINGS, bookings);
        } catch (Exception e) {
            builder.add(JSON_ERROR, JSON_ERROR_BAD_PARAM);
        }
    }

    /**
     * returns given list of bookings as JsonArray
     * @param allBookings list of all bookings needed to be converted
     * @return JsonArray representation of this list of bookings
     */
    private JsonArray toBookingJsonArray(List<Booking> allBookings) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Booking booking : allBookings) {
            arrayBuilder.add(bookingToJsonObject(booking));
        }
        return arrayBuilder.build();
    }

    /**
     * parses given booking to JsonObject
     * @param booking booking to be converted
     * @return JsonObject representation ofgiven booking
     */
    private JsonObject bookingToJsonObject(Booking booking) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add(JSON_BOOKING_ID, booking.getId());
        builder.add(JSON_BOOKING_ROOM_ID, booking.getRoom().getId());
        builder.add(JSON_BOOKING_ROOM_NAME, booking.getRoom().getRoomName());
        User booker = booking.getBooker();
        builder.add(JSON_BOOKING_BOOKER_ID, booker.getId());
        builder.add(JSON_BOOKING_BOOKER_NAME, booker.getFirstName() + " " + booker.getLastName());
        builder.add(JSON_BOOKING_BOOKER_PICTURE, booker.getImageURL());
        CampusSubject subject = booking.getSubject();
        if (subject == null) {
            builder.add(JSON_BOOKING_SUBJECT_ID, JsonValue.NULL);
            builder.add(JSON_BOOKING_SUBJECT_NAME, JsonValue.NULL);
        } else {
            builder.add(JSON_BOOKING_SUBJECT_ID, subject.getId());
            builder.add(JSON_BOOKING_SUBJECT_NAME, subject.getName());
        }
        if (booking.getDescription() != null) {
            builder.add(JSON_BOOKING_DESCRIPTION, booking.getDescription());
        } else {
            builder.add(JSON_BOOKING_DESCRIPTION, JsonValue.NULL);
        }
        builder.add(JSON_BOOKING_DATE, dateToString(booking.getBookingDate(), "dd.mm.yy"));
        builder.add(JSON_BOOKING_START_TIME, toHHMM(booking.getStartTime()));
        builder.add(JSON_BOOKING_END_TIME, toHHMM(booking.getEndTime()));
        return builder.build();
    }

    /**
     * parses current day passed with request to dd.MM.yyyy format
     * @param request request sent to this servlet
     * @return respective date to passed day in dd.MM.yyyy format
     * @throws Exception if there occured exception during parsing date
     */
    private Date getDay(HttpServletRequest request) throws Exception {
        String day = request.getParameter("day");
        return new SimpleDateFormat("dd.MM.yyyy").parse(day);
    }
}
