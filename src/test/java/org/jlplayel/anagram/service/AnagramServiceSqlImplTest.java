package org.jlplayel.anagram.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.jlplayel.anagram.dao.AnagramDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AnagramServiceSqlImplTest {
    
    AnagramService anagramService;
    DictionaryService dictionaryService;
    AnagramDao anagramDao;
    
    
    @Before
    public void initAndMockClassUnderTest(){
        if( anagramService == null ){
            anagramService = new AnagramServiceSqlImpl();
            
            dictionaryService = Mockito.mock(DictionaryService.class);
            ReflectionTestUtils.setField(anagramService, "dictionaryService", dictionaryService);
            
            anagramDao = Mockito.mock(AnagramDao.class);
            ReflectionTestUtils.setField(anagramService, "anagramDao", anagramDao);
        }
        else{
            Mockito.reset(dictionaryService);
            Mockito.reset(anagramDao);
        }
    }
    
    
    @Test
    public void testGetAnagramsOf_emptyPhraseGetsNoResults(){
        String requestPhrase = "";
        
        List<String> anagrams = anagramService.getAnagramsOf( requestPhrase );
         
        assertEquals( 0, anagrams.size() );
    }
    
    @Test
    public void testGetAnagramsOf_validationCheck(){
        String requestPhrase = "!@#$ %^&*";
        
        List<String> anagrams = anagramService.getAnagramsOf( requestPhrase );
         
        assertEquals( 0, anagrams.size() );
    }
    
    
    @Test
    public void testGetAnagramsOf_passAnagramKeyAndWordToDao(){
        String requestPhrase = "BA DCeFg!$%";
        List<String> anagrams = Arrays.asList("abcd efg", "abc defg");
        Mockito.when( anagramDao.getAnagrams("abcdefg", 2) )
               .thenReturn( anagrams );
        
        List<String> result = anagramService.getAnagramsOf( requestPhrase );
         
        assertEquals( 2, result.size() );
        assertTrue( "Expected solution is missing.", result.contains("abcd efg") );
        assertTrue( "Expected solution is missing.", result.contains("abc defg") );
    }
    
}
