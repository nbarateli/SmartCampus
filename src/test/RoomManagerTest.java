package test;

import org.junit.Before;
import org.junit.Test;

import model.database.DBConnector;
import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;
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
		RoomSearchQuery query = new RoomSearchQuery();
		List<Room> list =  manager.find(query);
		int x = list.size();
		Room room1 = new Room(-1, 50, "100", RoomType.AUDITORIUM, SeatType.DESKS, true, 1);
		Room room2 = new Room(-1, 150, "101", RoomType.UTILITY, SeatType.COMPUTERS, false, 1);
		Room room3 = new Room(-1, 90, "102", RoomType.AUDITORIUM, SeatType.DESKS, true, 1);
		manager.add(room1);
		list = manager.find(query);
		int y = list.size();
		assertEquals(x + 1, y);
		manager.add(room2);
		list = manager.find(query);
		y = list.size();
		assertEquals(x + 2, y);
		manager.add(room3);
		list = manager.find(query);
		y = list.size();
		assertEquals(x + 3, y);
		
	}
	
	@Test
	public void test2() {
		RoomSearchQuery query = new RoomSearchQuery();
		List<Room> list =  manager.find(query);
		int x = list.size();
		manager.remove(manager.getRoomByName("100").getID());
		list =  manager.find(query);
		int y = list.size();
		assertEquals(x - 1, y);
		manager.remove(manager.getRoomByName("101").getID());
		list =  manager.find(query);
		y = list.size();
		assertEquals(x - 2, y);
		manager.remove(manager.getRoomByName("102").getID());
		list =  manager.find(query);
		y = list.size();
		assertEquals(x - 3, y);
	}
	
	@Test
	public void test3() {
		RoomSearchQuery query = new RoomSearchQuery();
		List<Room> list =  manager.find(query);
		Room room1 = list.get(0);
		//System.out.println(room1.getID() + " " + room1.getRoomType());
		List<String> listOfImages =  manager.getAllImagesOf(room1);
		int x = listOfImages.size();
		manager.addImage(room1, "test24.jpg");
		listOfImages =  manager.getAllImagesOf(room1);
		int y = listOfImages.size();
		//System.out.println(x+1 + " " + y);
		assertEquals(x+1, y);
		manager.addImage(room1, "test2");
		listOfImages =  manager.getAllImagesOf(room1);
		y = listOfImages.size();
		//System.out.println(x+2 + " " + y);
		assertEquals(x+2, y);
		manager.addImage(room1, "test3");
		listOfImages =  manager.getAllImagesOf(room1);
		y = listOfImages.size();
		//System.out.println(x+3 + " " + y);
		assertEquals(x+3, y);
	}
	
	@Test
	public void test4() {
		RoomSearchQuery query = new RoomSearchQuery();
		List<Room> list =  manager.find(query);
		Room room1 = list.get(0);
		List<Lecture> lecturesList = manager.findAllLecturesAt(room1);
		List<Lecture> lecturesList2 = manager.findAllLecturesAt(room1, WeekDay.MONDAY);
		lecturesList2.addAll(manager.findAllLecturesAt(room1, WeekDay.TUESDAY));
		lecturesList2.addAll(manager.findAllLecturesAt(room1, WeekDay.WEDNESDAY));
		lecturesList2.addAll(manager.findAllLecturesAt(room1, WeekDay.THURSDAY));
		lecturesList2.addAll(manager.findAllLecturesAt(room1, WeekDay.FRIDAY));
		lecturesList2.addAll(manager.findAllLecturesAt(room1, WeekDay.SATURDAY));
		lecturesList2.addAll(manager.findAllLecturesAt(room1, WeekDay.SUNDAY));
		assertTrue(lecturesList.equals(lecturesList2));
	}

}
