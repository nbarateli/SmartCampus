package serve.lecture;

import misc.ModelConstants;
import misc.WebConstants;
import model.accounts.User;
import model.bookings.Booking;
import model.bookings.BookingManager;
import model.lectures.CampusSubject;
import model.lectures.Lecture.WeekDay;
import model.lectures.LectureManager;
import model.managers.DefaultAccountManager;
import model.rooms.Room;
import model.rooms.RoomManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;

import static misc.WebConstants.*;
import static misc.ModelConstants.DAYS_IN_WEEK;

/**
 * Servlet implementation class LectureAdder
 */
@WebServlet(name = "Lecture Adder", urlPatterns = {"/lectures/addlecture"})
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
        ServletContext context = request.getServletContext();
        BookingManager manager = 
                (BookingManager) context.getAttribute(WebConstants.BOOKING_MANAGER);
        LectureManager lectManager = 
                (LectureManager) context.getAttribute(WebConstants.LECTURE_MANAGER);
        DefaultAccountManager accountManager = 
                (DefaultAccountManager) context.getAttribute(ACCOUNT_MANAGER);
        RoomManager roomManager = (RoomManager) context.getAttribute(ROOM_MANAGER);
        
        String lecturerEmail = request.getParameter("lecturer_email");
        String subjectName = request.getParameter("subject_name");
        String roomName = request.getParameter("room_name");
        Date date = misc.Utils.stringToDate(request.getParameter("start_date"));
        WeekDay weekDay = misc.Utils.toWeekDay(date);
        Time startTime = misc.Utils.toHHMM(request.getParameter("start_time"));
        Time endTime = misc.Utils.toHHMM(request.getParameter("end_time"));
        Integer numWeeks = misc.Utils.validateNumber(request.getParameter("num_weeks"), 1, 16);
        int rep = Integer.parseInt(request.getParameter("repetition"));
        
        User lecturer = accountManager.getUserViaEMail(lecturerEmail);
        CampusSubject subject = lectManager.findSubject(subjectName);
        Room room = roomManager.getRoomByName(roomName);

        if (lecturer != null && room != null && subject != null
                && startTime != null && endTime != null && date != null && numWeeks != null) {
            
            for(int i = 0; i < numWeeks; i += DAYS_IN_WEEK * rep) {
                Booking thisBooking = new Booking(
                        ModelConstants.SENTINEL_INT, lecturer, room, subject, weekDay, startTime, 
                        endTime, null, misc.Utils.addDaysToDate(date, i));
                manager.add(thisBooking);
            }
            
            response.getWriter().println(SUCCESS);
        } else {
            response.getWriter().println(FAILED);
        }
    }

}
