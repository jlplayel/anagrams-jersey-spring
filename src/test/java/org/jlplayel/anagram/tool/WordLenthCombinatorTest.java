package org.jlplayel.anagram.tool;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class WordLenthCombinatorTest {
    
    private int MIN_WORD_LENGTH = 3;
    
    @Test
    public void testGetValuesFor_totalPhraseLenght0(){
        List<List<Integer>> combinations = 
                WordLengthCombinator.getValuesFor( 0, MIN_WORD_LENGTH);
        
        assertEquals(0, combinations.size());
    }
    
    
    @Test
    public void testGetValuesFor_totalPhraseLenght2(){
        List<List<Integer>> combinations = 
                WordLengthCombinator.getValuesFor( 2, MIN_WORD_LENGTH);

        assertEquals(0, combinations.size());
    }
    
    
    @Test
    public void testGetValuesFor_totalPhraseLenght3(){
        List<List<Integer>> combinations = 
                WordLengthCombinator.getValuesFor( 3, MIN_WORD_LENGTH);
        
        assertEquals(1, combinations.size());
        assertEquals(1, combinations.get(0).size());
        assertEquals(3, combinations.get(0).get(0).longValue());
    }
    
    
    @Test
    public void testGetValuesFor_totalPhraseLenght5(){
        List<List<Integer>> combinations = 
                WordLengthCombinator.getValuesFor( 5, MIN_WORD_LENGTH);
        
        assertEquals(1, combinations.size());
        assertEquals(1, combinations.get(0).size());
        assertEquals(5, combinations.get(0).get(0).longValue());
    }
    
    
    @Test
    public void testGetValuesFor_totalPhraseLenght9(){
        List<List<Integer>> combinations = 
                WordLengthCombinator.getValuesFor( 9, MIN_WORD_LENGTH);

        assertEquals(4, combinations.size());
        
        assertEquals(1, combinations.get(0).size());
        assertEquals(9, combinations.get(0).get(0).longValue());
        
        assertEquals(2, combinations.get(1).size());
        assertEquals(6, combinations.get(1).get(0).longValue());
        assertEquals(3, combinations.get(1).get(1).longValue());
        
        assertEquals(2, combinations.get(2).size());
        assertEquals(5, combinations.get(2).get(0).longValue());
        assertEquals(4, combinations.get(2).get(1).longValue());
        
        assertEquals(3, combinations.get(3).size());
        assertEquals(3, combinations.get(3).get(0).longValue());
        assertEquals(3, combinations.get(3).get(1).longValue());
        assertEquals(3, combinations.get(3).get(2).longValue());
    }
    
    
    @Test
    public void testGetValuesFor_totalPhraseLenght15(){
        
        List<List<Integer>> solution = Arrays.asList(Arrays.asList(15),
                                                     Arrays.asList(12, 3),
                                                     Arrays.asList(11, 4),
                                                     Arrays.asList(10, 5),
                                                     Arrays.asList(9, 6),
                                                     Arrays.asList(8, 7),
                                                     Arrays.asList(9, 3, 3),
                                                     Arrays.asList(8, 4, 3),
                                                     Arrays.asList(7, 5, 3),
                                                     Arrays.asList(7, 4, 4),
                                                     Arrays.asList(6, 5, 4),
                                                     Arrays.asList(5, 5, 5),
                                                     Arrays.asList(6, 3, 3, 3),
                                                     Arrays.asList(5, 4, 3, 3),
                                                     Arrays.asList(4, 4, 4, 3),
                                                     Arrays.asList(3, 3, 3, 3, 3));
        List<List<Integer>> combinations = 
                WordLengthCombinator.getValuesFor( 15, MIN_WORD_LENGTH);
        
        assertEquals(solution, combinations);
    }
}
