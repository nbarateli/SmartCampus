package model.lectures;

import model.campus.CampusManager;

public interface SubjectManager extends CampusManager<CampusSubject, SubjectSearchQueryGenerator> {
    
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

}
