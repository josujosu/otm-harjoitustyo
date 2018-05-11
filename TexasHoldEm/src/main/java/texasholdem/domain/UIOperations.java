/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

import java.sql.SQLException;
import java.util.List;
import texasholdem.database.Database;
import texasholdem.database.ResultDao;
import texasholdem.database.UserDao;

/**
 * A class that defines operations that are made in the UI
 * @author josujosu
 */
public class UIOperations {
    
    private UserDao uDao;
    private ResultDao rDao;
    
    /**
     * Constructor
     */
    public UIOperations() {
        this.uDao = new UserDao(new Database("jdbc:sqlite:THE.db"));
        this.rDao = new ResultDao(new Database("jdbc:sqlite:THE.db"));
    }
    
    /**
     * Prints all users to the terminal window from a database 
     * @return true if printing was successful, otherwise false
     */
    public boolean printAllUsers() {
        List<User> users;
        try{
            users = uDao.findAll();
            if(users.isEmpty()){
                System.out.println("No users found!");
                return false;
            } else {
                for(User user: users){
                    System.out.println(user);
                }
                return true;
            }          
        } catch (Exception e){
            System.out.println(e);
            return false;
        }        
    }
    
    /**
     * A method for retrieving a User object from a database
     * @param command A command given in the UI. Should correspond to the id of the 
     * user to be retrieved
     * @return If acquiring of the User was successful the User object, otherwise null
     */
    public User getUser(String command) {
        try {
            
            int id = Integer.parseInt(command);
            return this.uDao.findOne(id);
        
        } catch (NumberFormatException | SQLException e) {
        
            return null;
        
        }
    }
    
    /**
     * Adds a User into a database 
     * @param username Username of the added user
     * @return true if the addition was successful, otherwise false
     */
    public boolean createUser(String username) {
        
        try{
            List<User> users = this.uDao.findAll();
            
            for (User user : users) {
                
                if (user.getUsername().equals(username)) {
                    return false;
                }
                
            }
            
            User newUser = new User(1, username, 4000);
            uDao.save(newUser);
            return true;
            
        } catch (Exception e) {
            return false;        
        }

    }
    
    /**
     * Removes a user from a database
     * @param command A command given from the UI. Should correspond to the id of
     * the user to be removed
     * @return true if the deletion was successful, otherwise false
     */
    public boolean removeUser(String command) {
        
        try {
        
            int id = Integer.parseInt(command);
            this.uDao.delete(id);
            this.rDao.deleteAllWithSameUserId(id);
            return true;
        
        } catch (NumberFormatException | SQLException e) {
            return false;
        }
    }
    
    /**
     * Creates a Game object with a user
     * @param user The User the player will use
     * @return The created Game object if the user can be used in a game, otherwise
     * null
     */
    public Game createGame(User user) {
        
        Game game = new Game(user, 8);
        
        if(game.playerUserCanBeUsed(user)) {
            return game;
        } else {
            return null;
        }
        
    }
    
    /**
     * Prints the latest AI player to the terminal window
     * @param game The game from which the AI player will be printed
     */
    public void printLatestAIPLayer(Game game) {
        
        if (game.playerInRelationToCurrent(-1).equals(game.getLastActor())) {
            
            System.out.println(game.getLastActor() + ": " + game.getLatest().toStringTextUIFormatted());                    
        
        }
        
    }
    
    /**
     * Checks if a database has any users defined
     * @return true if database has users, otherwise false
     */
    public boolean checkIfDatabaseHasAnyUsers() {
        
        try {
            
            if(uDao.findAll().size() > 0) {
                return true;
            } else {
                return false;
            }
        
        } catch (Exception e) {
        
            return false;
        
        }
      
    }

    /**
     * @param uDao the uDao to set
     */
    public void setuDao(UserDao uDao) {
        this.uDao = uDao;
    }

    /**
     * @param rDao the rDao to set
     */
    public void setrDao(ResultDao rDao) {
        this.rDao = rDao;
    }
    
}
