package serve.managers;

import model.campus.CampusSearchQuery;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.LectureManager;
import model.lectures.LectureSearchQueryGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

public class DefaultLectureManager implements LectureManager {

    private static LectureManager instance;


    private final DBConnector connector;

    DefaultLectureManager(DBConnector connector) {
        this.connector = connector;
    }

    @Override
    public void addSubject(String subjectName) {
        String sql = "INSERT INTO " + SQL_TABLE_SUBJECT + " (" + SQL_COLUMN_SUBJECT_NAME + ") " +
                "VALUES ('?')";
        Object[] values = {subjectName};

        try {
            //TODO
            connector.executeUpdate(sql, values);
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
            ResultSet res = connector.executeQuery(query);
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
            connector.executeUpdate(sql, subjectName);
        } catch (SQLException e) {
            //ignored
        }
    }

    @Override
    public int getLectureId(Lecture lecture) {
        LectureSearchQueryGenerator query = new LectureSearchQueryGenerator();
        query.setLecturerId(lecture.getLecturer().getId());
        query.setRoomID(lecture.getRoom().getId());
        query.setSubjectID(lecture.getSubject().getId());
        query.setDay(lecture.getDay());
        query.setStartTime(lecture.getStartTime());
        query.setEndTime(lecture.getEndTime());
        List<Lecture> lectures = find(query);

        return lectures.get(0).getId();
    }

    @Override
    public CampusSubject findSubject(String subjectName) {

        String sql = "SELECT * FROM " + SQL_TABLE_SUBJECT + " WHERE " + SQL_COLUMN_SUBJECT_NAME + " LIKE ?";
        Object[] values = {subjectName};
        try (ResultSet matches = connector.executeQuery(sql, values)) {


            if (matches.next()) {
                return getSubjectFromResults(matches);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeAllLectures() {
        String truncateQuery = "truncate table " + SQL_TABLE_LECTURE;
        try {
            //TODO
            connector.executeUpdate(truncateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Lecture> find(LectureSearchQueryGenerator queryGenerator) {
        List<Lecture> list = new ArrayList<>();
        try {
            //TODO
            CampusSearchQuery query = queryGenerator.generateQuery();
            ResultSet set = connector.executeQuery(query.getQuery(), query.getValues());

            while (set.next()) {
                list.add(getLectureFromResults(set));
            }
        } catch (SQLException e) {
            //doing nothing
        }
        return list;
    }

    @Override
    public boolean add(Lecture lecture) {
        String insertQuery = "insert into " + SQL_TABLE_LECTURE + " (" +
                SQL_COLUMN_LECTURE_LECTURER + ", " + SQL_COLUMN_LECTURE_ROOM + ", " +
                SQL_COLUMN_LECTURE_SUBJECT + ", " + SQL_COLUMN_LECTURE_DAY +
                ", " + SQL_COLUMN_LECTURE_START_TIME + ", " +
                SQL_COLUMN_LECTURE_END_TIME + ") values (" +
                lecture.getLecturer().getId() + ", " + lecture.getRoom().getId() + ", " +
                lecture.getSubject().getId() + ", '" +
                lecture.getDay().name().toLowerCase() + "', '" + lecture.getStartTime() + "', '" +
                lecture.getEndTime() + "') ";
        return successfulOperation(insertQuery, connector);
    }

    @Override
    public boolean remove(int entityId) {
        String deleteQuery = "delete from " + SQL_TABLE_LECTURE + " where "
                + SQL_COLUMN_LECTURE_ID + " = " + entityId;
        return successfulOperation(deleteQuery, connector);
    }


}
