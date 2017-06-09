package model.rooms;

import model.accounts.User;

import java.util.Date;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The SmartCampus room problem ADT
 */
public class RoomProblem {
    private final User author;
    private final Room room;
    private final String title;
    private final String description;
    private final long dateCreated;

    public RoomProblem(User author, Room room, String title, String description, Date dateCreated) {
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
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateCreated() {
        return new Date(dateCreated);
    }
}
