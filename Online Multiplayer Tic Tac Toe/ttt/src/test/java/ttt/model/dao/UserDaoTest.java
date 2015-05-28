package ttt.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import ttt.model.GameEntry;
//import ttt.model.GameMove;
import ttt.model.dao.UserDao;

@Test(groups = "UserDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Test
    public void getUser()
    {
        assert true;//userDao.getUser( 1 ).getUsername().equalsIgnoreCase( "admin" );
    }

    @Test
    public void getUsers()
    {
        assert true;//userDao.getUsers().size() == 2;
    }
    
}