package model.accounts;

import model.campus.CampusManager;
import model.lostandfound.ItemReport;
import model.problems.CampusProblem;

import java.util.List;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The account manager DAO.
 * Responsible for pulling and pushing information related to user accounts
 * from and into the database.
 */
public interface AccountManager extends CampusManager<User, UserSearchQuery> {


    /**
     * Returns a {@code User} object associated with given id in the database.
     *
     * @param id an id of the user
     * @return a {@code User} object or null if none found.
     */
    User getUserViaID(int id);

    /**
     * Returns a {@code User} object associated with given email address.
     *
     * @param email email address of the user
     * @return a {@code User} object or null if none found.
     */
    User getUserViaEMail(String email);

    /**
     * Returns all warnings issued for the user
     *
     * @param user a user to whom the warnings were issued
     * @return a List of warnings
     */
    List<UserWarning> getAllWarningsOf(User user);

    /**
     * Returns a list of all the item reports (lost or found) posted by the user
     *
     * @param user the author of reports
     * @return a List of {@code ItemReport} objects
     */
    List<ItemReport> getAllItemReportsBy(User user);

    /**
     * Returns all the problems the user has reported
     *
     * @param user the author of the problems
     * @return a List of {@code CampusProblem } objects
     */
    List<CampusProblem> getAllProblemsReportedBy(User user);

    /**
     * Returns all the roles the user is associated with
     *
     * @param user a user whose roles the caller needs to get
     * @return a List of {@code UserRole} objcets
     */
    List<User.UserRole> getAllRolesOF(User user);

    /**
     * Returns all the permissions user has.
     *
     * @param user a user whose permissions the caller needs to get
     * @return a list of {@code UserPermission} objects
     */
    List<User.UserPermission> getAllPermissionsOf(User user);

    /**
     * Removes the user warning from database.
     *
     * @param warning a {@code UserWarning} object with valid ID to be removed.
     */
    void removeUserWarning(UserWarning warning);

}
