package serve.lecture;

import misc.ModelConstants;
import model.accounts.AccountManager;
import model.accounts.User;
import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
import model.subjects.CampusSubject;
import model.subjects.SubjectManager;
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
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import static misc.ModelConstants.DAYS_IN_WEEK;
import static misc.Utils.addDaysToDate;
import static misc.WebConstants.*;

/**
 * Servlet implementation class LectureAdder
 */
@WebServlet(name = "Lecture Adder", urlPatterns = {"/subjects/addlecture"})
public class LectureAdder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LectureAdder() {
        super();
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<String> ps = new LinkedList<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) ps.add(params.nextElement());
        String s = ps.toString();
        ServletContext context = request.getServletContext();

        ManagerFactory factory = (ManagerFactory) context.getAttribute(MANAGER_FACTORY);
        BookingManager manager = factory.getBookingManager();
        SubjectManager subjectManager = factory.getSubjectManager();
        AccountManager accountManager = factory.getAccountManager();
        RoomManager roomManager = factory.getRoomManager();


        if (addLecture(request, manager, subjectManager, accountManager, roomManager, response.getWriter())) {
            response.getWriter().println(SUCCESS);

        }

    }

    /**
     * Reads parameters from the request and tries to add it
     */
    private boolean addLecture(HttpServletRequest request, BookingManager manager,
                               SubjectManager subjectManager, AccountManager accountManager, RoomManager roomManager,
                               PrintWriter writer) {
        String lecturerEmail = request.getParameter("lecturer_email");
        String subjectName = request.getParameter("subject_name");
        String roomName = request.getParameter("room_name");
        String description = request.getParameter("description");
        Date date = misc.Utils.stringToDate(request.getParameter("start_date"), "dd.MM.yyyy");
        Time startTime = misc.Utils.toHHMM(request.getParameter("start_time"));
        Time endTime = misc.Utils.toHHMM(request.getParameter("end_time"));
        Integer numWeeks = misc.Utils.validateNumber(request.getParameter("num_weeks"), 1, 16);
        Integer rep = misc.Utils.validateNumber(request.getParameter("repetition"), 1, 16);

        User lecturer = accountManager.getUserViaEMail(lecturerEmail);
        CampusSubject subject = subjectManager.getSubjectByName(subjectName);
        Room room = roomManager.getRoomByName(roomName);

        if (validParameters(lecturer, room, startTime, endTime, date, numWeeks, rep, writer) &&
                !overlapsOtherLectures(room, date, startTime, endTime, numWeeks, rep, manager, writer)) {
            for (int i = 0; i < numWeeks / rep; i++) {
                Booking thisBooking = new Booking(
                        ModelConstants.SENTINEL_INT, lecturer, room, subject, startTime,
                        endTime, description,
                        addDaysToDate(date, i * DAYS_IN_WEEK * rep));
                manager.add(thisBooking);
            }

            return true;
        }
        return false;
    }

    private boolean overlapsOtherLectures(Room room, Date date, Time startTime,
                                          Time endTime, Integer numWeeks, Integer rep,
                                          BookingManager manager, PrintWriter writer) {
        BookingSearchQueryGenerator queryGenerator = new BookingSearchQueryGenerator();
        queryGenerator.setBookingDate(date);
        queryGenerator.setRoom(room);
        queryGenerator.setStartTime(startTime);
        queryGenerator.setEndTime(endTime);
        for (int i = 0; i < numWeeks / rep; i++) {
            queryGenerator.setBookingDate(addDaysToDate(date, i * DAYS_IN_WEEK * rep));

            for (Booking booking : manager.find(queryGenerator)) {
                if (booking.getSubject() != null) {
                    writer.print(FAILED + ": overlaps other subjects");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validParameters(User lecturer, Room room, Time startTime, Time endTime,
                                    Date date, Integer numWeeks, Integer rep, PrintWriter writer) {
        if (lecturer != null && room != null && startTime != null && endTime != null && date != null
                && numWeeks != null && rep != null) {
            return true;
        }
        writer.print(FAILED + ": incomplete parameters");
        return false;
    }
}
