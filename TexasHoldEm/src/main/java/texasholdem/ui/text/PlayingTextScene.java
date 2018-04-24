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
import texasholdem.domain.HandComparator;

public class PlayingTextScene implements TextScene{
    
    
    @Override
    public TextScene run() {
        this.listAllUsers();
        System.out.print("Choose user: ");
        Integer userId = Integer.parseInt(scan.next());
        User playerUser = this.getUserFromDb(userId);
        Game newGame = new Game(playerUser, 8);
        String status = "";
        
        while (true) {
            status = this.playPreFlop(newGame, status);
            status = this.play(newGame, status);
            newGame.putCardsOnTable(3);
            status = this.play(newGame, status);
            newGame.putCardsOnTable(1);
            status = this.play(newGame, status);
            newGame.putCardsOnTable(1);
            status = this.play(newGame, status);
            newGame.playShowdown();
            newGame.printPlayers();
            newGame.initializeNextDeal();

            System.out.print("New deal? 'y/n'");
            String command = scan.next();
            if (command.equals("n")) {
                break;
            }
        }
        
        
        return new StartTextScene();
    }
    
    public String play(Game game, String status) {
        while(!(status.equals("NextRound") || status.equals("EndGame"))) {
            if(status.equals("Player") && !game.playerHasFolded()) {
                System.out.println(game.getPotAndTableString());
                System.out.println(game.getHumanPlayer());
                System.out.print("Actions: \n "
                        + "Call, Raise, Fold\n"
                        + "What will you do? ");
                String command = scan.next();
                switch(command) {
                    case "Call":
                        status = game.playHuman(command, 0);
                        break;
                    case "Raise":
                        System.out.print("How much?: ");
                        int raiseAmount = Integer.parseInt(scan.next());
                        status = game.playHuman(command, raiseAmount);
                        break;
                    case "Fold":
                        status = game.playHuman(command, 0);
                        break;
                    default:
                        status = game.playHuman("Call", 0);
                        break;
                }
            } else {
                status = game.playNext();
            }
        }
        status = game.initializeForNextRound();
        return status;
    }
    
    public String playPreFlop(Game game, String status) {
        status = game.playBlinds();
        return status;
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
