package model.campus;

/**
 * Created by Shota on 6/12/2017.
 * <p>
 * The interface for generating SQL search queries.
 *
 * @param <T> the data type this interface is associated with.
 */
public interface CampusSearchQueryGenerator<T extends CampusObject> {

    CampusSearchQuery<T> generateQuery();

}
