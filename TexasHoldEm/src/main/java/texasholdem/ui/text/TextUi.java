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

public class TextUi {
    
    private TextScene currentScene;
    private StartTextScene startScene;
    
    public TextUi(){
        this.startScene = new StartTextScene();
        this.currentScene = this.startScene;
    }
    
    public void run(){
        while(true){
            TextScene newScene = this.currentScene.run();
            
            if(newScene == null){
                break;
            } else {
                this.currentScene = newScene;
            }
        }
    }
    
}
