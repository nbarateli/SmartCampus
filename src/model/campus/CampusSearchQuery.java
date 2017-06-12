package model.campus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Shota on 6/12/2017.
 * <p>
 * The interface for generating SQL search queries.
 */
public interface CampusSearchQuery {
    String generateQuery();
}
