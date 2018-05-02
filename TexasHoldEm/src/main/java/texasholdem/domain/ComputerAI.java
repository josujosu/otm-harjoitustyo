/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.domain;

import java.util.Random;

/**
 * A class that decides the action that an AI player makes
 * @author josujosu
 */
public class ComputerAI {
    
    private double handCallProbability;
    private double handRaiseProbability;
    private double callProbability;
    private double raiseProbability;
    private double allInProbability;
    private int maxNormalBet;
    private Random ran;
    
    public ComputerAI() {
        this.handCallProbability = 1.0;
        this.handRaiseProbability = 0.15;
        this.callProbability = 0.4;
        this.raiseProbability = 0.05;
        this.allInProbability = 0.005;
        this.maxNormalBet = 200;
        this.ran = new Random();
    }
    
    /**
     * Method for obtaining an Action, the nature of which is affected by the
     * deck parameter. It first decreases the probabilities for actions by checking
     * the number of the round played, then checks if the given deck contains a
     * hand better than a high, and depending on that decides which probability values to
     * use for deciding the action type.
     * @param hand a Deck containing the cards which will affect the 
     * nature of the return value
     * @param call an integer depicting the amount of money the player making the
     * action has to use for a call
     * @return the decided action that the player will make
     */
    public Action makeAction(Deck hand, int call) {
        this.decreaseProbabilitiesAccordingToRoundNumber(hand);
        if (this.hasBetterHandThanHigh(hand)) {
            return this.selectRandomAction(this.handCallProbability, this.handRaiseProbability, call);
        } else {
            return this.selectRandomAction(callProbability, raiseProbability, call);
        }
    }
    
    /**
     * Selects a random action type from probabilities for random calling and raising.
     * The raise amount is also decided randomly.
     * @param cProb The probability for the action being a call
     * @param rProb The probability for the action being a raise
     * @param call The amount of money the player making the action would need
     * for a call
     * @return An Action with a randomly selected type and raise amount if the
     * action is a raise type
     */
    public Action selectRandomAction(double cProb, double rProb, int call) {
        if (this.ran.nextDouble() < rProb) {
            return new Action(Action.ActionType.RAISE, call, this.ran.nextInt(this.maxNormalBet));
        } else if (this.ran.nextDouble() < cProb) {
            return new Action(Action.ActionType.CALL, call, 0);
        } else if (this.ran.nextDouble() < this.allInProbability) {
            return new Action(Action.ActionType.RAISE, call, Integer.MAX_VALUE);
        } else {
            return new Action(Action.ActionType.FOLD, 0, 0);
        }
    }
    
    
    /**
     * A method for checking if the given deck has a hand better than a high
     * @param deck The deck from which the hand will be checked
     * @return true, if the deck contains a hand better than high, otherwise false
     */
    public boolean hasBetterHandThanHigh(Deck deck) {
        PokerHand hand = new PokerHand(deck);
        return hand.getHandType() != PokerHand.HandType.HIGH;
    }
    
    /**
     * Decreases the probability values for calling by 1/10th of the value of the
     * current round number
     * @param deck The current deck from which the number of the round is inferred
     */
    public void decreaseProbabilitiesAccordingToRoundNumber(Deck deck) {
        double dec = this.getRoundNumberFromNumberOfCards(deck) / 10;
        this.callProbability -= dec;
        this.handCallProbability -= dec;
    }
    
    /**
     * A method for inferring the number of the current round from a deck. It
     * works by checking the number of cards. If the size of the Card array in
     * the deck corresponds to the number of cards a player has during the first round,
     * an integer with the value of 1 is returned.
     * @param deck A Deck containing the cards a player currently has
     * @return The number of the current round as an integer.
     */
    public int getRoundNumberFromNumberOfCards(Deck deck) {
        int n = deck.getCards().size();
        switch (n) {
            case 2:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 4;
            default:
                return 0;
        }
    }  
}
