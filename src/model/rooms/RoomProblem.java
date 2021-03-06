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

    public RoomProblem(int id, User author, Room room, String title, String description, 
            Date dateCreated) {
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
    public int getId() {
        return id;
    }
    
    /**
     * override of equals method automatically generated by eclipse
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RoomProblem other = (RoomProblem) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (dateCreated != other.dateCreated) {
            System.out.println("date1 " + dateCreated + " date 2" + other.dateCreated);
            return false;
        }
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id != other.id)
            return false;
        if (room == null) {
            if (other.room != null)
                return false;
        } else if (!room.equals(other.room))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
}
