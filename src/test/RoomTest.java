package test;

import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoomTest {

    private Room room1, room2;
    
    @Before 
    public void setUp() {
        room1 = new Room(1, 2, "name1", RoomType.AUDITORIUM, SeatType.DESKS, false, 5);
        room2 = new Room(3, 4, "name2", RoomType.UTILITY, SeatType.COMPUTERS, true, 3);
    }
    
    @Test
    public void test1() {
        assertEquals(1, room1.getId());
        assertEquals(2, room1.getCapacity());
        assertEquals("name1", room1.getRoomName());
        assertEquals(RoomType.AUDITORIUM, room1.getRoomType());
        assertEquals(SeatType.DESKS, room1.getSeatType());
        assertEquals(false, room1.isAvailableForStudents());
        assertEquals(5, room1.getFloor());
    }
    
    @Test
    public void test2() {
        assertEquals(3, room2.getId());
        assertEquals(4, room2.getCapacity());
        assertEquals("name2", room2.getRoomName());
        assertEquals(RoomType.UTILITY, room2.getRoomType());
        assertEquals(SeatType.COMPUTERS, room2.getSeatType());
        assertEquals(true, room2.isAvailableForStudents());
        assertEquals(3, room2.getFloor());
    }

}
