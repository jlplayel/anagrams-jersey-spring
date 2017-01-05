package org.jlplayel.anagram.tool;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class LetterToolTest {
    
    private final static String EMPTY_STRING = "";
    private final static String LOWER_CASE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    
    @Test
    public void testGetAnagramKey_noWord(){
        String anagramKey = LetterTool.getAnagramKey(EMPTY_STRING);
        
        assertTrue(EMPTY_STRING.equals(anagramKey));
        assertEquals(EMPTY_STRING, anagramKey);
    }
    
    @Test
    public void testGetAnagramKey_wordWithNoValidCharacters(){
        String anagramKey = LetterTool.getAnagramKey("123/{}# $%&");
        
        assertEquals(EMPTY_STRING, anagramKey);
    }
    
    @Test
    public void testGetAnagramKey_wordWithNoValidCharacters2(){
        String anagramKey = LetterTool.getAnagramKey("12a3/{b }#$c%&");
        
        assertEquals("abc", anagramKey);
    }
    
    @Test
    public void testGetAnagramKey_toLowerCase(){
        String anagramKey = LetterTool.getAnagramKey("ABC");
        
        assertEquals("abc", anagramKey);
    }
    
    @Test
    public void testGetAnagramKey_toLowerCase2(){
        String anagramKey = LetterTool.getAnagramKey("AaBbCc");
        
        assertEquals("aabbcc", anagramKey);
    }
    
    @Test
    public void testGetAnagramKey_anagramKeyHasSortedLetters(){
        String anagramKey = LetterTool.getAnagramKey("CaBcAb");
        
        assertEquals("aabbcc", anagramKey);
    }

    
    @Test
    public void testGetLowerCaseOnlyLetters_noWord(){
        String anagramKey = LetterTool.getLowerCaseOnlyLetters(EMPTY_STRING);
        
        assertTrue(EMPTY_STRING.equals(anagramKey));
        assertEquals(EMPTY_STRING, anagramKey);
    }
    
    @Test
    public void testGetLowerCaseOnlyLetters_wordWithNoValidCharacters(){
        String anagramKey = LetterTool.getLowerCaseOnlyLetters("123/{}# $%&");
        
        assertEquals(EMPTY_STRING, anagramKey);
    }
    
    @Test
    public void testGetLowerCaseOnlyLetters_wordWithNoValidCharacters2(){
        String anagramKey = LetterTool.getLowerCaseOnlyLetters("12b3/{c }#$a%&");
        
        assertEquals("bca", anagramKey);
    }
    
    @Test
    public void testGetLowerCaseOnlyLetters_toLowerCase(){
        String anagramKey = LetterTool.getLowerCaseOnlyLetters("ABC");
        
        assertEquals("abc", anagramKey);
    }
    
    @Test
    public void testGetLowerCaseOnlyLetters_toLowerCase2(){
        String anagramKey = LetterTool.getLowerCaseOnlyLetters("CcAaBb");
        
        assertEquals("ccaabb", anagramKey);
    }
    
    @Test
    public void testGetLowerCaseOnlyLetters_anagramKeyHasSortedLetters(){
        String anagramKey = LetterTool.getLowerCaseOnlyLetters("CaBcAb");
        
        assertEquals("cabcab", anagramKey);
    }
    
    
    @Test
    public void testGetMissingLetters_emptyWordReturnLowerCaseAlphabet(){
        List<String> expected = Arrays.asList(LOWER_CASE_ALPHABET.split(""));
                
        List<String> missingLetters = LetterTool.getMissingLetters(EMPTY_STRING);
        
        assertEquals(expected, missingLetters);
    }
    
    @Test
    public void testGetMissingLetters_lowerCaseFullAlphabetReturnEmptyList(){
        List<String> expected = Collections.emptyList();
                
        List<String> missingLetters = LetterTool.getMissingLetters(LOWER_CASE_ALPHABET);
        
        assertEquals(expected, missingLetters);
    }
    
    @Test
    public void testGetMissingLetters_lowerCaseReturnMissingLetters(){
        List<String> expected = Arrays.asList("a", "e", "i", "o", "u");
        
        List<String> missingLetters = LetterTool.getMissingLetters("bcdfghjklmnpqrstvwxyz");
        
        assertEquals(expected, missingLetters);
    }
    
    @Test
    public void testGetMissingLetters_capitalLettersAreNotIgnored(){
        List<String> expected = Arrays.asList("x", "y", "z");
                
        List<String> missingLetters = LetterTool.getMissingLetters("ABCDEFGHIJKLMNOPQRSTUVW");
        
        assertEquals(expected, missingLetters);
    }
    
    @Test
    public void testGetMissingLetters_otherCharactersAreIgnored(){
        List<String> expected = Arrays.asList("a","e","i","o","u");
                
        List<String> missingLetters = LetterTool.getMissingLetters("123 BCDFGHJk lmnpqrstvwxyz!@#$");
        
        assertEquals(expected, missingLetters);
    }
    
    @Test
    public void testGetMissingLetters_repetedLettersAreIgnored(){
        List<String> expected = Arrays.asList("a","e","i","o","u");
                
        List<String> missingLetters = LetterTool.getMissingLetters("BCDFGHJklmnpqrstvwxyzBCDxyz");
        
        assertEquals(expected, missingLetters);
    }
    
    
    @Test 
    public void testGetLetterAmountMap_emptyString(){
        Map<String,Integer> letterAmount = LetterTool.getLetterAmountMap("");
        
        assertEquals( 0, letterAmount.size());
    }
    
    @Test 
    public void testGetLetterAmountMap_commonExample(){
        Map<String,Integer> letterAmount = LetterTool.getLetterAmountMap("abababac");
        
        assertEquals( 3, letterAmount.size());
        assertEquals( 4, (long)letterAmount.get("a"));
        assertEquals( 3, (long)letterAmount.get("b"));
        assertEquals( 1, (long)letterAmount.get("c"));
    }
    
    @Test 
    public void testGetLetterAmountMap_caseSensitive(){
        Map<String,Integer> letterAmount = LetterTool.getLetterAmountMap("aaaAA");
        
        assertEquals( 2, letterAmount.size());
        assertEquals( 3, (long)letterAmount.get("a"));
        assertEquals( 2, (long)letterAmount.get("A"));
    }
    
}
