/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.database;

/**
 * An interface for all classes that has to deal with databases
 * @author josujosu
 */
import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {

    /**
     * A method for finding one element from a database.
     * @param key Parameter with which an element will be found
     * @return An element that corresponds to the key
     * @throws SQLException 
     */
    T findOne(K key) throws SQLException;

    /**
     * A method for saving elements in a database
     * @param element The element that will be saved
     * @throws SQLException 
     */
    void save(T element) throws SQLException;

    /**
     * A method for retrieving all elements
     * @return All elements as a list
     * @throws SQLException 
     */
    List<T> findAll() throws SQLException;

    /**
     * A method for removing elements from a database
     * @param key The parameter that defines the element to be removed
     * @throws SQLException 
     */
    void delete(K key) throws SQLException;
    
    /**
     * A method for initialising the table corresponding to the Dao-type
     */
    void init();
}
