package test;

import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;
import model.managers.DefaultRoomManager;
import model.managers.ManagerFactory;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import model.rooms.RoomSearchQueryGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoomManagerTest {
    private DefaultRoomManager manager;

    @Before
    public void setUp() {
        manager = (DefaultRoomManager) new ManagerFactory().getRoomManager();
    }

    /*
     * Test1
     * Tests methods find and add of RoomManagerClass
     */
    @Test
    public void test1() {
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        List<Room> list = manager.find(query);
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

    /*
     * Test2
     * Tests methods find, remove and getRoomByName of RoomManagerClass
     */
    @Test
    public void test2() {
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        List<Room> list = manager.find(query);
        int x = list.size();
        manager.remove(manager.getRoomByName("100").getId());
        list = manager.find(query);
        int y = list.size();
        assertEquals(x - 1, y);
        manager.remove(manager.getRoomByName("101").getId());
        list = manager.find(query);
        y = list.size();
        assertEquals(x - 2, y);
        manager.remove(manager.getRoomByName("102").getId());
        list = manager.find(query);
        y = list.size();
        assertEquals(x - 3, y);
    }

    /*
     * Test3
     * Tests methods find, addImage and getAllImagesOf of RoomManagerClass
     */
    @Test
    public void test3() {
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        List<Room> list = manager.find(query);
        Room room1 = list.get(0);
        List<String> listOfImages = manager.getAllImagesOf(room1);
        int x = listOfImages.size();
        manager.addImage(room1, "test24.jpg");
        listOfImages = manager.getAllImagesOf(room1);
        int y = listOfImages.size();
        assertEquals(x + 1, y);
        manager.addImage(room1, "test2");
        listOfImages = manager.getAllImagesOf(room1);
        y = listOfImages.size();
        assertEquals(x + 2, y);
        manager.addImage(room1, "test3");
        listOfImages = manager.getAllImagesOf(room1);
        y = listOfImages.size();
        assertEquals(x + 3, y);
    }

    /*
     * Test4
     * Tests methods find, findAllLecturesAt and addAll of RoomManagerClass
     */
    @Test
    public void test4() {
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        List<Room> list = manager.find(query);
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

    /*
     * Test5
     * Tests methods findAllLecturesAt and getRoomById of RoomManagerClass
     */
    @Test
    public void test5() {
        Room room1 = manager.getRoomById(4);
        List<Lecture> lecturesList = manager.findAllLecturesAt(room1);
        Lecture lec = lecturesList.get(0);
        List<Lecture> lecturesList2 = manager.findAllLecturesAt(room1, lec.getDay(), lec.getStartTime(), lec.getEndTime());
        assertEquals(lecturesList2.size(), 1);
        assertTrue(lecturesList2.get(0).equals(lec));
    }

    /*
     * Test6
     * Tests methods findAllLecturesAt and getRoomByName of RoomManagerClass
     */
    @Test
    public void test6() {
        Room room1 = manager.getRoomByName("408");
        List<Lecture> lecturesList = manager.findAllLecturesAt(room1);
        Lecture lec = lecturesList.get(0);
        List<Lecture> lecturesList2 = manager.findAllLecturesAt(room1, lec.getDay(), lec.getStartTime(), lec.getEndTime());
        assertEquals(lecturesList2.size(), 1);
        assertTrue(lecturesList2.get(0).equals(lec));
    }
}
