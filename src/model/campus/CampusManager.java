
package model.campus;


import java.util.List;

/**
 * Created by Shota on 6/12/2017.
 * <p>
 * The interface for all DAO-s that store and modify data in the database.
 *
 * @param <T> an abstract data type (database entry) the manager will store and access.
 * @param <Q> a SQL query generator related to the given data type.
 */
public interface CampusManager<T extends CampusObject, Q extends CampusSearchQueryGenerator<T>> {

    /**
     * searches for T objects (child of CampusObject) that users requirements,
     * which are already generated in given Q object (child of CampusSearchQueryGenerator)
     * @param queryGenerator T objects should be searched for according to this queryGenerator
     * @return list of found T objects
     */
    List<T> find(Q queryGenerator);

    /**
     * inserts given entity to needed table in database
     * @param entity entity needed to be added
     * @return true if entity was added successfully
     */
    boolean add(T entity);

    /**
     * removes entry with given id from needed table in database
     * @param entityId id of the entry needed to be removed
     * @return true if entry was removed successfully
     */
    boolean remove(int entityId);

}
