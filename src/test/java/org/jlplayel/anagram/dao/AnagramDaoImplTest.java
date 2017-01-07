package org.jlplayel.anagram.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jlplayel.anagram.config.SpringConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AnagramDaoImplTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private AnagramDao anagramDao;
    
    @Before
    public void initAndMockClassUnderTest(){
        if( anagramDao == null ){
            anagramDao = new AnagramDaoImpl();
            cleanDataBaseInfo();
            ReflectionTestUtils.setField(anagramDao, "jdbcTemplate", jdbcTemplate);
        }
    }
    
    
    @Test
    public void testGetTotalAmountOfAvailableWords_notTableExist(){
        int result = anagramDao.getTotalAmountOfAvailableWords();
        
        assertNotNull(result);
        assertEquals(0, result);
    }
    
    @Test
    public void testAddWords(){
        int insertNum = anagramDao.addWords(Arrays.asList("one","two"));
        
        assertEquals(2, insertNum);
    }
    
    @Test
    public void testAddWords_mixingUpperCaseAndCapitalLetters(){
        int insertNum = anagramDao.addWords(Arrays.asList("Alpha"));
        
        assertEquals(1, insertNum);
    }
    
    
    @Test(expected = Exception.class)
    public void testGetAnagramOf_emptyInitialWord() {
        
        String anagramKey = "";
        int maxNumWords = 3;
        anagramDao.getAnagrams(anagramKey, maxNumWords);
    }
    
    @Test
    public void testGetAnagramOf_noDictionaryWords() {
        
        anagramDao.addWords(Collections.emptyList());
        
        String anagramKey = "aaabbc";
        int maxNumWords = 3;
        List<String> result = anagramDao.getAnagrams(anagramKey, maxNumWords);
        
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetAnagramOf_dictionaryCaseSensitive() {
        
        anagramDao.addWords(Arrays.asList("aaa","BBC","bcb","cbb",
                                          "ddd","AAS","acc","bbb"));
        
        String anagramKey = "aaabbc";
        int maxNumWords = 3;
        List<String> result = anagramDao.getAnagrams(anagramKey, maxNumWords);
        
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue("Error! Missing result: aaa bbc.", result.contains("aaa bbc"));
        assertTrue("Error! Missing result: aaa bcb.", result.contains("aaa bcb"));
        assertTrue("Error! Missing result: aaa cbb.", result.contains("aaa cbb"));
    }
    
    
    @Test
    public void testGetAnagramOf_smallSample() {
        
        anagramDao.addWords(Arrays.asList("aaa","bbc","bcb","cbb",
                                          "ddd","aas","acc","bbb",
                                          "aa","ab","cb", "ac", 
                                          "ababac", "aaabbb"));
        
        String anagramKey = "aaabbc";
        int maxNumWords = 3;
        List<String> result = anagramDao.getAnagrams(anagramKey, maxNumWords);
        
        assertNotNull(result);
        assertEquals(6, result.size());
        assertTrue("Error! Missing result: ababac.", result.contains("ababac"));
        assertTrue("Error! Missing result: aaa bbc.", result.contains("aaa bbc"));
        assertTrue("Error! Missing result: aaa bcb.", result.contains("aaa bcb"));
        assertTrue("Error! Missing result: aaa cbb.", result.contains("aaa cbb"));
        assertTrue("Error! Missing result: aa ab cb.", result.contains("aa ab cb"));
        assertTrue("Error! Missing result: ab ab ac.", result.contains("ab ab ac"));
    }
       
    
    @Test
    public void testGetAnagramsOf_descriminateWordsWithExtraLetters(){
        String requestPhrase = "abcdefg";
        List<String> wordsWithTheLetters = Arrays.asList("abcd", "efg",
                                                         "abc", "defg",
                                                         "aabcd",
                                                         "abbcdef");
        anagramDao.addWords( wordsWithTheLetters );
        
        List<String> anagrams = anagramDao.getAnagrams( requestPhrase, 2 );
         
        assertEquals( 2, anagrams.size() );
        assertTrue( "Expected solution is missing.", anagrams.contains("abcd efg") );
        assertTrue( "Expected solution is missing.", anagrams.contains("abc defg") );
    }
    
    
    @Test
    public void testGetAnagramsOf_resultDoesNotContainDuplicates(){
        String requestPhrase = "abcdefg";

        List<String> wordsWithTheLetters = Arrays.asList("abc", "defg",
                                                         "abc", "defg",
                                                         "abcd", "efg");
        anagramDao.addWords( wordsWithTheLetters );
        
        List<String> anagrams = anagramDao.getAnagrams( requestPhrase, 2 );
        Set<String> anagramsSet = anagrams.stream().collect(Collectors.toSet());
         
        assertEquals( 2, anagramsSet.size() );
    }
    
    @Test
    public void testGetAnagramsOf_noDuplicateResultsIfDicWordsContainDuplicate(){
        String requestPhrase = "abcdefg";

        List<String> wordsWithTheLetters = Arrays.asList("abc defg",
                                                         "abcd efg",
                                                         "abcd", "efg");
        anagramDao.addWords( wordsWithTheLetters );
        
        List<String> anagrams = anagramDao.getAnagrams( requestPhrase, 2 );
        Set<String> anagramsSet = anagrams.stream().collect(Collectors.toSet());
        
        assertEquals( 2, anagramsSet.size() );
    }
    
    
    private void cleanDataBaseInfo(){
        jdbcTemplate.execute("TRUNCATE SCHEMA public AND COMMIT");
    }
      
}
