package serve.managers;

import misc.DBInfo;
import org.junit.Before;
import org.junit.Test;
import serve.managers.DBConnector;
import serve.managers.*;

import static org.junit.Assert.*;

/**
 * Created by Zaura on 7/3/2017.
 */
public class DefaultBookingManagerTest {
    private ManagerFactory thisOne = new ManagerFactory();
    private DefaultBookingManager a = (DefaultBookingManager) thisOne.getBookingManager();
    @Before
    public void setUp() throws Exception{
        DBInfo.class.newInstance();
    }

    @Test
    public void test1(){
        a.deleteAllOccurrences(56);
        System.out.println("deleted");
    }


}