package model.rooms;

import model.accounts.User;
import model.campus.CampusObject;

import java.util.Date;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The SmartCampus room problem ADT
 */
public class RoomProblem implements CampusObject {
    private final User author;
    private final Room room;
    private final String title;
    private final String description;
    private final long dateCreated;
    private final int id;

    public RoomProblem(int id, User author, Room room, String title, String description, Date dateCreated) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated.getTime();
    }

    public User getAuthor() {
        return author;
    }

    public Room getRoom() {
        return room;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public Date getDateCreated() {
        return new Date(dateCreated);
    }

    @Override
    public int getID() {
        return id;
    }
}
