package ie.corktrainingcentre.wordwheel;

import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by AppDeveloper on 10/06/2015.
 */
public class WordWheel {

    private final String TAG = "Word Wheel";
    private final boolean   LOGS_ON = true;
    private final String FILE = "res/raw/list_of_words.txt"; // res/raw/test.txt also work.

    private String word;
    private char centreChar;
    private int wordlenght = 9;


    // Construtor made so that it will always have a wordlength parameter, and automatically
    // gets a random word from the file of the correct length
    public WordWheel(int wordLength){

       // this method also sets the word varible
        setWordlenght(wordLength);

    }

   /* public String solutions(String word){

//        open the
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(FILE);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        String word = "";
        ArrayList<String> words = new ArrayList<>();

//      read the file and add all the words that are equal the the length we have selected
        try {
            while ((word = bufferedReader.readLine()) != null)
                if(word.length() == getWordlenght())
                    words.add(word);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }*/

// TODO Make this method more usable to by using a parameter of the file to be read
    private String getWordFromDic() {


        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(FILE);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        String word = "";
        ArrayList<String> words = new ArrayList<>();

//      read the file and add all the words that are equal the the length we have selected
        try {
            while ((word = bufferedReader.readLine()) != null)
                if(word.length() == getWordlenght())
                    words.add(word);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        pick out a random word from the words arraylist that we have populated
        Random rand = new Random();
        if(LOGS_ON) Log.d(TAG, "words size is " + words.size());
        int index = rand.nextInt(words.size());

        return words.get(index);

    }

    public Result checkWord(String wordToBeChecked){

        Result result = new Result();

        if(checkIfWordIsEnglish(wordToBeChecked) == false) {

            result.setMessage( "Sorry, we have no record of \" " + wordToBeChecked + "\" + n the dictionary");
            result.setValid(false);
            return result;
        }
        else{
            boolean[] boolUsedCharacters = new boolean[getWordlenght()];
            char c = ' ';
            boolean foundCharacter = false;

                for(int i =0;i< wordToBeChecked.length();i++)
                {
                    c = wordToBeChecked.charAt(i);
                    foundCharacter = false; // We start a new search, so set foundCharter to false
//                    again

//                    Uses the character in 'c' and iterates through the wordWheel word to see if
//                    'c' is equal to any of the characters in wordWheel that have not been used
//                    already in the comparing process.
                    for (int j = 0; j < getWordlenght();j++) {

                        if(boolUsedCharacters[j] == false && c == getWord().charAt(j)) {
                            boolUsedCharacters[j] = true;
                            foundCharacter = true;
                            break;
                        }

                    }
//                    if 'c' is not found in the wordWheel word the user has entered an incorrect
//                    guess
                    if(!foundCharacter) {

                        result.setValid(false);
                        result.setMessage("\n Word Wheel do not have a :" + c);
                        return  result;
                    }
                }
        }

        result.setValid(true);
        result.setMessage("Correct, well done ");
        return result;

    }

    private boolean checkIfWordIsEnglish(String wordToBeChecked){

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(FILE);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String word = "";


//      read the file and check the words in it against out wordToBeChecked
        try {
            while ((word = bufferedReader.readLine()) != null)
                if(word.equals(wordToBeChecked))
                    return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getWord() {
         return word;
    }

    public char getCentreChar() {
        return centreChar;
    }

    private int getWordlenght() {
        return wordlenght;
    }

    // Every time this method is called, it also sets the word varible of the wordWheel class
    // and gets the centre character for the word wheel
    public void setWordlenght(int wordlenght) {
        this.wordlenght = wordlenght;
        setWord(getWordFromDic());
        setCentreLetter();
    }

    private void setCentreLetter(){

        Random rand = new Random();
        int centreIndex = rand.nextInt(getWordlenght());
        centreChar = getWord().charAt(centreIndex);

    }

    // Jumbles all the letters of a word and returns the result
    public String scrambledWord(String word)
    {
        char[] scrambled = word.toCharArray();

        int lenght = word.length();
        Random random = new Random();
        int oldCharIndex=0;
        int newCharIndex=0;
        char tempChar= ' ';
        char c = ' ';

        for(int i=0; i< 50;i++)
        {
            // Assign a ramdom index to oldCharIndex within the bounds of the word
            oldCharIndex = random.nextInt(lenght);

            // Assign a ramdom index to newCharIndex within the bounds of the word
            newCharIndex = random.nextInt(lenght);

            // save the old characters that is going to be moved
            tempChar = scrambled[oldCharIndex];
            scrambled[oldCharIndex] = scrambled[newCharIndex];
            scrambled[newCharIndex] = tempChar;

        }
        Log.d(TAG,scrambled.toString());
        String string1 = new String(scrambled);
        return string1;


    }

    public void setWord(String word) {
        this.word = word;
    }
}
