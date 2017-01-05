package org.jlplayel.anagram.service;

import static org.junit.Assert.*;

import java.util.List;

import org.jlplayel.anagram.config.SpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AnagramServiceJavaIntegrationTest {
    
    @Autowired
    @Qualifier("javaAnagram")
    AnagramService anagramService;
    
    @Test 
    public void testGetAnagramOf_EmptyString() {
        
        String initAnagram = "";
        
        List<String> anagramsResult = anagramService.getAnagramsOf(initAnagram);
        
        assertNotNull(anagramsResult);
        assertEquals( 0, anagramsResult.size() );
    }
    
    
    @Test
    public void testGetAnagramOf_HaveNoSolution() {
        
        String initAnagram = "pystoz";
        
        List<String> anagramsResult = anagramService.getAnagramsOf(initAnagram);
        
        assertNotNull(anagramsResult);
        assertEquals( 0, anagramsResult.size() );
    }
    
    
    @Test
    public void testGetAnagramOf_IgnoreNoLetters() {
        String onlyLetterAnagram = "pystom";
        String addedNoLettersAnagram = "p y2st!om";
        
        List<String> onlyLetterResult = anagramService.getAnagramsOf(onlyLetterAnagram);
        List<String> notOnlyLetterResult = anagramService.getAnagramsOf(addedNoLettersAnagram);
        
        assertNotNull(onlyLetterResult.get(0));
        assertEquals(onlyLetterResult, notOnlyLetterResult);
    }
    
    
    @Test
    public void testGetAnagramOf_DifferentLetters() {
        String anagram1 = "pystom";
        String anagram2 = "pyston";
        
        List<String> result1 = anagramService.getAnagramsOf(anagram1);
        List<String> result2 = anagramService.getAnagramsOf(anagram2);
        
        assertNotNull(result1.get(0));
        assertNotEquals(result1, result2);
    }
    
    
    @Test 
    public void testGetAnagramOf_NoCaseSensitive() {
        
        String lowerCaseAnagram = "pystom";
        String capitalAnagram = "PYSTOM";
        String mixCaseAnagram = "pYsToM";
        
        List<String> lowerCaseResult = anagramService.getAnagramsOf(lowerCaseAnagram);
        List<String> capitalResult = anagramService.getAnagramsOf(capitalAnagram);
        List<String> mixCaseResult = anagramService.getAnagramsOf(mixCaseAnagram);
        
        assertNotNull(lowerCaseResult.get(0));
        assertEquals(lowerCaseResult, capitalResult);
        assertEquals(lowerCaseResult, mixCaseResult);
    }
    
    
    @Test 
    public void testGetAnagramOf_KnownResultsNum() {
        String anagram1 = "Best Secret";
        String anagram2 = "IT-Crowd";
        String anagram3 = "Aschheim";
        
        List<String> result1 = anagramService.getAnagramsOf(anagram1);
        List<String> result2 = anagramService.getAnagramsOf(anagram2);
        List<String> result3 = anagramService.getAnagramsOf(anagram3);
        
        assertEquals( 16, result1.size() );
        assertEquals( 5, result2.size() );
        assertEquals( 7, result3.size() );
    }
    
}
