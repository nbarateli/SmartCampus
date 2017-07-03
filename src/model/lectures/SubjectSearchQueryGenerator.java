package model.lectures;

import model.campus.CampusSearchQuery;
import model.campus.CampusSearchQueryGenerator;

import java.util.ArrayList;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

public class SubjectSearchQueryGenerator implements CampusSearchQueryGenerator<CampusSubject> {
    private Integer id;
    private String name;

    public SubjectSearchQueryGenerator(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    //Default Constructor:
    public SubjectSearchQueryGenerator() {
        this(null, null);
    }

    public int getSubjectId() {
        return id;
    }

    public void setSubjectId(Integer id) {
        this.id = id;
    }

    public String getSubjectName() {
        return name;
    }

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

    private boolean hasNonNullFields() {
        return id != null || name != null;
    }
}
