package model.lectures;

import misc.ModelConstants;
import misc.Utils;
import model.campus.CampusSearchQuery;
import model.lectures.Lecture.WeekDay;

import java.sql.Time;

import static model.database.SQLConstants.*;

public class LectureSearchQuery implements CampusSearchQuery<Lecture> {
    private int lectureID;
    private int lecturerID;
    private int roomID;
    private int subjectID;
    private WeekDay day;
    private Time startTime;
    private Time endTime;

    public LectureSearchQuery(int lectureID, int lecturerID, int roomID, int subjectID,
                              WeekDay day, Time startTime, Time endTime) {
        this.lectureID = lectureID;
        this.lecturerID = lecturerID;
        this.roomID = roomID;
        this.subjectID = subjectID;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //Default Constructor:
    public LectureSearchQuery() {
        lectureID = ModelConstants.SENTINEL_INT;
        lecturerID = ModelConstants.SENTINEL_INT;
        roomID = ModelConstants.SENTINEL_INT;
        subjectID = ModelConstants.SENTINEL_INT;
        day = (WeekDay) ModelConstants.SENTINEL_PTR;
        startTime = (Time) ModelConstants.SENTINEL_PTR;
        endTime = (Time) ModelConstants.SENTINEL_PTR;
    }

    public int getLectureID() {
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

    public WeekDay getDay() {
        return day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    private boolean hasNonNullField() {
        return lectureID != ModelConstants.SENTINEL_INT || lecturerID != ModelConstants.SENTINEL_INT
                || roomID != ModelConstants.SENTINEL_INT || subjectID != ModelConstants.SENTINEL_INT
                || day != ModelConstants.SENTINEL_PTR || startTime != ModelConstants.SENTINEL_PTR
                || endTime != ModelConstants.SENTINEL_PTR;
    }

    private String generateEqualQuery(String field, String column) {
        return field == ModelConstants.SENTINEL_PTR ? "" : "" + column + " Like \'%" + field + "%\'";
    }

    private String generateEqualQuery(int field, String column) {
        return field == ModelConstants.SENTINEL_INT ? "" : "" + column + " = " + field;
    }

    public static void main(String[] args) {
        LectureSearchQuery bla = new LectureSearchQuery(1, 2, 3, 4, WeekDay.FRIDAY,
                new Time(100), new Time(200));
        System.out.println(bla.generateQuery());
    }

    @Override
    public String generateQuery() {
        return hasNonNullField() ? String.format(
                "SELECT * FROM %s JOIN %s ON %s.%s = %s.%s" +
                        " JOIN %s ON %s.%s = %s.%s " +
                        " JOIN %s ON %s.%s= %s.%s" +
                        " \nWHERE %s%s%s%s%s%s%s%s%s%s%s%s",
                SQL_TABLE_LECTURE, SQL_TABLE_ROOM, SQL_TABLE_LECTURE,
                SQL_COLUMN_LECTURE_ROOM, SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID,
                SQL_TABLE_USER, SQL_TABLE_LECTURE, SQL_COLUMN_LECTURE_LECTURER,
                SQL_TABLE_USER, SQL_COLUMN_USER_ID, SQL_TABLE_SUBJECT,
                SQL_TABLE_LECTURE, SQL_COLUMN_LECTURE_SUBJECT, SQL_TABLE_SUBJECT,
                SQL_COLUMN_SUBJECT_ID,
                generateEqualQuery(lectureID, SQL_COLUMN_LECTURE_ID),
                lectureID == ModelConstants.SENTINEL_INT ? "" : " AND \n",
                generateEqualQuery(lecturerID, SQL_COLUMN_LECTURE_LECTURER),
                lecturerID == ModelConstants.SENTINEL_INT ? "" : " AND \n",
                generateEqualQuery(roomID, SQL_TABLE_LECTURE + "." + SQL_COLUMN_ROOM_ID),
                roomID == ModelConstants.SENTINEL_INT ? "" : " AND \n",
                generateEqualQuery(subjectID, SQL_TABLE_LECTURE + "." + SQL_COLUMN_SUBJECT_ID),
                subjectID == ModelConstants.SENTINEL_INT ? "" : " AND \n",
                generateEqualQuery(day.name().toLowerCase(), SQL_COLUMN_LECTURE_DAY),
                day.ordinal() == ModelConstants.SENTINEL_INT ? "" : " AND \n",
                startTime == ModelConstants.SENTINEL_PTR ? "" :
                        "" + SQL_COLUMN_LECTURE_START_TIME + " >= " + Utils.toSqlTime(startTime)
                                + " AND \n",
                endTime == ModelConstants.SENTINEL_PTR ? "TRUE;" :
                        "" + SQL_COLUMN_LECTURE_END_TIME + " <= " + Utils.toSqlTime(endTime) + ";")
                :
                "SELECT * FROM lecture";
    }
}
