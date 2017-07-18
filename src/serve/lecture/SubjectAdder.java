package serve.lecture;

import model.subjects.CampusSubject;
import model.subjects.SubjectManager;
import serve.managers.ManagerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static misc.ModelConstants.SENTINEL_INT;
import static misc.WebConstants.*;

/**
 * Servlet implementation class SubjectAdder
 */
@WebServlet(name = "Subject Adder", urlPatterns = {"/subjects/addsubject"})
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
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ManagerFactory factory = (ManagerFactory) getServletContext().getAttribute(MANAGER_FACTORY);
        SubjectManager manager = factory.getSubjectManager();
        String subjectName = request.getParameter("subj_name");
        CampusSubject searched = manager.getSubjectByName(subjectName);
        if (searched == null) {
            CampusSubject subj = new CampusSubject(SENTINEL_INT, subjectName);
            manager.add(subj);
            response.getWriter().print(SUCCESS);
        } else {
            response.getWriter().print(FAILED);
        }
    }
}

