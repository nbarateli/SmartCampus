package serve.booking;

import model.accounts.User;
import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
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
import java.io.PrintWriter;
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
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        User currentUser = (User) request.getSession().getAttribute(SIGNED_ACCOUNT);
        if (currentUser == null) {
            out.print(FAILED + ": no user signed in");
            return;
        }

        ServletContext context = request.getServletContext();
        ManagerFactory factory = (ManagerFactory) context.getAttribute(MANAGER_FACTORY);
        BookingManager bookingManager = factory.getBookingManager();
        RoomManager roomManager = factory.getRoomManager();

        if (!factory.getAccountManager().getAllPermissionsOf(currentUser).contains(User.UserPermission.BOOK_A_ROOM)) {
            out.print(FAILED + ": you don't have that permission.");
            return;
        }
        Room room = roomManager.getRoomByName(request.getParameter("room_name"));
        if (room == null) {
            out.print(FAILED + " room not found");
            return;
        }
        Date bookingDate = misc.Utils.stringToDate(request.getParameter("booking_date"), "dd.MM.yyyy");

        String description = request.getParameter("description");
        Time startTime = misc.Utils.toHHMM(request.getParameter("start_time"));
        Time endTime = misc.Utils.toHHMM(request.getParameter("end_time"));

        try {
            Booking booking = new Booking(SENTINEL_INT, currentUser, room,
                    null, startTime, endTime, description, bookingDate);
            if (!overlapsOtherBookings(booking, bookingManager) && bookingManager.add(booking)) {
                out.println(SUCCESS);
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            out.println(FAILED + ": overlaps other bookings or lectures.");
        }
    }

    private boolean overlapsOtherBookings(Booking booking, BookingManager bookingManager) {
        BookingSearchQueryGenerator generator = new BookingSearchQueryGenerator();
        generator.setRoom(booking.getRoom());
        generator.setStartTime(booking.getStartTime());
        generator.setEndTime(booking.getEndTime());
        generator.setBookingDate(booking.getBookingDate());

        return bookingManager.find(generator).size() > 0;

    }
}
