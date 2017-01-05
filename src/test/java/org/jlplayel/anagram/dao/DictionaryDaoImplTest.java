package org.jlplayel.anagram.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DictionaryDaoImplTest {
    
    DictionaryDao dictionaryDao;
    
    @Before
    public void initAndMockClassUnderTest(){
        if( dictionaryDao == null ){
            dictionaryDao = new DictionaryDaoImpl();
        }
    }
    
    @Test
    public void testGetAllWords_withResults(){
        List<String> words = dictionaryDao.getAllWords();
        
        assertNotNull(words);
        assertFalse(words.isEmpty());
        assertTrue( words.size() > 50000 );
    }
    
}
