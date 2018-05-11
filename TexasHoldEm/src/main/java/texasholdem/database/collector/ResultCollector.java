/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.database.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import texasholdem.domain.Result;

/**
 * A class for interpreting query results as Result objects
 * @author josujosu
 */
public class ResultCollector implements Collector<Result> {
    
    @Override
    public Result collect(ResultSet rs) throws SQLException {
        return new Result(rs.getInt("id"), rs.getInt("userId"), rs.getInt("oldBalance"), rs.getInt("balanceChange"));
    }
    
}
