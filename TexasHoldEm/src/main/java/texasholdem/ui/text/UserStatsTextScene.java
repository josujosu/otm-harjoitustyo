/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.ui.text;

/**
 * A Class that defines the UI for when the user checking the statistics of different users
 * @author josujosu
 */
import java.util.List;
import texasholdem.domain.*;
import texasholdem.database.*;

public class UserStatsTextScene implements TextScene {
    
    private User user;
    
    @Override
    public TextScene run() {
        System.out.println("----User stats checker----");
        if(!this.op.checkIfDatabaseHasAnyUsers()) {
            System.out.println("No users to check!");
            return new StartTextScene();
        }
        
        while (true) {
            this.op.printAllUsers();
            System.out.print("Choose user: \n"
                    + "id: pick user\n"
                    + "x: return to main menu\n"
                    + "> ");
            String command = scan.next();
            switch (command) {
                case "x":
                    return new StartTextScene();
                default:
                    this.user = this.op.getUser(command);
                    if (this.user != null) {
                        break;
                    } else {
                        System.out.println("Could not pick user");
                        return new StartTextScene();
                    }
            }
            break;
        }
        
        this.selectWhatToInspect();
        
        return new StartTextScene();
    }
    
    /**
     * A method for defining the UI when the user is selecting the User to inspect
     */
    public void selectUserToInspect() {
        

    }
    
    /**
     * A method for defining the UI when the user is selecting what to inspect
     */
    public void selectWhatToInspect() {
        while (true) {
            System.out.print("What do you want to check?\n"
                    + "1: Win/Lose ratio\n"
                    + "2: Balance history\n"
                    + "3: Wins and Losses\n"
                    + "x: Stop\n"
                    + ">");
            String command = scan.next();
            switch (command) {
                case "1":
                    this.printWinLossRatio();
                    break;
                case "2":
                    this.printBalanceHistory();
                    break;
                case "3":
                    this.printWinsAndLosses();
                    break;
                case "x":
                    return;
                default:
                    System.out.println("Not a command");
                    break;
            }    
        }
        
    }
    
    /**
     * A method for defining the UI when the win/lose ratio is being shown
     */
    public void printWinLossRatio() {
        System.out.println("W/L: " + this.user.getWinLoseRatio());
    }
    
    /**
     * A method for defining the UI when the balance history of a User is being
     * shown
     */
    public void printBalanceHistory() {
        List<Integer> balances = this.user.getBalanceHistory();
        int i = 1;
        
        for(Integer balance : balances) {
            System.out.println(i + ": " + balance);
            i++;
        }
    }
    
    /**
     * A method defining the UI when the wins and losses of a User are being shown
     */
    public void printWinsAndLosses() {
        List<Integer> winnings = this.user.getWinsAndLosses();
        int i = 1;
        System.out.println("--Positive values are wins, negative losses--");        
        for (Integer win : winnings) {
            System.out.println(i + ": " + win);
            i++;
        }
    }
    
}
