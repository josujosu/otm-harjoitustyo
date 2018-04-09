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
import java.util.Scanner;

public interface TextScene {
    
    public Scanner scan = new Scanner(System.in);
    
    public TextScene run();
    
}
