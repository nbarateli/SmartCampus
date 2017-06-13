package model.lecture;

import model.campus.CampusObject;

public class CampusSubject implements CampusObject {
    private final int id;
    private final String name;

    public CampusSubject(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return id;
    }
}
