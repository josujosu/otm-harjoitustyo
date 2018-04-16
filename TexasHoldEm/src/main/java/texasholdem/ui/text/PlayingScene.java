/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.ui.text;

/**
 *
 * @author josujosu
 */
import java.util.List;
import java.sql.SQLException;
import texasholdem.database.UserDao;
import texasholdem.database.Database;
import texasholdem.domain.User;
import texasholdem.domain.Game;

public class PlayingScene implements TextScene{
    
    
    @Override
    public TextScene run() {
        this.listAllUsers();
        System.out.println("Choose user: ");
        Integer userId = Integer.parseInt(scan.next());
        User playerUser = this.getUserFromDb(userId);
        Game newGame = new Game(playerUser, 8);
        newGame.printPlayers();
        
        return null;
    }
    
    public void listAllUsers(){
        UserDao dao = new UserDao(new Database("jdbc:sqlite:THE.db"));
        List<User> users;
        try{
            users = dao.findAll();
            if(users.isEmpty()){
                System.out.println("No users found!");
            } else {
                for(User user: users){
                    System.out.println(user);
                }    
            }            
        } catch (Exception e){
            System.out.println(e);
        }        
    }
    
    public User getUserFromDb(Integer key) {
        UserDao dao = new UserDao(new Database("jdbc:sqlite:THE.db"));
        try {
            return dao.findOne(key);        
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
    
}
