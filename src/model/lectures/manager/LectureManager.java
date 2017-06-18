package model.lectures.manager;

import java.util.List;

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
    
    /**
     * Returns number of subjects in the database (mostly needed for testing)
     */
    public int numOfSubjects();
    
    /**
     * Removes subject entry with given name from the database
     *
     * @param subject a subject to be removed
     */
    public void removeSubject(String string);
    
    /**
     * gets id of the given lecture from the database
     * @param lecture lecture to get id of
     */
    public int getLectureId(Lecture lecture);
}
