package model.campus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Shota on 6/12/2017.
 * <p>
 * The interface for generating SQL search queries.
 *
 * @param <T> the data type this interface is associated with.
 */
public interface CampusSearchQuery<T extends CampusObject> {
    String generateQuery();
}
