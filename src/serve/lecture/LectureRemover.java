package serve.lecture;

import model.lectures.manager.LectureManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static misc.WebConstants.*;

/**
 * Servlet implementation class LectureRemover
 */
@WebServlet(name = "Lecture Remover", urlPatterns = {"/lectures/removelecture"})
public class LectureRemover extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LectureRemover() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        LectureManager manager = (LectureManager) request.getServletContext().getAttribute(LECTURE_MANAGER);
        PrintWriter out = response.getWriter();
        if ("true".equals(request.getParameter("remove_all"))) {
            manager.removeAllLectures();
            out.println(SUCCESS + ": all lectures removed.");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("lecture_id"));
            if (manager.remove(id)) {
                out.println(SUCCESS + ": lecture removed");
            } else {
                out.println(FAILED + ": lecture not found");
            }

        } catch (NumberFormatException e) {
            out.println(FAILED + ": bad parameter");
        }
    }

}
