package model.lecture;

import model.accounts.User;
import model.campus.CampusObject;
import model.rooms.Room;


import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Class for Lecture ADT (immutable)
 */
public class Lecture implements CampusObject {
    private final int lectureID;
    private final User lecturer;
    private final Room room;
    private final CampusSubject subject;
    private final WeekDay day;
    private final Time startTime;
    private final Time endTime;

    public Lecture(int lectureID, User lecturer, Room room, CampusSubject subject, WeekDay day, Time startTime, Time endTime) {
        this.lectureID = lectureID;
        this.lecturer = lecturer;
        this.room = room;
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getID() {
        return lectureID;
    }


    public User getLecturer() {
        return lecturer;
    }

    public Room getRoom() {
        return room;
    }

    public CampusSubject getSubject() {
        return subject;
    }

    public Time getEndTime() {
        return new Time(endTime.getTime());
    }

    public Time getStartTime() {
        return new Time(startTime.getTime());
    }

    public WeekDay getDay() {
        return day;
    }

    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        return "Lecture{" + "\n"
                + subject.getName() + ": ლექტორი: " + lecturer.getFirstName() + " " +
                lecturer.getLastName() + " დღე: " + day + " დასაწყისი " + format.format(startTime) +
                " აუდიტორია " + room.getRoomName() +
                "}";
    }

    public enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
