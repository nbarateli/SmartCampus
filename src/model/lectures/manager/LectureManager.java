package model.lectures.manager;

import model.campus.CampusManager;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.LectureSearchQuery;

public interface LectureManager extends CampusManager<Lecture, LectureSearchQuery> {
	/**
     * Adds a new subject entry to the database
     *
     * @param subjectName a subject to be added
     */
    void addSubject(String subjectName);
    
    /**
     * Returns number of subjects in the database (mostly needed for testing)
     */
    int numOfSubjects();
    
    /**
     * Removes subject entry with given name from the database
     *
     * @param subject a subject to be removed
     */
    void removeSubject(String subject);
    
    /**
     * gets id of the given lecture from the database
     * 
     * @param lecture lecture to get id of
     */
    int getLectureId(Lecture lecture);
    
    /**
     * returns the list of subjects Fetched by given SQL query
     *
     * @param subjectName name of the subject to find
     */
    CampusSubject findSubject(String subjectName);
    
    /**
     * removes all lectures from database
     */
    void removeAllLectures();
}
