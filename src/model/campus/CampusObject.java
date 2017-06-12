package model.campus;

/**
 * An interface for any ADT of campus database.
 */
public interface CampusObject {
    /**
     * Returns the ID associated with given object
     * (Usually the primary key in SQL database)
     *
     */
    int getID();
}
