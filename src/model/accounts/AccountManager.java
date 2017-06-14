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


    User getUserViaID(int id);

    List<UserProblem> getAllProblemsOf(User user);

    List<ItemReport> getAllItemReportsBy(User user);

    List<CampusProblem> getAllProblemReportsBy(User user);

    void removeUserProblem(int problemID);

}
