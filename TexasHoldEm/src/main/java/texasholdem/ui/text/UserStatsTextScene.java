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
                    + "2: Balance history (not ready)\n"
                    + "3: Wins and Losses (not ready)\n"
                    + "x: Stop\n"
                    + ">");
            String command = scan.next();
            switch (command) {
                case "1":
                    this.printWinLossRatio();
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
