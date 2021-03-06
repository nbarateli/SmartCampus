package serve.managers;

import misc.Utils;
import model.accounts.AccountManager;
import model.accounts.User;
import model.accounts.UserSearchQueryGenerator;
import model.accounts.UserWarning;
import model.lostandfound.ItemReport;
import model.problems.CampusProblem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

/**
 * implementation of AccountManager (DAO for accounts)
 */
public class DefaultAccountManager implements AccountManager {

    private final DBConnector connector;

    /**
     * constructor of DefaultAccountManager class
     * @param connector DBConnector object, so that this class can interact with database
     */
    DefaultAccountManager(DBConnector connector) {

        this.connector = connector;
    }

    @Override
    public void setPicture(User user, String imageURL) {
        try {
            String sql = String.format("UPDATE %s \n" +
                            "SET %s.%s = ? WHERE %s = ?",
                    SQL_TABLE_USER, SQL_TABLE_USER, SQL_COLUMN_USER_IMAGE,
                    SQL_COLUMN_USER_ID);
            connector.executeUpdate(sql, imageURL, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserViaID(int id) {
        return null;
    }

    @Override
    public User getUserViaEMail(String email) {

        String sql = "SELECT * FROM " + SQL_TABLE_USER + " WHERE " + SQL_TABLE_USER +
                "." + SQL_COLUMN_USER_EMAIL + "= ?";
        Object[] values = {email};
        try (ResultSet matches = connector.executeQuery(sql, values)) {

            if (matches.next()) {
                return getUserFromResults(matches);
            }
            return null;
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public List<UserWarning> getAllWarningsOf(User user) {


        return null;
    }

    @Override
    public List<ItemReport> getAllItemReportsBy(User user) {
        return null;
    }

    @Override
    public List<CampusProblem> getAllProblemsReportedBy(User user) {
        return null;
    }

    @Override
    public List<User.UserRole> getAllRolesOF(User user) {
        List<User.UserRole> roles = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s\n  WHERE %s = ?", SQL_TABLE_USER_ROLE, SQL_COLUMN_USER_ROLE_USER);
        try (ResultSet results = connector.executeQuery(sql, user.getId())) {
            while (results.next()) {
                roles.add(toUserRole(results.getString(SQL_COLUMN_USER_ROLE_NAME)));
            }
        } catch (SQLException e) {
            //ignored
        }
        return roles;
    }

    @Override
    public List<User.UserPermission> getAllPermissionsOf(User user) {

        List<User.UserPermission> permissions = new ArrayList<>();
        if (user == null) {
            return permissions;
        }
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", SQL_TABLE_USER_PERMISSION, SQL_COLUMN_USER_PERMISSION_USER);
        try (ResultSet resultSet = connector.executeQuery(sql, user.getId())) {
            while (resultSet.next()) {
                permissions.add(toUserPermission(resultSet.getString(SQL_COLUMN_USER_PERMISSION_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //ignored
        }
        return permissions;
    }

    @Override
    public void removeUserWarning(UserWarning warning) {

    }


    @Override
    public List<User> find(UserSearchQueryGenerator queryGenerator) {
        return null;
    }

    @Override
    public boolean add(User entity) {

        String sql = String.format("INSERT  INTO %s " +
                        "(%s, %s, %s, %s, %s) \n" +
                        "VALUE (?, ?, ?, ?, ?)",
                SQL_TABLE_USER, SQL_COLUMN_USER_FIRST_NAME, SQL_COLUMN_USER_LAST_NAME,
                SQL_COLUMN_USER_EMAIL, SQL_COLUMN_USER_ROLE, SQL_COLUMN_USER_IMAGE);

        return successfulOperation(sql, connector,
                entity.getFirstName(), entity.getLastName(), entity.geteMail(),
                Utils.roleToString(entity.getInitialRole()), entity.getImageURL());
    }

    @Override
    public boolean remove(int entityId) {

        String sql = String.format("DELETE FROM %s WHERE %s = ?", SQL_TABLE_USER, SQL_COLUMN_USER_ID);

        return successfulOperation(sql, connector, entityId);
    }


}
