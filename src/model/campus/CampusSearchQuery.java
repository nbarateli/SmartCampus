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

    @Override
    public String toString() {

        return replaceQuestionMarks(query, values);
    }

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
}