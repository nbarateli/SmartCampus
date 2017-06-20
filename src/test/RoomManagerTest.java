package test;

import model.rooms.manager.DefaultRoomManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoomManagerTest {
    private DefaultRoomManager manager;

    @Before
    public void setUp() {
        manager = (DefaultRoomManager) DefaultRoomManager.getInstance();
    }

    @Test
    public void test1() {
        assertEquals(manager, manager);
    }
}
