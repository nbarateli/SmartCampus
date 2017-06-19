package test;

import org.junit.Before;
import org.junit.Test;

import model.database.DBConnector;
import model.rooms.Room;
import model.rooms.RoomSearchQuery;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import model.rooms.manager.DefaultRoomManager;
import model.rooms.manager.RoomManager;

import static misc.Utils.getRoomFromResults;
import static model.database.SQLConstants.SQL_COLUMN_ROOM_AVAILABLE;
import static model.database.SQLConstants.SQL_COLUMN_ROOM_CAPACITY;
import static model.database.SQLConstants.SQL_COLUMN_ROOM_FLOOR;
import static model.database.SQLConstants.SQL_COLUMN_ROOM_NAME;
import static model.database.SQLConstants.SQL_COLUMN_ROOM_SEAT_TYPE;
import static model.database.SQLConstants.SQL_COLUMN_ROOM_TYPE;
import static model.database.SQLConstants.SQL_TABLE_ROOM;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomManagerTest {
	DefaultRoomManager manager;
	
	@Before 
    public void setUp() {
        manager = (DefaultRoomManager) DefaultRoomManager.getInstance();
    }
	@Test
	public void test1() {
		String query = "SELECT * FROM " + SQL_TABLE_ROOM;
		List<Room> list =  DefaultRoomManager.findRooms(query);
		int x = list.size();
		Room room1 = new Room(21, 50, "100", RoomType.AUDITORIUM, SeatType.DESKS, true, 1);
		Room room2 = new Room(22, 150, "101", RoomType.UTILITY, SeatType.COMPUTERS, false, 1);
		Room room3 = new Room(23, 90, "102", RoomType.AUDITORIUM, SeatType.DESKS, true, 1);
		manager.add(room1);
		list = DefaultRoomManager.findRooms(query);
		int y = list.size();
		assertEquals(x + 1, y);
		manager.add(room2);
		list = DefaultRoomManager.findRooms(query);
		y = list.size();
		assertEquals(x + 2, y);
		manager.add(room3);
		list = DefaultRoomManager.findRooms(query);
		y = list.size();
		assertEquals(x + 3, y);
	}
	
	@Test
	public void test2() {
		String query = "SELECT * FROM " + SQL_TABLE_ROOM;
		List<Room> list =  DefaultRoomManager.findRooms(query);
		int x = list.size();
		manager.remove(14);
		list =  DefaultRoomManager.findRooms(query);
		int y = list.size();
		assertEquals(x - 1, y);
		manager.remove(15);
		list =  DefaultRoomManager.findRooms(query);
		y = list.size();
		assertEquals(x - 2, y);
		manager.remove(16);
		list =  DefaultRoomManager.findRooms(query);
		y = list.size();
		assertEquals(x - 3, y);
	}
	
//	@Test
//	public void test3() {
//		 
//	}

}
