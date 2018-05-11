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
import texasholdem.database.ResultDao;
import texasholdem.domain.Result;

/**
 *
 * @author josujosu
 */
public class ResultDaoTest {
    
    private Database db;
    private ResultDao rDao;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        db = new Database("jdbc:sqlite:test.db");
        rDao = new ResultDao(db);
        try{
            this.rDao.save(new Result(1, 2, 3000, 100));
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
    public void findOneReturnsTheRightResult() throws SQLException {
        Result r2 = new Result(2, 1, 400, 3);
        this.rDao.save(r2);
        assertEquals(r2.toString(), this.rDao.findOne(2).toString());
    }
    
    @Test
    public void findOneReturnsNullIfNoResults() throws SQLException {
        this.rDao.delete(1);
        assertTrue(this.rDao.findOne(1) == null);
    }
    
    @Test
    public void findAllReturnsAllResults() throws SQLException {
        Result r1 = new Result(1, 2, 3000, 100);
        Result r2 = new Result(2, 1, 400, 3);
        this.rDao.save(r2);
        List<Result> rs = new ArrayList<>(Arrays.asList(r1, r2));
        assertEquals(rs.toString(), this.rDao.findAll().toString());
    }
    
    @Test
    public void deleteRemovesTheRightResult() throws SQLException {
        Result r2 = new Result(2, 1, 400, 3);
        this.rDao.save(r2);
        List<Result> rs = new ArrayList<>(Arrays.asList(r2));
        this.rDao.delete(1);
        assertEquals(rs.toString(), this.rDao.findAll().toString());
    }
    
    @Test
    public void findAllWithSameUserIdReturnsTheRightResults() throws SQLException {
        Result r2 = new Result(2, 1, 400, 3);
        Result r3 = new Result(3, 1, 600, 5);
        this.rDao.save(r2);
        this.rDao.save(r3);
        List<Result> rs = new ArrayList<>(Arrays.asList(r2, r3));
        assertEquals(rs.toString(), this.rDao.findAllWithSameUserId(1).toString());
    }
    
    @Test
    public void deleteAllWithSameUserIdDeletesTheRightResults() throws SQLException {
        Result r2 = new Result(2, 2, 400, 3);
        Result r3 = new Result(3, 1, 600, 5);
        this.rDao.save(r2);
        this.rDao.save(r3);
        List<Result> rs = new ArrayList<>(Arrays.asList(r3));
        this.rDao.deleteAllWithSameUserId(2);
        assertEquals(rs.toString(), this.rDao.findAll().toString());
    }
}
