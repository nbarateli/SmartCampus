package serve.booking;

import model.accounts.AccountManager;
import model.accounts.User;
import model.bookings.Booking;
import model.bookings.BookingManager;
import model.lectures.LectureManager;
import model.rooms.Room;
import model.rooms.RoomManager;
import serve.managers.ManagerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;

import static misc.ModelConstants.SENTINEL_INT;
import static misc.WebConstants.*;

/**
 * Created by Administrator on 04.07.2017.
 */
@WebServlet(name = "BookingAdder", urlPatterns = {"/bookings/addbooking"})
public class BookingAdder extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute(SIGNED_ACCOUNT);
        if (currentUser == null) return;
        ServletContext context = request.getServletContext();
        ManagerFactory factory = (ManagerFactory) context.getAttribute(MANAGER_FACTORY);
        BookingManager bookingManager = factory.getBookingManager();
        LectureManager lectureManager = factory.getLectureManager();
        AccountManager accountManager = factory.getAccountManager();
        RoomManager roomManager = factory.getRoomManager();

        Room room = roomManager.getRoomByName(request.getParameter("room_name"));
        if (room == null) {
            response.getWriter().println(FAILED);
            return;
        }
        Date bookingDate = misc.Utils.stringToDate(request.getParameter("start_date"));
        String description = request.getParameter("description");
        Time startTime = misc.Utils.toHHMM(request.getParameter("start_time"));
        Time endTime = misc.Utils.toHHMM(request.getParameter("end_time"));
        Booking.WeekDay weekDay = null;
        if (bookingDate != null) {
            weekDay = misc.Utils.toWeekDay(bookingDate);
        }
        try {
            Booking booking = new Booking(SENTINEL_INT, currentUser, room,
                    null, weekDay, startTime, endTime, description, bookingDate);
            bookingManager.add(booking);
            response.getWriter().println(SUCCESS);
        } catch (Exception ex) {
            response.getWriter().println(FAILED);
        }
    }
}
