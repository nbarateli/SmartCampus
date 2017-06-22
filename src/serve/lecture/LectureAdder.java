package serve.lecture;

import misc.ModelConstants;
import misc.WebConstants;
import model.accounts.DefaultAccountManager;
import model.accounts.User;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;
import model.lectures.manager.LectureManager;
import model.rooms.Room;
import model.rooms.manager.RoomManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;

import static misc.WebConstants.*;

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
        LectureManager manager = (LectureManager) context.getAttribute(WebConstants.LECTURE_MANAGER);
        DefaultAccountManager accountManager = (DefaultAccountManager) context.getAttribute(ACCOUNT_MANAGER);
        RoomManager roomManager = (RoomManager) context.getAttribute(ROOM_MANAGER);
        String lecturerEmail = request.getParameter("lecturer_email");

        String subjectName = request.getParameter("subject_name");
        String roomName = request.getParameter("room_name");
        WeekDay weekDay = misc.Utils.toWeekDay(request.getParameter("week_day"));
        Time startTime = misc.Utils.toHHMM(request.getParameter("start_time"));
        Time endTime = misc.Utils.toHHMM(request.getParameter("end_time"));

        User lecturer = accountManager.getUserViaEMail(lecturerEmail);
        CampusSubject subject = manager.findSubject(subjectName);
        Room room = roomManager.getRoomByName(roomName);

        if (lecturer != null && room != null && subject != null
                && startTime != null && endTime != null) {
            Lecture thisLecture = new Lecture(
                    ModelConstants.SENTINEL_INT, lecturer, room, subject, weekDay, startTime, endTime);
            manager.add(thisLecture);
            response.getWriter().println(SUCCESS);
        } else {
            response.getWriter().println(FAILED);
        }
    }

}
