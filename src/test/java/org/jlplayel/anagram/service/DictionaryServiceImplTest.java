package org.jlplayel.anagram.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jlplayel.anagram.dao.DictionaryDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class DictionaryServiceImplTest {
    
    DictionaryService dictionaryService;
    DictionaryDao dictionaryDao;
    
    @Before
    public void initAndMockClassUnderTest(){
        if( dictionaryService == null ){
            dictionaryService = new DictionaryServiceImpl();
            dictionaryDao = Mockito.mock(DictionaryDao.class);
            ReflectionTestUtils.setField(dictionaryService, "dictionaryDao", dictionaryDao);
        }
        else{
            Mockito.reset(dictionaryDao);
        }
    }
    
    
    @Test
    public void testGetAllWords_emptyDictionaryDao(){
        
        Mockito.when( dictionaryDao.getAllWords() )
               .thenReturn( Collections.emptyList() );
        
        List<String> words = dictionaryService.getAllWords();
        
        assertNotNull(words);
        assertTrue(words.isEmpty());
    }
    
    
    @Test
    public void testGetAllWords_severalWordsInDictionaryDao(){
        List<String> wordsInDictionary = Arrays.asList("one", "two", "three", "four", "five");
        
        Mockito.when( dictionaryDao.getAllWords() )
               .thenReturn( wordsInDictionary );
        
        List<String> words = dictionaryService.getAllWords();
        
        assertNotNull(words);
        assertFalse("The service should return some words.", words.isEmpty());
        assertTrue( "There should be exactly 5 words in this method test", words.size() == 5);
        assertTrue("There should not be lost words.", words.containsAll(wordsInDictionary));
    }
    
    
    @Test
    public void testGetWordsWithoutLetters_noLetterToConsider(){
        
        List<String> noDesiredLetters = Collections.emptyList();
        List<String> wordsInDictionary = Arrays.asList("one", "two", "three", "four", "five");
        
        Mockito.when( dictionaryDao.getAllWords() )
               .thenReturn( wordsInDictionary );
        
        List<String> words = dictionaryService.getWordsWithoutLetters(noDesiredLetters);
        
        assertTrue(words.size()==5);
        assertTrue(words.containsAll(wordsInDictionary));
    }
    
    
    @Test
    public void testGetWordsWithoutLetters_noWordsToConsider(){
        
        List<String> noDesiredLetters = Arrays.asList("o","t");
        List<String> wordsInDictionary = Collections.emptyList();
        
        Mockito.when( dictionaryDao.getAllWords() )
               .thenReturn( wordsInDictionary );
        
        List<String> words = dictionaryService.getWordsWithoutLetters(noDesiredLetters);
        
        assertTrue(words.isEmpty());
    }
    
    
    @Test
    public void testGetWordsWithoutLetters_affectedWordsAreNotConsidered(){
        
        List<String> noDesiredLetters = Arrays.asList("o","t");
        List<String> wordsInDictionary = Arrays.asList("one", "two", "three", "four", "five");
        
        Mockito.when( dictionaryDao.getAllWords() )
               .thenReturn( wordsInDictionary );
        
        List<String> words = dictionaryService.getWordsWithoutLetters(noDesiredLetters);
        
        assertTrue(words.size()==1);
        assertTrue(words.contains("five"));
    }
    
    @Test
    public void testGetWordsWithoutLetters_allWordsWithAffectedLetter(){
        
        List<String> noDesiredLetters = Arrays.asList("o","e");
        List<String> wordsInDictionary = Arrays.asList("one", "two", "three", "four", "five");
        
        Mockito.when( dictionaryDao.getAllWords() )
               .thenReturn( wordsInDictionary );
        
        List<String> words = dictionaryService.getWordsWithoutLetters(noDesiredLetters);
        
        assertNotNull(words);
        assertTrue(words.isEmpty());
    }
    
    
    @Test
    public void testGetWordsWithoutLetters_noWordsWithAffectedLetter(){
        
        List<String> noDesiredLetters = Arrays.asList("a","b");
        List<String> wordsInDictionary = Arrays.asList("one", "two", "three", "four", "five");
        
        Mockito.when( dictionaryDao.getAllWords() )
               .thenReturn( wordsInDictionary );
        
        List<String> words = dictionaryService.getWordsWithoutLetters(noDesiredLetters);
        
        assertTrue(words.size()==5);
        assertTrue(words.containsAll(wordsInDictionary));
    }
    
}
