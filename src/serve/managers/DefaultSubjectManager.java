package serve.managers;

import model.campus.CampusSearchQuery;
import model.lectures.CampusSubject;
import model.lectures.SubjectManager;
import model.lectures.SubjectSearchQueryGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static misc.Utils.getSubjectFromResults;
import static misc.Utils.successfulOperation;
import static model.database.SQLConstants.*;

public class DefaultSubjectManager implements SubjectManager {

    private static SubjectManager instance;

    private final DBConnector connector;

    DefaultSubjectManager(DBConnector connector) {
        this.connector = connector;
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
    public List<CampusSubject> getAllSubjects() {
        List<CampusSubject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM " + SQL_TABLE_SUBJECT;
        try (ResultSet results = connector.executeQuery(sql)) {
            while (results.next()) {
                subjects.add(new CampusSubject(
                        results.getInt(SQL_COLUMN_SUBJECT_ID),
                        results.getString(SQL_COLUMN_SUBJECT_NAME))
                );
            }
        } catch (Exception e) {
            //ignored
        }

        return subjects;
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
    public CampusSubject getSubjectByName(String subjectName) {
        if (subjectName == null) {
            return null;
        }
        String sql = String.format("SELECT * FROM %s \n" +
                "WHERE %s LIKE ?", SQL_TABLE_SUBJECT, SQL_COLUMN_SUBJECT_NAME);
        try (ResultSet results = connector.executeQuery(sql, subjectName)) {
            if (results.next()) {
                return getSubjectFromResults(results);
            }
        } catch (Exception e) {
            //ignored
        }
        return null;
    }

    @Override
    public List<CampusSubject> find(SubjectSearchQueryGenerator queryGenerator) {

        List<CampusSubject> CampusSubject = new ArrayList<>();
        CampusSearchQuery query = queryGenerator.generateQuery();
        try (ResultSet matches = connector.executeQuery(query.getQuery(), query.getValues())) {
            while (matches.next()) {
                CampusSubject.add(getSubjectFromResults(matches));
            }
        } catch (SQLException e) {
            //doing nothing
        }

        return CampusSubject;
    }

    @Override
    public boolean add(CampusSubject subject) {
        String sql = "INSERT INTO " + SQL_TABLE_SUBJECT + " (" + SQL_COLUMN_SUBJECT_NAME + ") " +
                "VALUES (\'" + subject.getName() + "\')";
        try {
            //TODO
            connector.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            //ignored
            return false;
        }
    }

    @Override
    public boolean remove(int entityId) {
        String deleteQuery = "delete from " + SQL_TABLE_LECTURE + " where "
                + SQL_COLUMN_LECTURE_ID + " = " + entityId;
        return successfulOperation(deleteQuery, connector);
    }
}
