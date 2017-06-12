package model.campus;


import java.util.List;

/**
 * Created by Shota on 6/12/2017.
 */
/*
* This interface has the base methods that every database object will
* use.
* */
public interface CampusManager<T extends  CampusObject> {
    List<T> find(CampusSearchQuery query);
    void add(T entity);
    void remove(T entity);
}
