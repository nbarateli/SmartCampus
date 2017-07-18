package model.subjects;

import model.campus.CampusSearchQuery;
import model.campus.CampusSearchQueryGenerator;

import java.util.ArrayList;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

/**
 * implementation of CampusSearchQueryGenerator for CampusSubject
 */
public class SubjectSearchQueryGenerator implements CampusSearchQueryGenerator<CampusSubject> {
    private Integer id;
    private String name;

    /**
     * constructor of SubjectSearchQueryGenerator
     * @param id id of the subject being searched in database
     * @param name name of the subject being searched
     */
    public SubjectSearchQueryGenerator(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    //Default Constructor:
    public SubjectSearchQueryGenerator() {
        this(null, null);
    }

    /**
     * @return id of the subject being searched in database
     */
    public int getSubjectId() {
        return id;
    }

    /**
     * sets id of the subject being searched in database
     */
    public void setSubjectId(Integer id) {
        this.id = id;
    }

    /**
     * @return name of the subject being searched
     */
    public String getSubjectName() {
        return name;
    }

    /**
     * sets name of the subject being searched in database
     */
    public void setSubjectName(String name) {
        this.name = name;
    }

    @Override
    public CampusSearchQuery generateQuery() {
        List<Object> values = new ArrayList<>();
        String sql = hasNonNullFields() ? String.format("SELECT * FROM %s " +
                        " \nWHERE %s%s",
                SQL_TABLE_SUBJECT,
                generateEqualQuery(id, SQL_COLUMN_SUBJECT_ID, values) + " AND ",
                generateLikeQuery(name, SQL_COLUMN_SUBJECT_NAME, values)
        ) : "SELECT * FROM " + SQL_TABLE_SUBJECT;
        return new CampusSearchQuery(sql, asArray(values));
    }

    /**
     * Returns whether this object has any variable in non-default state.
     */
    private boolean hasNonNullFields() {
        return id != null || name != null;
    }
}
