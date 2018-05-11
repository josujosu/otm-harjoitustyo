/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.ui.text;

/**
 * An Interface that defines methods important for the different UI scenes
 * @author josujosu
 */
import java.util.Scanner;
import texasholdem.domain.UIOperations;

public interface TextScene {
    
    public Scanner scan = new Scanner(System.in);
    public UIOperations op = new UIOperations();
    
    /**
     * A method that defines what happens in a certain scene
     * @return The next TextScene to be run
     */
    public TextScene run();
    
}
