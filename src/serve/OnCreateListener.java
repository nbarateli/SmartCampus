package serve;
/**
 * Created by Niko on 10.06.2017.
 * <p>
 * Responsible for initializing and storing all the necessary objects for the server.
 */

import misc.DBInfo;
import model.accounts.DefaultAccountManager;
import model.lectures.manager.DefaultLectureManager;
import model.lectures.manager.LectureManager;
import model.rooms.manager.DefaultRoomManager;
import model.rooms.manager.RoomManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static misc.WebConstants.*;

@WebListener()
public class OnCreateListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public OnCreateListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        try {
            DBInfo.class.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        RoomManager roomManager = DefaultRoomManager.getInstance();
        LectureManager lectureManager = DefaultLectureManager.getInstance();
        ServletContext context = sce.getServletContext();

        context.setAttribute(ROOM_MANAGER, roomManager);
        context.setAttribute(LECTURE_MANAGER, lectureManager);
        context.setAttribute(ACCOUNT_MANAGER, DefaultAccountManager.getInstance());

    }	

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
