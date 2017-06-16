package model.lecture;

import misc.Utils;
import model.campus.CampusManager;
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
public class LectureManager implements CampusManager<Lecture, LectureSearchQuery> {
    /**
     * Adds a new subject entry to the database
     *
     * @param subject a subject to be added
     */
    public void addSubject(CampusSubject subject) {
        String sql = "INSERT  INTO campus_subject (subject_name) " +
                "VALUE (\'" + subject.getName() + "\')";
        try {
            DBConnector.executeUpdate(sql);
        } catch (SQLException e) {
            //ignored
        }
    }

    @Override
    public List<Lecture> find(LectureSearchQuery query) {
        List<Lecture> list = new ArrayList<>();
        try {
            ResultSet set = DBConnector.executeQuery(query.generateQuery());

            while (set.next()) {
                list.add(Utils.getLectureFromResults(set));
            }
        } catch (SQLException e) {
            //doing nothing
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
                lecture.getLecturer() + ", " + lecture.getRoom() + ", " + lecture.getSubject() + ", '" +
                lecture.getDay().name().toLowerCase() + "', '" + lecture.getStartTime() + "', " +
                lecture.getEndTime() + ") ";
        try {
            DBConnector.executeUpdate(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int lectureID) {
        String deleteQuery = "delete from " + SQL_TABLE_LECTURE + " where " + SQL_COLUMN_LECTURE_ID + " = " + lectureID;
        try {
            DBConnector.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
