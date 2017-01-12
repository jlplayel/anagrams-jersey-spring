package org.jlplayel.anagram.tool;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class WordsCombinatorTest {
    
    private List<String> DICTONARY = Arrays.asList("a", "b", "aa", "bb", "cc",
                                                   "aaa", "bbb", "aaaa", "bbbb",
                                                   "aaaaa", "ddddddd");
    
    @Test
    public void testGetAllPhraseCombinations_zeroPhraseLength(){
        int phraseLength = 0;
        int minWordLength = 1;
        
        List<String> combinations =       
                WordsCombinator.getAllPhraseCombinations(phraseLength, DICTONARY, minWordLength);

        assertEquals(0, combinations.size());
    }
    
    
    @Test(expected = ArithmeticException.class)
    public void testGetAllPhraseCombinations_zeroMinWordLength(){
        int phraseLength = 1;
        int minWordLength = 0;
        
        WordsCombinator.getAllPhraseCombinations(phraseLength, DICTONARY, minWordLength);
    }
   
    @Test
    public void testGetAllPhraseCombinations_minWordLength1PhraseLength1(){
        int phraseLength = 1;
        int minWordLength = 1;
        
        List<String> expected = Arrays.asList("a", "b");
        
        List<String> combinations =       
                WordsCombinator.getAllPhraseCombinations(phraseLength, DICTONARY, minWordLength);

        assertEquals(expected, combinations);
    }
    
    
    @Test
    public void testGetAllPhraseCombinations_minWordLength1PhraseLength2(){
        int phraseLength = 2;
        int minWordLength = 1;
        
        List<String> expected = Arrays.asList("aa", "bb", "cc", "a b", "a a", "b b");
        
        List<String> combinations =       
                WordsCombinator.getAllPhraseCombinations(phraseLength, DICTONARY, minWordLength);

        assertEquals(expected, combinations);
    }
    
    
    @Test
    public void testGetAllPhraseCombinations_minWordLength3PhraseLength7(){
        int phraseLength = 7;
        int minWordLength = 3;
        
        List<String> expected = Arrays.asList("ddddddd", "aaaa bbb", "aaa aaaa",
                                              "aaa bbbb", "bbb bbbb");
        
        List<String> combinations =       
                WordsCombinator.getAllPhraseCombinations(phraseLength, DICTONARY, minWordLength);

        assertEquals(expected, combinations);
    }
    
    
    
}
