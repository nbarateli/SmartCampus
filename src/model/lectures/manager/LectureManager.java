package model.lectures.manager;

import model.campus.CampusManager;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.LectureSearchQuery;

public interface LectureManager extends CampusManager<Lecture, LectureSearchQuery> {
	/**
     * Adds a new subject entry to the database
     *
     * @param subject a subject to be added
     */
    public void addSubject(String subjectName);
    
    public int numOfSubjects();
}
