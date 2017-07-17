package serve.rooms;

import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
import model.rooms.Room;
import model.rooms.RoomManager;
import model.rooms.RoomSearchQueryGenerator;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static misc.ModelConstants.DAYS_IN_WEEK;
import static misc.Utils.*;
import static misc.WebConstants.MANAGER_FACTORY;

/**
 * Created by Administrator on 06.07.2017.
 */
@WebServlet(name = "AvailableRoomFinder", urlPatterns = {"/rooms/availablerooms"})
public class AvailableRoomFinder extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ManagerFactory managerFactory = (ManagerFactory)
                request.getServletContext().getAttribute(MANAGER_FACTORY);
        RoomManager roomManager = managerFactory.getRoomManager();
        BookingManager bookingManager = managerFactory.getBookingManager();

        List<Room> rooms = handleRequestParameters(request, roomManager, bookingManager);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Room room : rooms) {
            arrayBuilder.add(room.getRoomName());
        }
        JsonWriter writer = Json.createWriter(response.getWriter());
        writer.writeArray(arrayBuilder.build());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * gets rooms that match request parameters and are empty during the time passed through request
     * @param request HttpServletRequest sent to this servlet
     * @param roomManager DAO needed to find specific bookings in database
     * @param bookingManager DAO needed to find specific rooms in database
     * @return list of rooms that are free of lectures during passed time and match other specifications
     */
    private List<Room> handleRequestParameters(HttpServletRequest request, RoomManager roomManager,
                                               BookingManager bookingManager) {
        List<Room> rooms = new ArrayList<>();

        //get parameters from request
        Integer capacity = validateNumber(request.getParameter("num_students"), 1, 200);
        RoomSearchQueryGenerator roomQuery = new RoomSearchQueryGenerator();
        roomQuery.setCapacityFrom(capacity == null ? 0 : capacity);
        rooms = roomManager.find(roomQuery);

        Integer numWeeks = validateNumber(request.getParameter("num_weeks"), 1, 16);
        Date date = stringToDate(request.getParameter("start_date"), "dd.MM.yyyy");
        Time startTime = stringToTime(request.getParameter("start_time"));
        Time endTime = stringToTime(request.getParameter("end_time"));

        return removeRoomWhenNotFree(request, bookingManager, numWeeks, date, startTime, endTime, rooms);
    }

    /**
     * removes rooms from list when there are lectures overlapping with lecture given through request
     * @param request HttpServletRequest sent to this servlet
     * @param bookingManager DAO needed to find specific bookings in database
     * @param numWeeks requested number of weeks
     * @param date requested start date
     * @param startTime requested start time
     * @param endTime requested end time
     * @param rooms list of rooms needed to be checked for overlapping lectures
     * @return list of remaining rooms
     */
    List<Room> removeRoomWhenNotFree(HttpServletRequest request, BookingManager bookingManager, Integer numWeeks,
                                     Date date, Time startTime, Time endTime, List<Room> rooms) {
        if (numWeeks != null && date != null && startTime != null && endTime != null) {
            Integer rep = validateNumber(request.getParameter("repetition"), 1, 4);

            BookingSearchQueryGenerator generator = new BookingSearchQueryGenerator();
            generator.setStartTime(startTime);
            generator.setEndTime(endTime);

            for (int i = 0; i < numWeeks / rep; i++) {
                Iterator<Room> it = rooms.iterator();
                while (it.hasNext()) {
                    Room room = it.next();
                    generator.setRoom(room);
                    generator.setBookingDate(addDaysToDate(date, i * DAYS_IN_WEEK * rep));
                    List<Booking> bookings = bookingManager.find(generator);
                    if (bookings.size() == 0) continue;
                    for (Booking booking : bookings) {
                        if (booking.getSubject() != null)
                            it.remove();
                    }
                }
            }

        }

        return rooms;
    }
}
