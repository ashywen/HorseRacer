package horseracer.GameLogic;

import java.util.ArrayList;
import java.util.Random;

import horseracer.model.PowerUp;
import horseracer.model.PowerUpType;
import javafx.animation.Timeline;

/**
 * The Power up manager.
 * <br><br>
 * Manage gain or use powerUp
 *
 * @version 1.0.0
 * @author Tianrui XU
 */
public class PowerUpManager{
    /** The powerup*/
    private PowerUp powerUp;
    /** Timer for powerup*/
    private Timer timer = new Timer();
    /** boolean indicating if a powerup is active*/
    private boolean active = false;

    /**
     * Gain power up
     *
     * @param powerUpType the power up type
     */
    public void gainPowerUp(PowerUpType powerUpType){
        this.powerUp = new PowerUp(powerUpType, 6 + new Random().nextInt(4));
    }

    /**
     * Active the first power up
     *
     * @param sManager the score manager
     * @param lSystem  the life system
     */
    public void activePowerUp(ScoreManager sManager, LifeSystem lSystem, Timeline gameLoop){
        this.active = true;
        switch(powerUp.getType()){
            case DOUBLE_POINTS:
                sManager.applyMultiplier();
                //TODO: ui for multiplier
//                System.out.println("Double points");
                break;
            case SLOW_OBSTACLE:
                //TODO: slow obstacle indication ui
//                System.out.println("Slow obstacle");
                gameLoop.setRate(gameLoop.getRate()/1.5);
                break;
            case EXTRA_LIFE:
                //TODO: ui for gain live
//                System.out.println("Extra life");
                if(lSystem.getCurrentLives() < 3){
                    lSystem.gainLife();
                }
                break;
        }
    }

    /**
     * De-active powerUp
     * @param sManager score manager
     * @param gameLoop the game loop
     * @return false if de-activated, true indicate still running power up
     */
    public boolean deactivatePowerUp(ScoreManager sManager, Timeline gameLoop){
        if(this.active){
            switch(this.powerUp.getType()){
                case DOUBLE_POINTS:
                    if(this.timer.getElapsedSeconds() > this.powerUp.getDurationSeconds()){
                        this.active = false;
                        sManager.restoreMultiplier();
                        return false;
                    }
                    break;
                case SLOW_OBSTACLE:
                    if(this.timer.getElapsedSeconds() > this.powerUp.getDurationSeconds()){
                        this.active = false;
                        gameLoop.setRate(gameLoop.getRate() * 1.5);
                        return false;
                    }
                    break;
                default:
                    if(this.timer.getElapsedSeconds() > this.powerUp.getDurationSeconds()){
                        this.active = false;
                        return false;
                    }

            }
        }else{
            return !(this.timer.getElapsedSeconds() >= 5);
        }
        return true;
    }

    /**
     * Start timer for power up
     */
    public void startPowerUpTimer(){
        this.timer.start();
    }
}

