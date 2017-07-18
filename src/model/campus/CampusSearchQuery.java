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

    /**
     * constructor of CampusSearchQuery class
     * @param query sql search query
     * @param values all the objects that are represented as "?"-s in given query
     */
    public CampusSearchQuery(String query, Object[] values) {
        this.query = query;
        this.values = values;
    }

    /**
     * @return all the objects that are represented as "?"-s in given query
     */
    public Object[] getValues() {
        return values;
    }

    /**
     * @return sql search query
     */
    public String getQuery() {
        return query;
    }

    /**
     * replace ?-s with respective values from given array in query
     * @param query sql search query
     * @param values all the objects that are represented as "?"-s in given query
     * @return string with replaces ?-s
     */
    private String replaceQuestionMarks(String query, Object[] values) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = 0; i < query.length(); i++) {
            char c = query.charAt(i);
            if (c == '?') {
                result.append(values[j++]);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    @Override
    public String toString() {

        return replaceQuestionMarks(query, values);
    }
}