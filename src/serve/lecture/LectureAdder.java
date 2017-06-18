package serve.lecture;

import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misc.Utils.*;
import misc.*;
import model.accounts.User;
import model.database.SQLConstants;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;
import model.lectures.manager.DefaultLectureManager;
import model.lectures.manager.LectureManager;
import model.rooms.Room;
import model.rooms.manager.DefaultRoomManager;

import static model.database.SQLConstants.*;

/**
 * Servlet implementation class LectureAdder
 */
@WebServlet("/LectureAdder")
public class LectureAdder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public LectureAdder() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LectureManager manager = (LectureManager)request.getServletContext().getAttribute(WebConstants.LECTURE_MANAGER);
		
		String lecturerName = request.getParameter("lecturer_name");
		String firstName = lecturerName.substring(0,lecturerName.indexOf(' '));
		String lastName = lecturerName.substring(lecturerName.indexOf(' ') + 1);
		
		String subjectName = request.getParameter("subject");
		String roomName = request.getParameter("room_name");
		WeekDay weekDay = misc.Utils.toWeekDay(request.getParameter("week_day"));
		Time startTime = misc.Utils.toHHMM(request.getParameter("start_time"));
		Time endTime = misc.Utils.toHHMM(request.getParameter("end_time"));
		
		System.out.println("SELECT * FROM " + SQL_TABLE_USER + " WHERE " + 
				SQL_COLUMN_USER_FIRST_NAME + "=\'" + firstName + "\' AND "+ 
				SQL_COLUMN_USER_LAST_NAME + "=\'" + lastName + "\'");
		System.out.println("SELECT * FROM " + SQL_TABLE_ROOM + " WHERE " +
				SQL_COLUMN_ROOM_NAME + "=\'" + roomName + "\'");
		System.out.println("SELECT * FROM " + SQL_TABLE_SUBJECT + " WHERE " +
				SQL_COLUMN_SUBJECT_NAME + "=\'" + subjectName + "\'");
		User lecturer = DefaultLectureManager.findLecturer("SELECT * FROM " + SQL_TABLE_USER + " WHERE " + 
															SQL_COLUMN_USER_FIRST_NAME + "=\'" + firstName + "\' AND "+ 
															SQL_COLUMN_USER_LAST_NAME + "=\'" + lastName + "\'").get(0);
		System.out.println(lecturer.getLastName());
		CampusSubject subject = DefaultLectureManager.findSubject("SELECT * FROM " + SQL_TABLE_SUBJECT + " WHERE " +
															SQL_COLUMN_SUBJECT_NAME + "=\'" + subjectName + "\'").get(0);
		System.out.println(subject.getName());
		Room room = DefaultRoomManager.findRooms("SELECT * FROM " + SQL_TABLE_ROOM + " WHERE " +
				SQL_COLUMN_ROOM_NAME + "=\'" + roomName + "\'").get(0);

		System.out.println("Zauraaaaaaaaaa");
		System.out.println(room.getID() + "   " + lecturer.getLastName() + " " + subject.getName());
		if(lecturer != null && room != null && subject != null 
				&& weekDay != null && startTime != null && endTime != null){
			Lecture thisLecture = new Lecture(ModelConstants.SENTINEL_INT, lecturer, room, subject, weekDay, startTime, endTime);
			manager.add(thisLecture);
		}
		
        response.sendRedirect("data/addingData.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
