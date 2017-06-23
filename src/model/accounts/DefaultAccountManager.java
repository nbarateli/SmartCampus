package model.accounts;

import model.database.DBConnector;
import model.lostandfound.ItemReport;
import model.problems.CampusProblem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static misc.Utils.getUserFromResults;
import static model.database.SQLConstants.SQL_COLUMN_USER_EMAIL;
import static model.database.SQLConstants.SQL_TABLE_USER;

public class DefaultAccountManager implements AccountManager {

    public static AccountManager getInstance() {
        return new DefaultAccountManager();
    }

    private DefaultAccountManager() {

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
        try (ResultSet matches = DBConnector.executeQuery(sql, values)) {

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
        return null;
    }

    @Override
    public List<User.UserPermission> getAllPermissionsOf(User user) {
        return null;
    }

    @Override
    public void removeUserWarning(UserWarning warning) {

    }


    @Override
    public List<User> find(UserSearchQuery query) {
        return null;
    }

    @Override
    public boolean add(User entity) {
        return false;
    }

    @Override
    public boolean remove(int entityId) {
        return false;
    }


}
