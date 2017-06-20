package model.lectures.manager;

import model.database.DBConnector;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.LectureSearchQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static misc.Utils.getLectureFromResults;
import static misc.Utils.getSubjectFromResults;
import static model.database.SQLConstants.*;

public class DefaultLectureManager implements LectureManager {

    private static LectureManager instance;

    /***/
    public static LectureManager getInstance() {
        return instance == null ? instance = new DefaultLectureManager() : instance;
    }

    /**
     * returns the list of lectures Fetched by given SQL query
     *
     * @param sql
     */
    public static List<Lecture> findLectures(String sql) {
        List<Lecture> lectures = new ArrayList<>();
        try {
            //TODO
            ResultSet matches = DBConnector.executeQuery(sql, null);
            while (matches.next()) {
                lectures.add(getLectureFromResults(matches));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lectures;
    }

    @Override
    public void addSubject(String subjectName) {
        String sql = "INSERT INTO " + SQL_TABLE_SUBJECT + " (" + SQL_COLUMN_SUBJECT_NAME + ") " +
                "VALUES (\'" + subjectName + "\')";
        try {
            //TODO
            DBConnector.executeUpdate(sql, null);
        } catch (SQLException e) {
            //ignored
        }
    }

    /**
     * returns number of subjects in the database
     */
    @Override
    public int numOfSubjects() {
        String query = "select * from " + SQL_TABLE_SUBJECT;
        int count = 0;
        try {
            //TODO
            ResultSet res = DBConnector.executeQuery(query, null);
            while (res.next())
                count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void removeSubject(String subjectName) {
        String sql = "DELETE FROM " + SQL_TABLE_SUBJECT + " WHERE " +
                SQL_COLUMN_SUBJECT_NAME + " = '?'";
        try {
            //TODO
            DBConnector.executeUpdate(sql, new Object[]{subjectName});
        } catch (SQLException e) {
            //ignored
        }
    }

    @Override
    public int getLectureId(Lecture lecture) {
        LectureSearchQuery query = new LectureSearchQuery();
        query.setLecturerID(lecture.getLecturer().getID());
        query.setRoomID(lecture.getRoom().getID());
        query.setSubjectID(lecture.getSubject().getID());
        query.setDay(lecture.getDay());
        query.setStartTime(lecture.getStartTime());
        query.setEndTime(lecture.getEndTime());
        List<Lecture> lectures = find(query);

        return lectures.get(0).getID();
    }

    /**
     * returns the list of subjects Fetched by given SQL query
     *
     * @param subjectName name of the subject to find
     */
    public CampusSubject findSubject(String subjectName) {

        String sql = "SELECT * FROM " + SQL_TABLE_SUBJECT + " WHERE " + SQL_COLUMN_SUBJECT_NAME + " LIKE ?";
        Object[] values = {subjectName};
        try (ResultSet matches = DBConnector.executeQuery(sql, values)) {


            if (matches.next()) {
                return getSubjectFromResults(matches);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Lecture> find(LectureSearchQuery query) {
        List<Lecture> list = new ArrayList<>();
        try {
            //TODO
            ResultSet set = DBConnector.executeQuery(query.generateQuery(), null);

            while (set.next()) {
                list.add(getLectureFromResults(set));
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
                lecture.getLecturer().getID() + ", " + lecture.getRoom().getID() + ", " +
                lecture.getSubject().getID() + ", '" +
                lecture.getDay().name().toLowerCase() + "', '" + lecture.getStartTime() + "', '" +
                lecture.getEndTime() + "') ";
        try {
            DBConnector.executeUpdate(insertQuery, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int lectureID) {
        String deleteQuery = "delete from " + SQL_TABLE_LECTURE + " where "
                + SQL_COLUMN_LECTURE_ID + " = " + lectureID;
        try {
            //TODO
            DBConnector.executeUpdate(deleteQuery, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns number of lectures in the database
     */
    @Override
    public int size() {
        String query = "select * from " + SQL_TABLE_LECTURE;
        int count = 0;
        try {
            ResultSet res = DBConnector.executeQuery(query, null);
            while (res.next())
                count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}
