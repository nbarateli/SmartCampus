package model.managers;

import static misc.Utils.getLectureFromResults;
import static misc.Utils.getSubjectFromResults;
import static misc.Utils.successfulOperation;
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_START_TIME;
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_END_TIME;
import static model.database.SQLConstants.SQL_COLUMN_SUBJECT_NAME;
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_SUBJECT_ID; 
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_BOOKER;
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_DATE;
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_DESCRIPTION;
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_ID;
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_ROOM;
import static model.database.SQLConstants.SQL_COLUMN_BOOKING_WEEK_DAY;
import static model.database.SQLConstants.SQL_TABLE_SUBJECT;
import static model.database.SQLConstants.SQL_TABLE_BOOKING;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
import model.campus.CampusSearchQuery;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.LectureManager;
import model.lectures.LectureSearchQueryGenerator;

public class DefaultBookingManager implements BookingManager {
    
    private static LectureManager instance;

    private final DBConnector connector;

    DefaultBookingManager(DBConnector connector) {
        this.connector = connector;
    }

    @Override
    public void removeAllBookings() {
        String truncateQuery = "truncate table " + SQL_TABLE_BOOKING;
        try {
            //TODO
            connector.executeUpdate(truncateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void removeAllLectures() {
        String deleteQuery = "delete from " + SQL_TABLE_BOOKING + " where " + 
                SQL_COLUMN_BOOKING_SUBJECT_ID + " > 0";
        try {
            //TODO
            connector.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Booking> find(BookingSearchQueryGenerator queryGenerator) {
        List<Booking> list = new ArrayList<>();
        try {
            //TODO
            CampusSearchQuery query = queryGenerator.generateQuery();
            ResultSet set = connector.executeQuery(query.getQuery(), query.getValues());

            
            
        } catch (SQLException e) {
            //doing nothing
        }
        return list;
    }

    @Override
    public boolean add(Booking booking) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(booking.getDate());
        
        String insertQuery = "insert into " + SQL_TABLE_BOOKING + " (" +
                SQL_COLUMN_BOOKING_ROOM + ", " +
                SQL_COLUMN_BOOKING_BOOKER + ", " + SQL_COLUMN_BOOKING_DATE +
                ", " + SQL_COLUMN_BOOKING_SUBJECT_ID +
                ", " + SQL_COLUMN_BOOKING_DESCRIPTION +
                ", " + SQL_COLUMN_BOOKING_WEEK_DAY +
                ", " + SQL_COLUMN_BOOKING_START_TIME + ", " +
                SQL_COLUMN_BOOKING_END_TIME + ") values (" +
                booking.getRoom().getId() + ", " +
                booking.getBooker().getId() + ", '" +
                date + "', " + booking.getSubject().getId() + 
                ", '" + booking.getDescription() + "', " +
                "', '" + booking.getStartTime() + "', '" +
                booking.getEndTime() + 
                "', '" + booking.getDay().name().toLowerCase() + "') ";
        return successfulOperation(insertQuery, connector);
    }

    @Override
    public boolean remove(int entityId) {
        String deleteQuery = "delete from " + SQL_TABLE_BOOKING + " where "
                + SQL_COLUMN_BOOKING_ID + " = " + entityId;
        return successfulOperation(deleteQuery, connector);
    }

    @Override
    public void deleteAllOccurrences(int bookingId) {
        // TODO Auto-generated method stub

    }

}
