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
public class StartTextScene implements TextScene{
    
    @Override
    public TextScene run(){
        System.out.print("----Main Menu-----\n"
                    + "What do you want to do? \n"
                    + "1: Manage users\n"
                    + "2: Play\n"
                    + "3: Chack user stats\n"
                    + "x: End program\n");
        while(true){
            System.out.print("> ");
            String command = scan.next();
            switch (command) {
                case "1":
                    return new CreateUserTextScene();
                case "2":
                    return new PlayingTextScene();
                case "3":
                    return new UserStatsTextScene();
                case "x":
                    return null;
                default:
                    System.out.println("not a command");
                    break;
            }  
        }        
    }
    
}
