package texasholdem.ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The Main class of the program. Launches the game
 * @author josujosu
 */
import texasholdem.ui.text.TextUi;


public class Main {
    
    public static void main(String[] args) {

        System.out.println("!!!WELCOME TO TEXAS HOLD 'EM!!!\n"
                + "###############################");
        TextUi tui = new TextUi();
        tui.run();
        System.out.println("SEE YOU SOON!");
    }
    
}
