/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import texasholdem.database.Database;
import texasholdem.database.UserDao;
import texasholdem.domain.User;

/**
 *
 * @author josujosu
 */
public class UserDaoTest {
    
    private Database db;
    private UserDao uDao;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        db = new Database("jdbc:sqlite:test.db");
        uDao = new UserDao(db);
        try{
            this.uDao.save(new User(1, "Test", 4000));
        } catch (Exception e) {
            System.out.println(e);
        }       
    }
    
    @After
    public void tearDown() {
        new File("test.db").delete();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void findOneReturnsAUser() throws SQLException {
        User user = new User(1, "Test", 4000);
        assertEquals(user.toString(), this.uDao.findOne(1).toString());
    }
    
    @Test
    public void findOneReturnsNullIfNoSavedUsers() throws SQLException {
        this.uDao.delete(1);
        assertTrue(this.uDao.findOne(1) == null);
    }
    
    @Test
    public void findAllReturnsAllUsersFromDatabase() throws SQLException {
        User user = new User(1, "Test", 4000);
        User user2 = new User(2, "Test2", 4000);
        List<User> users = new ArrayList<>(Arrays.asList(user, user2));
        this.uDao.save(user2);
        assertEquals(users.toString(), this.uDao.findAll().toString());
    }
    
    @Test
    public void deletingUserDeletesTheRightUser() throws SQLException {
        User user2 = new User(2, "Test2", 4000);
        List<User> users = new ArrayList<>(Arrays.asList(user2));
        this.uDao.save(user2);
        this.uDao.delete(1);
        assertEquals(users.toString(), this.uDao.findAll().toString());
    }
    
    @Test
    public void addToBalanceAddsToTheRightBalanceAndTheRightAmount() throws SQLException {
        User user2 = new User(2, "Test2", 4000);
        this.uDao.save(user2);
        this.uDao.addToBalance(2, 300);
        assertEquals(4300, this.uDao.findOne(2).getBalance());
    }
}
