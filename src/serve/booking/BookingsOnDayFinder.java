package serve.booking;


import model.accounts.User;
import model.bookings.Booking;
import model.bookings.BookingManager;
import model.lectures.CampusSubject;
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

    private void buildJson(JsonObjectBuilder builder, HttpServletRequest request, BookingManager bookingManager, RoomManager roomManager) {
        try {
            Date day = getDay(request);
            Room room = roomManager.getRoomById(Integer.parseInt(request.getParameter("room_id")));
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

    private JsonArray toBookingJsonArray(List<Booking> allBookings) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Booking booking : allBookings) {
            arrayBuilder.add(bookingToJsonObject(booking));
        }
        return arrayBuilder.build();
    }

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
        builder.add(JSON_BOOKING_START_DATE, dateToString(booking.getStartDate(), "dd.mm.yy"));
        builder.add(JSON_BOOKING_END_DATE, dateToString(booking.getEndDate(), "dd.mm.yy"));
        builder.add(JSON_BOOKING_START_TIME, toHHMM(booking.getStartTime()));
        builder.add(JSON_BOOKING_END_TIME, toHHMM(booking.getEndTime()));
        return builder.build();
    }

    private Date getDay(HttpServletRequest request) throws Exception {
        String day = request.getParameter("day");
        return new SimpleDateFormat("dd.MM.yy").parse(day);
    }
}