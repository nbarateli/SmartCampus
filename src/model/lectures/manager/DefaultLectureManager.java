package model.lectures.manager;

import static model.database.SQLConstants.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import misc.Utils;
import model.database.DBConnector;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.LectureSearchQuery;

public class DefaultLectureManager implements LectureManager {
	private static LectureManager instance;
	/***/
	public static LectureManager getInstance(){
		return instance == null ? instance = new DefaultLectureManager() : instance ;
	}
	
	public void addSubject(String subjectName) {
        String sql = "INSERT  INTO campus_subject (subject_name) " +
                "VALUES (\'" + subjectName + "\')";
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
    
    /**
     * returns number of lectures in the database
     */
	@Override
    public int size() {
        String query = "select * from " + SQL_TABLE_LECTURE;
        int count = 0;
        try {
            ResultSet res = DBConnector.executeQuery(query);
            while(res.next())
                count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
	
	
    /**
     * returns number of subjects in the database
     */
	@Override
    public int numOfSubjects() {
        String query = "select * from " + SQL_TABLE_SUBJECT;
        int count = 0;
        try {
            ResultSet res = DBConnector.executeQuery(query);
            while(res.next())
                count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
