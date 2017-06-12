package model.Interfaces;


import java.util.List;

/**
 * Created by Shota on 6/12/2017.
 */
/*
* This interface has the base methods that every database object will
* use.
* */
public interface BaseManager<T> {
    List<T> find(SearchQuery query);
    void add(T entity);
    void remove(T entity);
}
