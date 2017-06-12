package model;

import java.sql.*;
import java.util.Calendar;

import static misc.DBInfo.*;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * Responsible for creating a connecting and executing queries in database.
 */
public class DBConnector {
    private DBConnector() {

    }


    /**
     * Executes the passed sql query and returns fetched results.
     *
     * @param sql a SQL statement to be sent to the database, typically a
     *            static SQL <code>SELECT</code> statement
     * @return a <code>ResultSet</code> object that contains the data produced
     * by the given query; never <code>null</code>
     * @throws SQLException if a database access error occurs,
     *                      the given SQL statement produces anything other than a single
     *                      <code>ResultSet</code> object, the method is called on a
     *                      <code>PreparedStatement</code> or <code>CallableStatement</code>
     */
    public static ResultSet executeQuery(String sql) throws SQLException {
        return (ResultSet) execute(sql, false);
    }

    /**
     * Executes passed SQL query and returns number of rows affected.
     *
     * @param sql a SQL Data Manipulation Language (DML) statement, such as <code>INSERT</code>,
     *            <code>UPDATE</code> or <code>DELETE</code>; or a SQL statement that returns nothing,
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed <code>Statement</code>, the given
     *                      SQL statement produces a <code>ResultSet</code> object, the method is called on a
     */
    public static int executeUpdate(String sql) throws SQLException {
        return (int) execute(sql, true);
    }

    /**
     * Connects to database, executes passed and returns either a <code>ResultSet</code> or an int,
     * based on what type of query was passed.
     *
     * @return a <code>ResultSet</code> if the query type is not update, an int denoting the number of rows if it is.
     * @throws SQLException if database access error occurs.
     */
    private static Object execute(String sql, boolean isUpdate) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection
                    (MYSQL_DATABASE_SERVER, MYSQL_USERNAME, MYSQL_PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeQuery("USE " + MYSQL_DATABASE_NAME + ";");

            if (isUpdate)
                return statement.executeUpdate(sql);
            else
                return statement.executeQuery(sql);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
