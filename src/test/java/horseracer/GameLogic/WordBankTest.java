package horseracer.GameLogic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class WordBankTest{
    private static WordBank wordBank;
    @BeforeAll
    public static void setUp(){
        wordBank = new WordBank("/assets/Easy.txt");
    }

    @Test
    public void testWordBankIndex(){
        assertEquals(0, wordBank.getIndex());
    }

    @Test
    public void testFileNotFound(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        wordBank = new WordBank("unknowFile.txt");

        // Restore System.out
        System.setOut(originalOut);

        // Assert the output
        assertEquals("File not found: unknowFile.txt" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testIndexOutOfBoundsException(){
        assertThrows(IndexOutOfBoundsException.class, () -> {wordBank.getWords(99999);});
        assertThrows(IndexOutOfBoundsException.class, () -> {wordBank.getWords(-1);});
    }

    @Test
    public void testArrayListNonNull(){
        assertNotNull(wordBank.getWords());
    }


}
