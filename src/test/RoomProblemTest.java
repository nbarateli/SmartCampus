package test;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;

import model.accounts.User;
import model.accounts.User.UserRole;
import model.accounts.User.UserStatus;
import model.accounts.User.UserType;
import model.rooms.Room;
import model.rooms.RoomProblem;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;

import static org.junit.Assert.*;

public class RoomProblemTest {

    private RoomProblem problem;
    private User user;
    private Room room;
    
    @Before 
    public void createRoomProblem() {
        user = new User(-1, "mail", "firstName", "lastName", UserStatus.ACTIVE,
                UserType.USER, UserRole.STUDENT);
        room = new Room(5, 5, "name", RoomType.AUDITORIUM, SeatType.DESKS, false, 5);
        problem = new RoomProblem(1, user, room, "title", "desc", new Date(500));
    }
    
    @Test
    public void test() {
        assertEquals(1, problem.getID());
        assertEquals(user, problem.getAuthor());
        assertEquals(room, problem.getRoom());
        assertEquals("title", problem.getTitle());
        assertEquals("desc", problem.getDescription());
        assertEquals(500, problem.getDateCreated().getTime());
    }
}
