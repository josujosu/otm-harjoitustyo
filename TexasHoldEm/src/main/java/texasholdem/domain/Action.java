/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

/**
 *
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
