package serve.rooms;

import misc.ModelConstants;
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
import static misc.WebConstants.SUCCESS;

/**
 * Created by Administrator on 06.07.2017.
 */
@WebServlet(name = "AvailableRoomFinder", urlPatterns = {"/rooms/availablerooms"})
public class AvailableRoomFinder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ManagerFactory managerFactory = (ManagerFactory)
                request.getServletContext().getAttribute(MANAGER_FACTORY);
        RoomManager roomManager = managerFactory.getRoomManager();
        BookingManager bookingManager = managerFactory.getBookingManager();
        List<Room> rooms = new ArrayList<>();

        Integer capacity = validateNumber(request.getParameter("num_students"), 1, 200);
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        query.setCapacityFrom(capacity == null ? 0 : capacity);
        rooms = roomManager.find(query);

        Integer numWeeks = validateNumber(request.getParameter("num_weeks"), 1, 16);
        Date date = stringToDate(request.getParameter("start_date"));
        Time startTime = stringToTime(request.getParameter("start_time"));
        Time endTime = stringToTime(request.getParameter("end_time"));

        if(numWeeks != null && date != null && startTime != null && endTime != null) {
            Integer rep = validateNumber(request.getParameter("repetition"), 1, 4);

            BookingSearchQueryGenerator generator = new BookingSearchQueryGenerator();
            generator.setStartTime(startTime);
            generator.setEndTime(endTime);

            for (int i = 0; i < numWeeks / rep; i++) {
                Iterator<Room> it = rooms.iterator();
                while(it.hasNext()) {
                    Room room = it.next();
                    generator.setRoom(room);
                    generator.setStartDate(addDaysToDate(date, i * DAYS_IN_WEEK * rep));
                    List<Booking> bookings = bookingManager.find(generator);
                    for(int j = 0; j < bookings.size(); j++) {
                        if(bookings.get(j).getSubject() != null)
                            it.remove();
                    }
                }
            }

        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Room room : rooms) {
            arrayBuilder.add(room.getRoomName());
        }
        JsonWriter writer = Json.createWriter(response.getWriter());
        writer.writeArray(arrayBuilder.build());
    }
}
