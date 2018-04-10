/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import texasholdem.domain.User;

/**
 *
 * @author josujosu
 */
public class UserTest {
    
    User user;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        user = new User(1, "user_1", 4000);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void constructorWorksAsItShould(){
        assertEquals("id: 1, username: user_1, balance: 4000", user.toString());
    }
    
    @Test
    public void setIdWorks(){
        user.setId(2);
        assertEquals("id: 2, username: user_1, balance: 4000", user.toString());
    }
    
    @Test
    public void setUsernameWorks(){
        user.setUsername("user_2");
        assertEquals("id: 1, username: user_2, balance: 4000", user.toString());
    }
    
    @Test
    public void setBalanceWorks(){
        user.setBalance(100);
        assertEquals("id: 1, username: user_1, balance: 100", user.toString());
    }
    
    @Test
    public void getIdWorks(){
        assertTrue(1 == user.getId());
    }
    
    @Test
    public void getUsernameWorks(){
        assertEquals("user_1", user.getUsername());
    }
    
    @Test
    public void getBalanceWorks(){
        assertTrue(4000 == user.getBalance());
    }
}
