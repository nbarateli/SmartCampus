package model.campus;

/**
 * Created by Niko on 25.06.2017.
 * <p>
 * An immutable structure that contains an SQL query for {@code PreparedStatement} and relevant values array
 * that denote all the objects that are represented as "?"-s in given query.
 */
public class CampusSearchQuery {
    private final String query;
    private final Object[] values;

    public CampusSearchQuery(String query, Object[] values) {
        this.query = query;
        this.values = values;
    }

    public Object[] getValues() {
        return values;
    }

    public String getQuery() {
        return query;
    }
}