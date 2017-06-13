package model.lecture;

import model.campus.CampusObject;

import java.sql.Time;

/**
 * Class for Lecture ADT (immutable)
 */
public class Lecture implements CampusObject {
    private final int lectureID;
    private final int lecturerID;
    private final int roomID;
    private final int subjectID;
    private final WeekDay day;
    private final Time startTime;
    private final Time endTime;

    public Lecture(int lectureID, int lecturerID, int roomID, int subjectID, WeekDay day, Time startTime, Time endTime) {
        this.lectureID = lectureID;
        this.lecturerID = lecturerID;
        this.roomID = roomID;
        this.subjectID = subjectID;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getID() {
        return lectureID;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getSubjectID() {
        return subjectID;
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

    public enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
