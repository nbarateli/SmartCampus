
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
public interface CampusManager<T extends CampusObject, Q extends CampusSearchQuery<T>> {


    List<T> find(Q query);

    void add(T entity);

    void remove(int entityID);
}
