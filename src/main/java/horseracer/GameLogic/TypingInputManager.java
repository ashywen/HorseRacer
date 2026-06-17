package horseracer.GameLogic;
import horseracer.model.Word;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * The type Typing input manager
 * <br><br>
 * Manage input from the scene and validate with corresponding action
 *
 * @version 1.0.0
 * @author Tianrui Xu
 */
public class TypingInputManager {
    /** the word instance*/
    private Word currentWord;
    /** index in the word*/
    private int index;
    /** total input char count*/
    private int totalInputChars;
    /** wrong input count*/
    private int wrongCharCount;
    /** the obstacle spawner*/
    private ObstacleSpawner oSpawner;

    /**
     * Instantiates a new Typing input manager.
     *
     * @param newWord  the new word
     * @param oSpawner the obstacle spawner
     */
    public TypingInputManager(Word newWord, ObstacleSpawner oSpawner){
        updateWord(newWord);
        this.oSpawner = oSpawner;
        this.totalInputChars = 0;
        this.wrongCharCount = 0;
    }

    /**
     * Append key handler to scene
     * @param pane the StackPane
     */
    public void appendEventHandler(StackPane pane){
        pane.setOnKeyPressed(e -> {
            if(e.getCode().isLetterKey()){
                processKey(e.getCode());
            }
        });
    }

    /**
     * Process the key pressed and update UI
     *
     * @param code the key code
     */
    public void processKey(KeyCode code){
        if(isWordComplete()){
            return;
        }
        this.oSpawner.updateObstacleTextUI(checkCharacter(code), this.index - 1);
    }

    /**
     * Check char entered with the char of word at index
     *
     * @param code the key code
     * @return true if input is correct
     */
    public boolean checkCharacter(KeyCode code){
        this.totalInputChars ++;
        if(code.getChar().toLowerCase().equals(String.valueOf(currentWord.getText().charAt(this.index)))){
            this.index ++;
            return true;
        }else{
            this.wrongCharCount++;
            this.currentWord.removePoints(5);
            return false;
        }
    }

    /**
     * To determine if the word completes
     *
     * @return true if reach the end of the word
     */
    public boolean isWordComplete(){
        return this.index == this.currentWord.getText().length();
    }

    /**
     * Get wrong char count int
     *
     * @return count of wrong char entered
     */
    public int getWrongCharCount(){
        return wrongCharCount;
    }

    /**
     * Get total input chars int
     *
     * @return count of total input chars
     */
    public int getTotalInputChars(){
        return totalInputChars;
    }

    /**
     * Update word and reset index
     *
     * @param newWord the new word
     */
    public void updateWord(Word newWord){
        this.currentWord = newWord;
        this.index = 0;
    }

}
