package model.lectures;

import model.campus.CampusManager;

import java.util.List;

public interface SubjectManager extends CampusManager<CampusSubject, SubjectSearchQueryGenerator> {

    /**
     * Returns number of subjects in the database (mostly needed for testing)
     */
    int numOfSubjects();

    /**
     * Returns all the subjects in database
     *
     * @return a {@code <List>} of subjects.
     */
    List<CampusSubject> getAllSubjects();

    /**
     * Removes subject entry with given name from the database
     *
     * @param subject a subject to be removed
     */
    void removeSubject(String subject);

}
