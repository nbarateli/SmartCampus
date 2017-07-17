package serve.lecture;

import misc.ModelConstants;
import model.accounts.AccountManager;
import model.accounts.User;
import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
import model.rooms.Room;
import model.rooms.RoomManager;
import model.subjects.CampusSubject;
import model.subjects.SubjectManager;
import serve.managers.ManagerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        ServletContext context = request.getServletContext();
        ManagerFactory factory = (ManagerFactory) context.getAttribute(MANAGER_FACTORY);
        BookingManager manager = factory.getBookingManager();
        SubjectManager subjectManager = factory.getSubjectManager();
        AccountManager accountManager = factory.getAccountManager();
        RoomManager roomManager = factory.getRoomManager();


        if (addLecture(request, manager, subjectManager, accountManager, roomManager, response.getWriter())) {
            response.getWriter().print(SUCCESS);
        }
    }

    /**
     * Reads parameters from the request and tries to add it
     * @param request passed HttpServletRequest
     * @param manager DAO needed to find specific bookings in database
     * @param subjectManager DAO needed to find specific subjects in database
     * @param accountManager DAO needed to find specific accounts in database
     * @param roomManager DAO needed to find specific rooms in database
     * @param writer response writer
     * @return true if no problems occurred while adding lecture
     */
    private boolean addLecture(HttpServletRequest request, BookingManager manager,
                               SubjectManager subjectManager, AccountManager accountManager, RoomManager roomManager,
                               PrintWriter writer) {
        //get request parameters
        String lecturerEmail = request.getParameter("lecturer_email");
        String subjectName = request.getParameter("subject_name");
        String roomName = request.getParameter("room_name");
        String description = request.getParameter("description");
        Date date = misc.Utils.stringToDate(request.getParameter("start_date"), "dd.MM.yyyy");
        Time startTime = misc.Utils.toHHMM(request.getParameter("start_time"));
        Time endTime = misc.Utils.toHHMM(request.getParameter("end_time"));
        Integer numWeeks = misc.Utils.validateNumber(request.getParameter("num_weeks"), 1, 16);
        Integer rep = misc.Utils.validateNumber(request.getParameter("repetition"), 1, 16);

        //get respective lecturer, subject and room from database
        User lecturer = accountManager.getUserViaEMail(lecturerEmail);
        CampusSubject subject = subjectManager.getSubjectByName(subjectName);
        Room room = roomManager.getRoomByName(roomName);

        //if parameters are valid adds lectures in database according to repetition and number of weeks
        if (validParameters(lecturer, room, startTime, endTime, date, numWeeks, rep, subject, writer) &&
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

    /**
     * checks that the given lectures don't overlap with old ones.
     * @param room requested room
     * @param date requested start date
     * @param startTime requested start time
     * @param endTime requested end time
     * @param numWeeks requested number of weeks
     * @param rep requested repetition (number of weeks between each repetition of this lecture)
     * @param manager DAO needed to find specific bookings in database
     * @param writer response writer
     * @return true if this lecture will overlap with any other lecture already in database
     */
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
                    writer.print(FAILED);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks if all the passed parameters are valid
     * @param room requested room
     * @param date requested start date
     * @param startTime requested start time
     * @param endTime requested end time
     * @param numWeeks requested number of weeks
     * @param rep requested repetition (number of weeks between each repetition of this lecture)
     * @param writer response writer
     * @return
     */
    private boolean validParameters(User lecturer, Room room, Time startTime, Time endTime,
                                    Date date, Integer numWeeks, Integer rep, CampusSubject subject, PrintWriter writer) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        if (lecturer != null && room != null && startTime != null && endTime != null && date != null
                && numWeeks != null && rep != null && subject != null) {
            return true;
        }
        if (lecturer == null) objectBuilder.add(JSON_BOOKING_BOOKER, "შეიყვანეთ არსებული ლექტორის ფოსტის მისამართი!");
        if (room == null) objectBuilder.add(JSON_ROOM_NAME, "შეიყვანეთ არსებული ოთახის ნომერი!");
        ;
        if (startTime == null) objectBuilder.add(JSON_BOOKING_START_TIME, "*");
        if (endTime == null) objectBuilder.add(JSON_BOOKING_END_TIME, "*");
        if (date == null) objectBuilder.add(JSON_BOOKING_DATE, "*");
        if (numWeeks == null) objectBuilder.add("numWeeks", "მიუთითეთ კვირების რაოდენობა!");
        if (subject == null) objectBuilder.add(JSON_BOOKING_SUBJECT, "სწორად შეიყვანეთ საგანი!");

        JsonObject obj = objectBuilder.build();
        JsonWriter jwriter = Json.createWriter(writer);
        jwriter.writeObject(obj);

        return false;
    }
}
