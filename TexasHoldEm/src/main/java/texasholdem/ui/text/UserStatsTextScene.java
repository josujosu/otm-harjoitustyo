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
import texasholdem.domain.*;
import texasholdem.database.*;

public class UserStatsTextScene implements TextScene {
    
    private User user;
    
    @Override
    public TextScene run() {
        System.out.println("----User stats checker----");
        this.selectUserToInspect();
        this.selectWhatToInspect();
        
        return new StartTextScene();
    }
    
    public void selectUserToInspect() {
        System.out.println("Select user:");
        this.listAllUsers();
        System.out.print("> ");
        int id = Integer.parseInt(scan.next());
        try {
            this.user = new UserDao(new Database("jdbc:sqlite:THE.db")).findOne(id);        
        } catch (Exception e) {
            System.out.println(e);
        }        
    }
    
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
    
    public void printWinLossRatio() {
        System.out.println("W/L: " + this.user.getWinLoseRatio());
    }
    
    public void printBalanceHistory() {
        List<Integer> balances = this.user.getBalanceHistory();
        int i = 1;
        
        for(Integer balance : balances) {
            System.out.println(i + ": " + balance);
            i++;
        }
    }
    
    public void printWinsAndLosses() {
        List<Integer> winnings = this.user.getWinsAndLosses();
        int i = 1;
        System.out.println("--Positive values are wins, negative losses--");        
        for (Integer win : winnings) {
            System.out.println(i + ": " + win);
            i++;
        }
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
    
}
