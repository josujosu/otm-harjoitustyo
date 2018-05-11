/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.ui.text;

/**
 * A Class that acts as the UI for when the user wants to manage Users in a database
 * @author josujosu
 */
public class CreateUserTextScene implements TextScene{
    
    @Override
    public TextScene run(){
        System.out.println("-----Create User Menu-----");
        System.out.println("1: Create user");
        System.out.println("2: List all created users");
        System.out.println("3: Remove user");
        System.out.println("x: Back to start screen");
        while(true){            
            System.out.print("> ");
            String command = scan.next();
            
            switch (command) {
                case "1":
                    this.op.printAllUsers();
                    System.out.print("Give username (has to be unique): ");
                    String username = scan.next();
                    if(!this.op.createUser(username)) {
                        System.out.println("Could not create user");
                    }
                    break;
                case "2":
                    this.op.printAllUsers();
                    break;
                case "3":
                    this.op.printAllUsers();
                    System.out.print("Give id of the user you want to remove: ");
                    String id = scan.next();
                    if(!this.op.removeUser(id)) {
                        System.out.println("Could not remove user.");
                    } else {
                        System.out.println("Successfully removed user " + id);
                    }
                    break;
                case "x":
                    return new StartTextScene();
                default:
                    System.out.println("Not a command");
                    break;
            }
        
        }
    }
    
}
