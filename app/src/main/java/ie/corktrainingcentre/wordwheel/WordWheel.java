package ie.corktrainingcentre.wordwheel;

import android.content.res.Resources;

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


    private String word;
    private char centreChar;
    private int wordlenght = 9;


    public WordWheel(int wordLength){

        this.wordlenght = wordLength;
        word = getWordFromDic();

    }
// TODO Make this method more usable to by using a parameter of the file to be read
    private String getWordFromDic() {

        String file = "res/raw/list_of_words.txt"; // res/raw/test.txt also work.
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        String word = "";
        ArrayList<String> words = new ArrayList<>();

//      read the file and add all the words that are equal the the length we have selected
        try {
            while ((word = bufferedReader.readLine()) != null)
                if(word.length() == getWord().length())
                    words.add(word);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        pick out a random word from the words arraylist that we have populated
        Random rand = new Random();
        int index = rand.nextInt(words.size());

        return words.get(index);

    }



    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public char getCentreChar() {
        return centreChar;
    }

    public void setCentreChar(char centreChar) {
        this.centreChar = centreChar;
    }

    public int getWordlenght() {
        return wordlenght;
    }

    public void setWordlenght(int wordlenght) {
        this.wordlenght = wordlenght;
    }


}
