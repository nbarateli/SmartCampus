package misc;

import java.io.*;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * Reads database information locally.
 * The database information should be stored in <code>DB_INFO_FILE</code>
 * <p>
 * The file itself should consist of following 4 lines:
 * username
 * password
 * server name
 * database name
 */
public class DBInfo {
    public static final String MYSQL_USERNAME;
    public static final String MYSQL_PASSWORD;
    public static final String MYSQL_DATABASE_SERVER;
    public static final String MYSQL_DATABASE_NAME;

    private static final String DB_INFO_FILE = "dbinfo.txt";

    static {

        String userName = "";
        String passWord = "";
        String server = "";
        String databaseName = "";
        try (BufferedReader in = new BufferedReader(new FileReader(DB_INFO_FILE))) {
            userName = in.readLine();
            passWord = in.readLine();
            server = in.readLine();
            databaseName = in.readLine();
        } catch (IOException e) {
            //doing nothing

        }
        MYSQL_USERNAME = userName;
        MYSQL_PASSWORD = passWord;
        MYSQL_DATABASE_SERVER = server;
        MYSQL_DATABASE_NAME = databaseName;
    }

}
