/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.ui.text;

/**
 * A Class that defines the UI for when the user is playing the game
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
        this.op.printAllUsers();
        Game newGame;
        String status = "";
        
        while (true) {
            System.out.print("Choose user: \n"
                    + "Give id: pick user\n"
                    + "x: return to main menu\n"
                    + "> ");
            String command = scan.next();
            
            if (command.equals("x")) {
                return new StartTextScene();
            }
            
            User playerUser = this.op.getUser(command);
            if (playerUser == null) {
                System.out.println("Could not pick requested user");
            }
            
            newGame = this.op.createGame(playerUser);
            
            if (newGame != null) {
                break;
            } else {
                System.out.println("User doesn't have enough money to be ued");
            }
        }
        
        
        
        while (true) {
            System.out.println("---START GAME---");
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
    
    /**
     * A method that defines the UI when the game is going through it's logic
     * @param game The Game that is being played
     * @param status The status of the game before running this method
     * @return The status of the game after running this method
     */
    public String play(Game game, String status) {
        while(!(status.equals("NextRound") || status.equals("EndGame"))) {
            if(status.equals("Player") && !game.hasFolded("Player")) {
                System.out.println("---PLAYER TURN---");
                System.out.println(game.getPotAndTableString());
                System.out.println(game.getHumanPlayer());
                while (true) {
                    System.out.print("What will you do?\n"
                            + "Actions: \n"
                            + "1: Call/Check \n"
                            + "2: Raise \n"
                            + "3: Fold\n"
                        + "> ");
                    String command = scan.next();
                    switch(command) {
                        case "1":
                            status = game.playHuman("Call", 0);
                            break;
                        case "2":
                            System.out.print("How much?: ");
                            try {
                                int raiseAmount = Integer.parseInt(scan.next());
                                status = game.playHuman("Raise", raiseAmount);
                                break;
                            } catch (Exception e) {
                                System.out.println("Invalid amount!");
                                continue;
                            }                            
                        case "3":
                            status = game.playHuman("Fold", 0);
                            break;
                        default:
                            System.out.println("Invalid command!");
                            continue;
                    }
                    break;
                }
                System.out.println("------------");
            } else {
                status = game.playNext();
                this.op.printLatestAIPLayer(game);
            }
        }
        
        status = game.initializeForNextRound();
        return status;
    }
    
    /**
     * A method for playing the "pre flop" part of the game.
     * @param game The Game that is being played
     * @param status The status of the game before running this method
     * @return The status of the game after running this method
     */
    public String playPreFlop(Game game, String status) {
        status = game.playBlinds();
        return status;
    }
    
}
