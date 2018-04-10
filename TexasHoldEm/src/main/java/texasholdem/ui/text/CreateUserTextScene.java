/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.ui.text;

import texasholdem.database.*;
import texasholdem.domain.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
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
        
            if(command.equals("1")){
                this.createUser();
            } else if (command.equals("2")){
                this.listAllUsers();
            } else if (command.equals("3")){
                this.removeUser();
            } else if(command.equals("x")){
                return new StartTextScene();
            } else {
                System.out.println("Invalid command!");
            }
        }
    }
    
    public void createUser(){
        UserDao dao = new UserDao(new Database("jdbc:sqlite:THE.db"));
        System.out.print("Give username: ");
        String username = scan.next();
        User newUser = new User(1, username, 4000);
        try{
            dao.save(newUser);
        } catch (Exception e) {
            System.out.println(e);
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
    
    public void removeUser(){
        UserDao dao = new UserDao(new Database("jdbc:sqlite:THE.db"));
        System.out.print("Give the id of the user you want to remove: ");
        String command = scan.next();
        Integer id = Integer.parseInt(command);
        try{
            dao.delete(id);
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
}
