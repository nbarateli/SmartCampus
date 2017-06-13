package model.lecture;

import misc.Utils;
import model.campus.CampusManager;
import model.campus.CampusSearchQuery;
import model.database.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static model.database.SQLConstants.*;

/**
 * Created by Shota on 6/13/2017.
 * <p>
 * LectureManager class overrides base functionality of CampusManager
 * and is responsible for finding, adding and removing lecture entities.
 * NOTE: THIS CLASS IS NOT TESTED YET.
 */
public class LectureManager implements CampusManager<Lecture> {

    @Override
    public List<Lecture> find(CampusSearchQuery query) {
        List<Lecture> list = new ArrayList<>();
        Connection con = null;
        try {
            ResultSet set = DBConnector.executeQuery(query.generateQuery());
            con = set.getStatement().getConnection();
            while (set.next()) {
                int lectureID = set.getInt(SQL_COLUMN_LECTURE_ID);
                int lecturerID = set.getInt(SQL_COLUMN_LECTURE_LECTURER);
                int roomID = set.getInt(SQL_COLUMN_LECTURE_ROOM);
                int subjectID = set.getInt(SQL_COLUMN_LECTURE_SUBJECT);
                Lecture.WeekDays day = Utils.toWeekDay(set.getString(SQL_COLUMN_LECTURE_DAY));
                Time startTime = set.getTime(SQL_COLUMN_LECTURE_START_TIME);
                Time endTime = set.getTime(SQL_COLUMN_LECTURE_END_TIME);
                Lecture lecture = new Lecture(lectureID, lecturerID, roomID, subjectID, day, startTime, endTime);
                list.add(lecture);
            }
        } catch (SQLException e) {
            //doing nothing
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException e) {
                //doing nothing
            }
        }
        return list;
    }

    @Override
    public void add(Lecture lecture) {
        String insertQuery = "insert into " + SQL_TABLE_LECTURE + " (" +
                SQL_COLUMN_LECTURE_LECTURER + ", " + SQL_COLUMN_LECTURE_ROOM + ", " +
                SQL_COLUMN_LECTURE_SUBJECT + ", " + SQL_COLUMN_LECTURE_DAY +
                ", " + SQL_COLUMN_LECTURE_START_TIME + ", " +
                SQL_COLUMN_LECTURE_END_TIME + ") values (" +
                lecture.getLecturerID() + ", " + lecture.getRoomID() + ", " + lecture.getSubjectID() + ", '" +
                lecture.getDay().name().toLowerCase() + "', '" + lecture.getStartTime() + "', " +
                lecture.getEndTime() + ") ";
        try {
            DBConnector.executeUpdate(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Lecture lecture) {
        String deleteQuery = "delete from " + SQL_TABLE_LECTURE + " where " + SQL_COLUMN_LECTURE_ID + " = " + lecture.getID();
        try {
            DBConnector.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
