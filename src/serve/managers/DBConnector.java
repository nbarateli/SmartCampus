package serve.managers;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import misc.DBInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static misc.DBInfo.*;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * Responsible for creating a connecting and executing queries in database.
 */
public class DBConnector {
    private MysqlConnectionPoolDataSource dataSource;

    DBConnector() {
        this.dataSource = new MysqlConnectionPoolDataSource();
        try {
            dataSource.getConnection();
            DBInfo.class.newInstance();
            dataSource.setURL(MYSQL_DATABASE_SERVER);
            dataSource.setUser(MYSQL_USERNAME);
            dataSource.setPassword(MYSQL_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
            //ignored
        }
    }


    /**
     * Executes the passed sql query and returns fetched results.
     *
     * @param sql    a SQL statement to be sent to the database, typically a
     *               static SQL <code>SELECT</code> statement
     * @param values an array of values to replace "?" characters in the SQL statement
     * @return a <code>ResultSet</code> object that contains the data produced
     * by the given query; never <code>null</code>
     * @throws SQLException if a database access error occurs,
     *                      the given SQL statement produces anything other than a single
     *                      <code>ResultSet</code> object, the method is called on a
     *                      <code>PreparedStatement</code> or <code>CallableStatement</code>
     */
    public ResultSet executeQuery(String sql, Object... values) throws SQLException {
        return (ResultSet) execute(sql, values, false);
    }

    /**
     * Executes passed SQL query and returns number of rows affected.
     *
     * @param sql    a SQL Data Manipulation Language (DML) statement, such as <code>INSERT</code>,
     *               <code>UPDATE</code> or <code>DELETE</code>; or a SQL statement that returns nothing,
     * @param values an array of values to replace "?" characters in the SQL statement
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed <code>Statement</code>, the given
     *                      SQL statement produces a <code>ResultSet</code> object, the method is called on a
     */
    public int executeUpdate(String sql, Object... values) throws SQLException {
        return (int) execute(sql, values, true);
    }


    /**
     * Connects to database, executes passed and returns either a <code>ResultSet</code> or an int,
     * based on what type of query was passed.
     *
     * @return a <code>ResultSet</code> if the query type is not update, an int denoting the number of rows if it is.
     * @throws SQLException if database access error occurs.
     */
    private Object execute(String sql, Object[] values, boolean isUpdate) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = dataSource.getPooledConnection().getConnection();
            //dataSource.getConnection();//DriverManager.getConnection
//                    (MYSQL_DATABASE_SERVER, MYSQL_USERNAME, MYSQL_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery("USE " + MYSQL_DATABASE_NAME + ";");
            if (values != null) {

                addParameters(statement, values);
            }

            if (isUpdate)
                return statement.executeUpdate();
            else
                return statement.executeQuery();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addParameters(PreparedStatement statement, Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);

        }

    }
}
