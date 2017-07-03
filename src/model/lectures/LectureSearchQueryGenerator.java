package model.lectures;

import model.campus.CampusSearchQuery;
import model.campus.CampusSearchQueryGenerator;
import model.bookings.Booking.WeekDay;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

public class LectureSearchQueryGenerator implements CampusSearchQueryGenerator<Lecture> {
    private Integer lectureId;
    private Integer lecturerId;
    private Integer roomID;
    private Integer subjectID;
    private WeekDay day;
    private Time startTime;
    private Time endTime;

    public LectureSearchQueryGenerator(Integer lectureId, Integer lecturerId, Integer roomID, Integer subjectID,
                                       WeekDay day, Time startTime, Time endTime) {
        this.lectureId = lectureId;
        this.lecturerId = lecturerId;
        this.roomID = roomID;
        this.subjectID = subjectID;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //Default Constructor:
    public LectureSearchQueryGenerator() {
        this(null, null, null, null,
                null, null, null);
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public CampusSearchQuery generateQuery() {
        List<Object> values = new ArrayList<>();
        String sql = hasNonNullFields() ? String.format("SELECT * FROM %s JOIN %s ON %s.%s = %s.%s" +
                        " JOIN %s ON %s.%s = %s.%s " +
                        " JOIN %s ON %s.%s= %s.%s" +
                        " \nWHERE %s%s%s%s%s%s",
                SQL_TABLE_LECTURE, SQL_TABLE_ROOM, SQL_TABLE_LECTURE,
                SQL_COLUMN_LECTURE_ROOM, SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID,
                SQL_TABLE_USER, SQL_TABLE_LECTURE, SQL_COLUMN_LECTURE_LECTURER,
                SQL_TABLE_USER, SQL_COLUMN_USER_ID, SQL_TABLE_SUBJECT,
                SQL_TABLE_LECTURE, SQL_COLUMN_LECTURE_SUBJECT, SQL_TABLE_SUBJECT,
                SQL_COLUMN_SUBJECT_ID,
                generateEqualQuery(lectureId, SQL_COLUMN_LECTURE_ID, values) + " AND ",
                generateEqualQuery(lecturerId, SQL_COLUMN_LECTURE_LECTURER, values) + " AND ",
                generateEqualQuery(roomID, SQL_COLUMN_LECTURE_ROOM, values) + " AND ",
                generateLikeQuery(day, SQL_COLUMN_LECTURE_DAY, values) + " AND ",
                generateEqualsOrQuery(startTime, SQL_COLUMN_LECTURE_START_TIME, values, true) + " AND ",
                generateEqualsOrQuery(endTime, SQL_COLUMN_LECTURE_END_TIME, values, false)
        ) : "SELECT * FROM " + SQL_TABLE_LECTURE;
        return new CampusSearchQuery(sql, asArray(values));
    }

    private boolean hasNonNullFields() {
        return lectureId != null || lecturerId != null
                || roomID != null || subjectID != null
                || day != null || startTime != null
                || endTime != null;
    }


}
