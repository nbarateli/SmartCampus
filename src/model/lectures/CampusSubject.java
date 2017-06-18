package model.lectures;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CampusSubject other = (CampusSubject) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
