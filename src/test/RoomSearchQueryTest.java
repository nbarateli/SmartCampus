package test;

import misc.DBInfo;
import model.rooms.Room;
import model.rooms.RoomSearchQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static model.rooms.manager.DefaultRoomManager.findRooms;
import static org.junit.Assert.*;


public class RoomSearchQueryTest {
    @Before
    public void setUp() throws Exception {
        DBInfo.class.newInstance();
    }

    @Test
    public void testQuery1() {
        //teting default query - should fetch all of the rooms.
        RoomSearchQuery query = new RoomSearchQuery();
        String sql = "SELECT * FROM  room";
        List<Room> expected = findRooms(sql);
        List<Room> actual = findRooms(query.generateQuery());
        assertEquals(expected.toString(), actual.toString());


    }


}
