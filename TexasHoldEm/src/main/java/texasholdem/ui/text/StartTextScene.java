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
        while(true){
            System.out.print("What do you want to do? \n"
                    + "1: Create user\n"
                    + "x: End program\n"
                    + "> ");
            String command = scan.next();
        
            if(command.equals("1")){
                return new CreateUserTextScene();
            } else if (command.equals("x")){
                return null;
            } else {
                System.out.println("Invalid command!");
            }    
        }        
    }
    
}
