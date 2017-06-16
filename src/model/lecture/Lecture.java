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
   
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lecture other = (Lecture) obj;
        if (day != other.day)
            return false;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (lectureID != other.lectureID)
            return false;
        if (lecturer == null) {
            if (other.lecturer != null)
                return false;
        } else if (!lecturer.equals(other.lecturer))
            return false;
        if (room == null) {
            if (other.room != null)
                return false;
        } else if (!room.equals(other.room))
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        return true;
    }

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
