/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.File;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import texasholdem.database.Database;
import texasholdem.database.ResultDao;
import texasholdem.database.UserDao;
import texasholdem.domain.Result;
import texasholdem.domain.UIOperations;
import texasholdem.domain.User;

/**
 *
 * @author josujosu
 */
public class UIOperationsTest {
    
    private UIOperations op;
    private UserDao ud;
    private ResultDao rd;
            
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.op = new UIOperations();
        this.ud = new UserDao(new Database("jdbc:sqlite:test.db"));
        this.rd = new ResultDao(new Database("jdbc:sqlite:test.db"));
        this.op.setuDao(this.ud);
        this.op.setrDao(this.rd);       
    }
    
    @After
    public void tearDown() {
        new File("test.db").delete();
    }
    
    public void addUsersAndresultsToDB() throws SQLException{
        this.ud.save(new User(1, "Test", 4000));
        this.ud.save(new User(2, "Test2", 4000));
        this.rd.save(new Result(1, 1, 300, 200));
        this.rd.save(new Result(1, 1, 200, 100));
        this.rd.save(new Result(1, 2, 900, -200));
        this.rd.save(new Result(1, 2, 600, -200));
        this.rd.save(new Result(1, 1, 200, 70));       
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void printUsersReturnsFalseWhenNoUsersInDatabase() {
        assertFalse(this.op.printAllUsers());
    }
    
    @Test
    public void printUsersReturnsTrueWhenUsersInDatabase() throws SQLException{
        this.addUsersAndresultsToDB();
        assertTrue(this.op.printAllUsers());
    }
    
    @Test
    public void getUserReturnsRightUser() throws SQLException {
        this.addUsersAndresultsToDB();
        assertEquals(new User(1, "Test", 4000).toString(), this.op.getUser("1").toString());
    }
    
    @Test
    public void getUserReturnsNullWhenInvalidCommand() throws SQLException {
        assertTrue(this.op.getUser("w") == null);
    }
    
    @Test
    public void createUserReturnsFalseWhenNameIsTaken() throws SQLException {
        this.addUsersAndresultsToDB();
        assertFalse(this.op.createUser("Test"));
    }
    
    @Test
    public void createUserReturnsTrueWhenNameIsValid() throws SQLException {
        this.addUsersAndresultsToDB();
        assertTrue(this.op.createUser("NotTaken"));
    }
    
}
