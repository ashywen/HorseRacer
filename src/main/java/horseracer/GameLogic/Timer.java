package horseracer.GameLogic;

import java.util.function.LongSupplier;

public class Timer{
    /** Start time*/
    private long start = 0;
    /** Paused time*/
    private long pausedOn = 0;
    /** boolean indicated if paused*/
    private boolean paused = false;

    /**
     * Start timer
     */
    public void start(){
        this.start = System.nanoTime();
    }

    /**
     * Pause timer
     */
    public void pause(){
        this.paused = true;
        this.pausedOn = System.nanoTime();
    }

    /**
     * Resume timer
     */
    public void resume(){
        this.paused = false;
        long now = System.nanoTime();
        long pausedDuration = now - pausedOn;
        start += pausedDuration;
    }

    /**
     * Get elapsed in seconds
     * @return seconds
     */
    public long getElapsedSeconds() {
        long now = System.nanoTime();
        long effectiveNow = paused ? pausedOn : now;
        return (effectiveNow - start) / 1_000_000_000L;
    }
}
