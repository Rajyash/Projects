package ttt.model.dao;

import java.util.List;

import ttt.model.GameEntry;
//import ttt.model.GameMove;
import ttt.model.GameUser;
import ttt.model.User;

public interface UserDao {

    User getUser( Integer id );

    List<User> getUsers();
    
}