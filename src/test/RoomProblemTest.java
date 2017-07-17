package test;

import model.accounts.User;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import model.rooms.RoomProblem;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoomProblemTest {

    private RoomProblem problem, problem1, problem2;
    private User user;
    private Room room;

    @Before
    public void createRoomProblem() {
        user = new User(-1, "mail", "firstName", "lastName", null, "");
        room = new Room(5, 5, "name", RoomType.AUDITORIUM, SeatType.DESKS, false, 5);
        problem = new RoomProblem(1, user, room, "title", "desc", new Date(500));
        problem1 = new RoomProblem(1, user, room, null, null, new Date(500));
        problem2 = new RoomProblem(1, user, room, null, null, new Date(500));
    }

    @Test
    public void test1() {
        assertEquals(1, problem.getId());
        assertEquals(user, problem.getAuthor());
        assertEquals(room, problem.getRoom());
        assertEquals("title", problem.getTitle());
        assertEquals("desc", problem.getDescription());
        assertEquals(500, problem.getDateCreated().getTime());
    }

    @Test
    public void test2() {
        assertEquals("", problem1.getTitle());
        assertEquals("", problem1.getDescription());
        assertTrue(problem1.equals(problem2));
    }
}
