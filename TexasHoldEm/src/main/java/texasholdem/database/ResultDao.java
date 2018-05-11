/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.database;

import java.sql.SQLException;
import java.util.List;
import texasholdem.database.collector.ResultCollector;
import texasholdem.domain.Result;

/**
 * A class for managing objects of the type "Result" in a database
 * @author josujosu
 */
public class ResultDao implements Dao<Result, Integer> {

    private Database db;

    /**
     * Constructor
     * @param db The database which will be managed
     */
    public ResultDao(Database db) {
        this.db = db;
    }

    @Override
    public Result findOne(Integer key) throws SQLException {
        List<Result> result = this.db.queryAndCollect("SELECT * FROM Result WHERE id = ?", new ResultCollector(), key);
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public void save(Result result) throws SQLException {
        this.db.update("INSERT INTO Result (userId, oldBalance, balanceChange) VALUES (?, ?, ?)", result.getUserId(), result.getOldBalance(), result.getBalanceChange());
    }

    @Override
    public List<Result> findAll() throws SQLException {
        List<Result> results = this.db.queryAndCollect("SELECT * FROM Result", new ResultCollector());
        return results;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        this.db.update("DELETE FROM Result WHERE id = ?", key);
    }

    /**
     * A method for extracting every Result containing the same userId
     * @param key The userId
     * @return A list of all of the Result objects
     * @throws SQLException 
     */
    public List<Result> findAllWithSameUserId(Integer key) throws SQLException {
        List<Result> results = this.db.queryAndCollect("SELECT * FROM Result WHERE userId = ?", new ResultCollector(), key);
        return results;
    }
    
    /**
     * A method for deleting all of the Results with the same userId from a database
     * @param key The userId
     * @throws SQLException 
     */
    public void deleteAllWithSameUserId(Integer key) throws SQLException {
        this.db.update("DELETE FROM Result WHERE userId = ?", key);
    }
}
