package test;

import misc.Utils;
import model.bookings.Booking;
import model.bookings.Booking.WeekDay;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import model.rooms.RoomProblem;
import model.rooms.RoomSearchQueryGenerator;
import org.junit.Before;
import org.junit.Test;
import serve.managers.DefaultRoomManager;
import serve.managers.ManagerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoomManagerTest {
    private DefaultRoomManager manager;
    private Room room;

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
        Room room1 = new Room(-1, 50, "500", RoomType.AUDITORIUM, SeatType.DESKS, true, 1);
        Room room2 = new Room(-1, 150, "501", RoomType.UTILITY, SeatType.COMPUTERS, false, 1);
        Room room3 = new Room(-1, 90, "502", RoomType.AUDITORIUM, SeatType.DESKS, true, 1);
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
        manager.remove(manager.getRoomByName("500").getId());
        list = manager.find(query);
        int y = list.size();
        assertEquals(x - 1, y);
        manager.remove(manager.getRoomByName("501").getId());
        list = manager.find(query);
        y = list.size();
        assertEquals(x - 2, y);
        manager.remove(manager.getRoomByName("502").getId());
        list = manager.find(query);
        y = list.size();
        assertEquals(x - 3, y);
    }

    /*
     * Test3
     * Tests methods find, addImage, removeImage and getAllImagesOf of RoomManagerClass
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
        room = room1;

        x = listOfImages.size();
        manager.removeImage(room1, "test24.jpg");
        listOfImages = manager.getAllImagesOf(room1);
        y = listOfImages.size();
        assertEquals(x - 1, y);
        manager.removeImage(room1, "test2");
        listOfImages = manager.getAllImagesOf(room1);
        y = listOfImages.size();
        assertEquals(x - 2, y);
        manager.removeImage(room1, "test3");
        listOfImages = manager.getAllImagesOf(room1);
        y = listOfImages.size();
        assertEquals(x - 3, y);
    }


    /*
     * Test5
     * Tests methods find, findAllLecturesAt and addAll of RoomManagerClass
     */
    @Test
    public void test5() {
        RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
        List<Room> list = manager.find(query);
        Room room1 = list.get(0);
        List<Booking> lecturesList2 = manager.findAllBookingsAt(room1, WeekDay.MONDAY);
        List<Booking> lecturesList = manager.findAllBookingsAt(room1);
        lecturesList2.addAll(manager.findAllBookingsAt(room1, WeekDay.TUESDAY));
        lecturesList2.addAll(manager.findAllBookingsAt(room1, WeekDay.WEDNESDAY));
        lecturesList2.addAll(manager.findAllBookingsAt(room1, WeekDay.THURSDAY));
        lecturesList2.addAll(manager.findAllBookingsAt(room1, WeekDay.FRIDAY));
        lecturesList2.addAll(manager.findAllBookingsAt(room1, WeekDay.SATURDAY));
        lecturesList2.addAll(manager.findAllBookingsAt(room1, WeekDay.SUNDAY));
        assertTrue(lecturesList.equals(lecturesList2));
    }

    /*
     * Test6
     * Tests methods findAllLecturesAt and getRoomById of RoomManagerClass
     */
    @Test
    public void test6() {
        Room room1 = manager.getRoomById(4);
        List<Booking> lecturesList = manager.findAllBookingsAt(room1);
        if(lecturesList.size() > 0){
            Booking lec = lecturesList.get(0);

        List<Booking> lecturesList2 = manager.findAllBookingsAt(room1,
                WeekDay.values()[Utils.getWeekDay(lec.getBookingDate())],
                lec.getStartTime(), lec.getEndTime());
        assertEquals(lecturesList2.size(), 1);
        assertTrue(lecturesList2.get(0).equals(lec));
        }
    }

    /*
     * Test7
     * Tests methods findAllLecturesAt and getRoomByName of RoomManagerClass
     */
    @Test
    public void test7() {
        Room room1 = manager.getRoomByName("408");
        List<Booking> lecturesList = manager.findAllBookingsAt(room1);
        if(lecturesList.size() > 0) {
            Booking lec = lecturesList.get(0);
            List<Booking> lecturesList2 = manager.findAllBookingsAt(room1,
                    WeekDay.values()[Utils.getWeekDay(lec.getBookingDate())], lec.getStartTime(), lec.getEndTime());
            assertEquals(lecturesList2.size(), 1);
            assertTrue(lecturesList2.get(0).equals(lec));
        }
    }

    /*
     * Test8
     * Tests findAllProblemsOf and getRoomByName method of RoomManager
     */
    @Test
    public void test8() {
        List<RoomProblem> myList = manager.findAllProblemsOf(manager.getRoomById(1));
        List<RoomProblem> myList1 = manager.findAllProblemsOf(manager.getRoomByName(manager.getRoomById(1).getRoomName()));
        assertTrue(myList.equals(myList1));
    }
}
