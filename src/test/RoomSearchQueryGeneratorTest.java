package test;

import misc.DBInfo;
import model.rooms.Room;
import model.rooms.RoomSearchQueryGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.StringTokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RoomSearchQueryGeneratorTest {
    @Before
    public void setUp() throws Exception {
        DBInfo.class.newInstance();
    }

    @Test
    public void testQuery1() {
        //teting default query - should fetch all of the rooms.
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        String sql = "SELECT * FROM room";
        assertTrue(haveSameTokens(sql, query.generateQuery().getQuery()));
        query.setName("name");
        assertEquals(query.getName(), "name");
        query.setFloor(0);
        assertEquals(query.getFloor(), (Integer) 0);
        query.setAvailableForBooking(true);
        assertEquals(query.isAvailableForBooking(), true);
        query.setSeatType(Room.SeatType.DESKS);
        assertEquals(query.getSeatType(), Room.SeatType.DESKS);
        query.setRoomType(Room.RoomType.AUDITORIUM);
        assertEquals(query.getRoomType(), Room.RoomType.AUDITORIUM);
        query.setCapacityFrom(0);
        assertEquals(query.getCapacityFrom(), (Integer)0);
        query.setCapacityTo(10);
        assertEquals(query.getCapacityTo(), (Integer)10);
        query.setHasProblems(false);
        assertEquals(query.hasProblems(), false);
        sql += " \n WHERE ";
    }



    private Room mockRoom(int id) {
        return new Room(id, 0, null,
                null, null, true, 0);
    }

    private boolean haveSameTokens(String a, String b) {
        StringTokenizer tokenizerA = new StringTokenizer(a);
        StringTokenizer tokenizerB = new StringTokenizer(b);
        while (tokenizerA.hasMoreTokens()) {
            String aToken = tokenizerA.nextToken().toLowerCase();
            String bToken = tokenizerB.nextToken().toLowerCase();
            if (!aToken.equals(bToken)) {
                return false;
            }
        }
        return !tokenizerB.hasMoreTokens();
    }



}
