package serve.managers;

import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
import model.campus.CampusSearchQuery;
import model.lectures.CampusSubject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static misc.Utils.getBookingFromResults;
import static misc.Utils.successfulOperation;
import static model.database.SQLConstants.*;

public class DefaultBookingManager implements BookingManager {
    
    private static BookingManager instance;

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

            while(set.next()) {
                list.add(getBookingFromResults(set));
            }

        } catch (SQLException e) {
            //doing nothing
        }
        return list;
    }

    @Override
    public boolean add(Booking booking) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(booking.getDate());
        String description = booking.getDescription();
        CampusSubject subject = booking.getSubject();
        
        String insertQuery = "insert into " + SQL_TABLE_BOOKING + " (" +
                SQL_COLUMN_BOOKING_ROOM + ", " +
                SQL_COLUMN_BOOKING_BOOKER + ", " + SQL_COLUMN_BOOKING_DATE +
                (subject == null ? "" : ", " + SQL_COLUMN_BOOKING_SUBJECT_ID) +
                (description == null ? "" : ", " + SQL_COLUMN_BOOKING_DESCRIPTION) +
                ", " + SQL_COLUMN_BOOKING_START_TIME + ", " +
                SQL_COLUMN_BOOKING_END_TIME + ", " + SQL_COLUMN_BOOKING_WEEK_DAY +
                ") values (?,?,?,?,?,?" + (description == null ? "" : ",?") +
                (subject == null ? ")" : ",?)");
        return successfulOperation(insertQuery, connector, booking.getRoom().getId(), booking.getBooker().getId(),
                date, (subject == null ? null : subject.getId()),
                (description == null ? null : description), booking.getStartTime(),
                booking.getEndTime(), booking.getDay().name().toLowerCase());
    }

    @Override
    public boolean remove(int entityId) {
        String deleteQuery = "delete from " + SQL_TABLE_BOOKING + " where "
                + SQL_COLUMN_BOOKING_ID + " = ?";
        return successfulOperation(deleteQuery, connector, entityId);
    }

    @Override
    public boolean deleteAllOccurrences(int bookingId) {
        String query = "select * from " + SQL_TABLE_BOOKING + " where " + SQL_COLUMN_BOOKING_ID + " = ?";
        try {
            ResultSet rs = connector.executeQuery(query, bookingId);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

}
