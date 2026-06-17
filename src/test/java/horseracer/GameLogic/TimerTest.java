package horseracer.GameLogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {

    @Test
    void start_shouldBeginCountingTime() throws InterruptedException {
        Timer timer = new Timer();

        timer.start();
        Thread.sleep(1100); // sleep slightly over 1 second

        long elapsed = timer.getElapsedSeconds();

        assertTrue(elapsed >= 1, "Elapsed time should be at least 1 second after start");
    }

    @Test
    void pause_shouldFreezeElapsedTime() throws InterruptedException {
        Timer timer = new Timer();

        timer.start();
        Thread.sleep(1100);
        timer.pause();

        long pausedTime = timer.getElapsedSeconds();

        Thread.sleep(1200); // time passes, but timer is paused
        long stillPausedTime = timer.getElapsedSeconds();

        assertEquals(pausedTime, stillPausedTime, "Elapsed time should not change while paused");
    }

    @Test
    void resume_shouldContinueAfterPause() throws InterruptedException {
        Timer timer = new Timer();

        timer.start();
        Thread.sleep(1100);
        timer.pause();

        long pausedTime = timer.getElapsedSeconds();

        Thread.sleep(1200); // should not count
        timer.resume();

        Thread.sleep(1100); // should count after resume
        long resumedTime = timer.getElapsedSeconds();

        assertTrue(resumedTime >= pausedTime + 1,
                "Elapsed time should continue increasing after resume");
    }

    @Test
    void pauseAndResume_shouldNotCountPausedDuration() throws InterruptedException {
        Timer timer = new Timer();

        timer.start();
        Thread.sleep(1000);

        timer.pause();
        Thread.sleep(2000); // paused, should not count
        timer.resume();

        Thread.sleep(1000);

        long elapsed = timer.getElapsedSeconds();

        assertTrue(elapsed >= 2 && elapsed < 4,
                "Elapsed time should exclude paused duration");
    }
}