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
import texasholdem.domain.Player;

public class PlayingTextScene implements TextScene{
    
    
    @Override
    public TextScene run() {
        this.listAllUsers();
        Game newGame;
        String status = "";
        
        System.out.print("Choose user: ");
        Integer userId = Integer.parseInt(scan.next());            
        User playerUser = this.getUserFromDb(userId);
        newGame = new Game(playerUser, 8);           
        if(!newGame.playerUserCanBeUsed(playerUser)) {
            System.out.println("User doesn't have enough money to be used");
            return new StartTextScene();
        }      
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
            
            System.out.println("WINNER:");
            for (Player p : newGame.getPlayersWithBestHand()) {
                System.out.println(p);
            }
            
            System.out.println("REMAINING PLAYERS:");
            newGame.printNonFoldedPlayers();
            
            newGame.initializeNextDeal();
            
            if(!newGame.hasEnoughPlayersForPlaying()) {
                System.out.println("Not enough players for a game!");
                return new PlayingTextScene();
            }
            
            if (!newGame.playerUserCanBeUsed(newGame.getHumanPlayer().getUser())) {
                System.out.println("You don't have enough money to play!");
                return new PlayingTextScene();
            }

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
            if(status.equals("Player") && !game.hasFolded("Player")) {
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
                if (game.playerInRelationToCurrent(-1).equals(game.getLastActor())) {
                    System.out.println(game.getLastActor() + ": " + game.getLatest().toStringTextUIFormatted());                    
                }
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
