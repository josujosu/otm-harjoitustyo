package texasholdem.ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josujosu
 */
import texasholdem.ui.text.TextUi;
import texasholdem.domain.*;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("!!!WELCOME TO TEXAS HOLD 'EM!!!\n"
                + "###############################");
        TextUi ui = new TextUi();
        ui.run();
        System.out.println("SEE YOU SOON!");
    }
    
}
