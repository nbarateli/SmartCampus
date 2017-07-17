package serve;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static misc.WebConstants.SIGNED_ACCOUNT;

@WebServlet(name = "LogoutHandler", urlPatterns = {"/logout", "/signout"})
public class LogoutHandler extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute(SIGNED_ACCOUNT, null);
        session.invalidate();
        response.getWriter().println("signed out");
    }
}
