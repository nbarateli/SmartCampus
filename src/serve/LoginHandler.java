package serve;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import misc.WebConstants;
import model.accounts.AccountManager;
import model.accounts.User;
import serve.managers.ManagerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static misc.WebConstants.MANAGER_FACTORY;
import static misc.WebConstants.SIGNED_ACCOUNT;

@WebServlet(name = "LoginHandler", urlPatterns = {"/tokensignin"})
public class LoginHandler extends HttpServlet {
    private static final String CLIENT_ID = "752594653432-dcqce0b92nbtce0d0ahpq91jfis07092.apps.googleusercontent.com";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idTokenString = request.getParameter("idtoken");
        HttpTransport transport = null;
        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            return;
        }
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
        try {
            User user = verifyLogin(verifier, idTokenString, response.getWriter(),
                    (ManagerFactory) getServletContext().getAttribute(MANAGER_FACTORY));
            if (user != null) {
                request.getSession().setAttribute(SIGNED_ACCOUNT, user);
                response.getWriter().println("signed in as " + user);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            response.getWriter().println("error");
        }
// (Receive idTokenString by HTTPS POST)


    }

    private User verifyLogin(GoogleIdTokenVerifier verifier, String idTokenString,
                             PrintWriter out, ManagerFactory factory) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(idTokenString);

        AccountManager accountManager = factory.getAccountManager();
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            if (!email.endsWith("freeuni.edu.ge") && !email.endsWith("agruni.edu.ge")) {
                out.println("only freeuni and agruni accouns are allowed.");
                return null;
            }
            // Use or store profile information
            // ...
            User user = accountManager.getUserViaEMail(email);
            if (user == null) {
                out.println("user not found, creating new one.");
                user = new User(-1, email, givenName, familyName, WebConstants.DEFAULT_USER_ROLE, pictureUrl);
                if (accountManager.add(user)) {
                    user = accountManager.getUserViaEMail(email);
                } else {
                    return null;
                }
            } else {
                out.println("found user");
            }
            return user;
        } else {
            out.println("Invalid ID token.");
            return null;
        }
    }
}
