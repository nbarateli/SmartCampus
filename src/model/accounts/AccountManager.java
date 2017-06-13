package model.accounts;

import model.campus.CampusManager;
import model.lecture.Lecture;

import java.sql.Statement;
import java.util.List;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The account manager DAO.
 * Responsible for pulling and pushing information related to user accounts
 * from and into the database.
 */
public interface AccountManager extends CampusManager<User, UserSearchQuery> {

    //TODO: write related methods prototypes.


}
