package serve.lecture;

import static model.database.SQLConstants.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misc.WebConstants;
import model.accounts.User;
import model.lectures.CampusSubject;
import model.lectures.manager.DefaultLectureManager;
import model.lectures.manager.LectureManager;
import model.rooms.RoomSearchQuery;
import model.rooms.manager.RoomManager;

/**
 * Servlet implementation class SubjectAdder
 */
@WebServlet("/SubjectAdder")
public class SubjectAdder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectAdder() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		LectureManager manager = (LectureManager)request.getServletContext().getAttribute(WebConstants.LECTURE_MANAGER);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String subjectName = request.getParameter("subj_name");
		List<CampusSubject> list = DefaultLectureManager.findSubject("SELECT * FROM " + SQL_TABLE_SUBJECT + " WHERE " + 
																		SQL_COLUMN_SUBJECT_NAME + "=\'" + subjectName + "\'");
		CampusSubject subject = list.size() == 0 ? null : list.get(0);
		if(subject == null){
			manager.addSubject(subjectName);
			System.out.println("I added subject: " + subjectName);
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

