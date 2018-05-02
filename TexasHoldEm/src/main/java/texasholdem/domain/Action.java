/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 *Class that encapsulates an action made by a player
 * @author josujosu
 */
public class Action {

    
    public enum ActionType {
        CALL, RAISE, FOLD
    }
    
    private int call;
    private int raise;
    private ActionType type;
    
    public Action(ActionType type, int call, int raise) {
        this.type = type;
        this.call = call;
        this.raise = raise;
    }
    
    
    /**
     * Used to keep track of actions in the Text UI
     * 
     * @return A string formatted in such a way that it works in the Text UI
     */
    public String toStringTextUIFormatted() {
        switch (this.type) {
            case CALL:
                return "Called/Checked";
            case RAISE:
                return "Raised " + this.raise;
            case FOLD:
                return "Folded";
            default:
                return "Did something";
        }
    }
    
    /**
     * @return the call
     */
    public int getCall() {
        return call;
    }

    /**
     * @return the raise
     */
    public int getRaise() {
        return raise;
    }


    /**
     * @return the type
     */
    public ActionType getType() {
        return type;
    }
    
    
}
