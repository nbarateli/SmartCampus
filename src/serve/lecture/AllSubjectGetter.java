package serve.lecture;

import model.lectures.CampusSubject;
import model.lectures.SubjectManager;
import serve.managers.ManagerFactory;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static misc.WebConstants.*;

@WebServlet(name = "AllSubjectGetter", urlPatterns = {"/getallsubjects"})
public class AllSubjectGetter extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        SubjectManager manager = ((ManagerFactory) getServletContext().
                getAttribute(MANAGER_FACTORY)).getSubjectManager();

        List<CampusSubject> subjects = manager.getAllSubjects();
        JsonArray array = toJsonArray(subjects);
        Json.createWriter(response.getWriter()).writeArray(array);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private JsonArray toJsonArray(List<CampusSubject> subjects) throws JsonException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (CampusSubject subject : subjects) {
            arrayBuilder.add(toJsonObject(subject));
        }
        return arrayBuilder.build();
    }

    private JsonObject toJsonObject(CampusSubject subject) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add(JSON_SUBJECT_ID, subject.getId());
        builder.add(JSON_SUBJECT_NAME, subject.getName());

        return builder.build();
    }
}
