package horseracer.GameLogic;

import horseracer.model.User;

/**
 * The Score manager.
 * <br><br>
 * Manage multiplier and player score
 *
 * @version 1.0.0
 * @author Tianrui Xu
 */
public class ScoreManager{
    /** Score multiplier*/
    private double scoreMultiplier;
    /** Points get*/
    private int points;


    /**
     * Instantiates a new Score manager.
     */
    public ScoreManager(){
        this.scoreMultiplier = 1;
        this.points = 0;
    }

    /**
     * Add points with multiplier
     *
     * @param pts points
     */
    public void addPoints(int pts){
        this.points += pts * this.scoreMultiplier;
    }

    /**
     * Apply 2x multiplier
     */
    public void applyMultiplier(){
        this.scoreMultiplier = 2;
    }

    /**
     * Restore multiplier
     */
    public void restoreMultiplier(){
        this.scoreMultiplier = 1;
    }

    /**
     * Reset score.
     */
    public void resetScore(){
        this.points = 0;
    }

    /**
     * Get score int.
     *
     * @return the score
     */
    public int getScore(){
        return this.points;
    }
}
