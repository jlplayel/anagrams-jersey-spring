package org.jlplayel.anagram.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AnagramServiceJavaImplTest {
    
    AnagramService anagramService;
    DictionaryService dictionaryService;
    
    
    @Before
    public void initAndMockClassUnderTest(){
        if( anagramService == null ){
            anagramService = new AnagramServiceJavaImpl();
            dictionaryService = Mockito.mock(DictionaryService.class);
            ReflectionTestUtils.setField(anagramService, "dictionaryService", dictionaryService);
        }
        else{
            Mockito.reset(dictionaryService);
        }
    }
    
    
    @Test
    public void testGetAnagramsOf_emptyPhraseGetsNoResults(){
        String requestPhrase = "";
        Mockito.when( dictionaryService.getWordsWithoutLetters(Mockito.anyList()) )
               .thenReturn( Collections.emptyList() );
        
        List<String> anagrams =anagramService.getAnagramsOf( requestPhrase );
         
        assertEquals( 0, anagrams.size() );
    }
    
    
    @Test
    public void testGetAnagramsOf_emptyPhraseGetsNoResults2(){
        String requestPhrase = "";
        List<String> wordsWithTheLetters = Arrays.asList("abcd", "efg",
                                                         "abc", "defg",
                                                         "aabcd",
                                                         "abbcdef");
        Mockito.when( dictionaryService.getWordsWithoutLetters(Mockito.anyList()) )
               .thenReturn( wordsWithTheLetters );
        
        List<String> anagrams =anagramService.getAnagramsOf( requestPhrase );
         
        assertEquals( 0, anagrams.size() );
    }   
    
    
    @Test
    public void testGetAnagramsOf_descriminateDicWordsUnderThreeLetters(){
        String requestPhrase = "abcdefg";
        List<String> wordsWithTheLetters = Arrays.asList("ab","cd","de", "f",
                                                         "abcd", "efg",
                                                         "abc", "defg");
        Mockito.when( dictionaryService.getWordsWithoutLetters(Mockito.anyList()) )
               .thenReturn( wordsWithTheLetters );
        
        List<String> anagrams =anagramService.getAnagramsOf( requestPhrase );
         
        assertEquals( 2, anagrams.size() );
        assertTrue( "Expected solution is missing.", anagrams.contains("abcd efg") );
        assertTrue( "Expected solution is missing.", anagrams.contains("abc defg") );
    }
    
    
    @Test
    public void testGetAnagramsOf_descriminateWordsWithExtraLetters(){
        String requestPhrase = "abcdefg";
        List<String> wordsWithTheLetters = Arrays.asList("abcd", "efg",
                                                         "abc", "defg",
                                                         "aabcd",
                                                         "abbcdef");
        Mockito.when( dictionaryService.getWordsWithoutLetters(Mockito.anyList()) )
               .thenReturn( wordsWithTheLetters );
        
        List<String> anagrams =anagramService.getAnagramsOf( requestPhrase );
         
        assertEquals( 2, anagrams.size() );
        assertTrue( "Expected solution is missing.", anagrams.contains("abcd efg") );
        assertTrue( "Expected solution is missing.", anagrams.contains("abc defg") );
    }
    
    
    @Test
    public void testGetAnagramsOf_resultDoesNotContainDuplicates(){
        String requestPhrase = "abcdefg";
        //Testing with unexpected duplicates in the possible dictionary words:
        List<String> wordsWithTheLetters = Arrays.asList("abc", "defg",
                                                         "abc", "defg",
                                                         "abcd", "efg");
        Mockito.when( dictionaryService.getWordsWithoutLetters(Mockito.anyList()) )
               .thenReturn( wordsWithTheLetters );
        
        List<String> anagrams =anagramService.getAnagramsOf( requestPhrase );
        Set<String> anagramsSet = anagrams.stream().collect(Collectors.toSet());
         
        assertEquals( 2, anagramsSet.size() );
    }
    
    @Test
    public void testGetAnagramsOf_noDuplicateResultsIfDicWordsContainPhrases(){
        String requestPhrase = "abcdefg";
        //Testing with unexpected phrases in the possible dictionary words:
        List<String> wordsWithTheLetters = Arrays.asList("abc defg",
                                                         "defg abc",
                                                         "abcd", "efg");
        Mockito.when( dictionaryService.getWordsWithoutLetters(Mockito.anyList()) )
               .thenReturn( wordsWithTheLetters );
        
        List<String> anagrams =anagramService.getAnagramsOf( requestPhrase );
        Set<String> anagramsSet = anagrams.stream().collect(Collectors.toSet());
         
        assertEquals( 1, anagramsSet.size() );
    }
    
}
