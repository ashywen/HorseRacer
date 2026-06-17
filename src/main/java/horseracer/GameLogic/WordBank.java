package horseracer.GameLogic;
import horseracer.model.Difficulty;
import horseracer.model.Word;

import java.io.*;
import java.util.ArrayList;

/**
 * The Word bank
 * <br><br>
 * Read file and generate word bank
 *
 * @version 1.0.0
 * @author Tianrui Xu
 */
public class WordBank {
    /** ArrayList of all generated word instances from txt file*/
    private ArrayList<Word> words;
    /** Index of the arrayList*/
    private int index;

    /**
     * Instantiates a new Word bank.
     *
     * @param fileName the file name
     */
    public WordBank(String fileName){
        //assume that the file should have lines with words separated by space
        this.words = new ArrayList<Word>();
        this.index = 0;
        try(
                InputStream is = getClass().getResourceAsStream(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ){
            String line;
            //random offset from the word bank prevent same words appear for each round
            for(int i = 0; i < (int)(Math.random() * 100); i ++){
                br.readLine();
            }
            while((line = br.readLine()) != null){
                for(String s : line.split(" ")){
                    Difficulty diff = getDifficulty(s);
                    int points = s.length() * 10;
                    Word word = new Word(s, diff, points);
                    this.words.add(word);
                }
            }
        }catch(Exception e){
            System.out.println("File not found: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Determine the difficulty level of each word
     * @param s the word as a string
     * @return difficulty level
     */
    private Difficulty getDifficulty(String s){
        if(s.length() <= 3){
            return Difficulty.EASY;
        }else if(s.length() <= 7){
            return Difficulty.MEDIUM;
        }else{
            return Difficulty.HARD;
        }
    }

    /**
     * Gets words
     *
     * @param n numbers of words
     * @return words
     * @throws IndexOutOfBoundsException if the amount required is illegal
     */
    public ArrayList<Word> getWords(int n) throws IndexOutOfBoundsException{
        if(this.index == this.words.size() - 1 || n > this.words.size() || n < 1){
            throw new IndexOutOfBoundsException("End of word lib");
        }
        int start = this.index;
        if(this.words.size() > this.index + n){
            this.index += n;
        }else{
            this.index = this.words.size() - 1;
        }
        return new ArrayList<Word>(this.words.subList(start, index));
    }

    /**
     * Get index int
     *
     * @return the index
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * Indicate end of the word bank
     *
     * @return true if reach the end
     */
    public boolean endOfWordBank(){
        if(this.index == this.words.size() - 1){
            return true;
        }
        return false;
    }

    /**
     * Get words array list
     *
     * @return the array list
     */
    public ArrayList<Word> getWords(){
        return this.words;
    }

    /**
     * Get a single word
     *
     * @return the word
     */
    public Word getWord(){
        return this.words.get(this.index++);
    }
}
