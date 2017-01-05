package org.jlplayel.anagram.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LetterTool {
    
    
    /**
     * Provided a String it returns the anagram key. A string with only letters, sorted by the
     * alphabet order. So the initial string is passed to lower case and any character non-letter in
     * the English alphabet is removed, finally it is sorted.
     * 
     * For example: "Hello World!" --> "dehllloorw"
     * 
     * @param initialText
     * @return the anagram key.
     */
    public static String getAnagramKey( String initialText ){
        
        String anagramKey = initialText.toLowerCase().trim().chars()
                .filter( s -> s>96 && s<123 ).sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
     
        return anagramKey;

    }
    
    
    /**
     * Provided a String it returns the lower case letters, removing any other character.
     * A string with only letters. So the initial string is passed to lower case and any
     * character non-letter in the English alphabet is removed, no letter sort is performanced.
     * 
     * For example: "Hello World!" --> "helloworld"
     * 
     * @param initialText
     * @return the anagram key.
     */
    public static String getLowerCaseOnlyLetters( String initialText ){
        
        String text = initialText.toLowerCase().trim().chars()
                .filter( s -> s>96 && s<123 )
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
     
        return text;

    }
    
    
    /**
     * Provided a word, it returns the list of letters, which has char code in [97,122], that
     * are not in that word.
     * 
     * For example: 123BCDFGHJklmnpqrstvwxyz!@#$  --> {"a","e","i","o","u"}
     * 
     * @param word or phrase
     * @return lower case list of letters
     */
    public static List<String> getMissingLetters( String word ){

        List<String> result = new ArrayList<String>();
        
        String aKey = getAnagramKey(word);
        for(int l=97; l<123; l++){
            if( !aKey.contains(String.valueOf((char)l)) ){
                result.add(String.valueOf((char)l));
            }
        }
        
        return result;
    }
    
    
    /**
     * Provided a word, it returns the map with its letters as keys and the number of times
     * in the word as value
     * 
     * @param An word
     * @return Map letter-->letterAmount in that word
     */
    public static Map<String,Integer> getLetterAmountMap(String word){
        
        Map<String,Integer> letterNum = new HashMap<String,Integer>();
              
        for(int letterPos=0; letterPos<word.length(); letterPos++){
            String letter = word.substring(letterPos, letterPos+1);
            letterNum.merge(letter, 1, Integer::sum);
        }
        
        return letterNum;
    }
    
}
