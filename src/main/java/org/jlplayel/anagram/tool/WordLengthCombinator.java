package org.jlplayel.anagram.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordLengthCombinator {
    
    /**
     * Generates the possible word combinations from min word length considered and the total
     * phrase length.
     * 
     * For example, if it is consider that the words in the combination has to have a minimum
     * of three letters and the phrase size is 9, the possible length combination are:
     * 
     *   {9}, {6, 3}, {5, 4}, {3, 3, 3}
     *   
     * All words with length 9, or a word with length 6 and another with length 3, or a word
     * with length 5 and another with length 4, or three words with length 3.
     * 
     * @param totalLength
     * @param minWordLength
     * @return
     */
    public static List<List<Integer>> getValuesFor(int totalLength, int minWordLength){
        
        List<List<Integer>> result = new ArrayList<>();
        
        int maxNumWord = totalLength/minWordLength;
        
        if(totalLength >= minWordLength){
            result.add(Arrays.asList( totalLength ));    
        }
        
        if( maxNumWord >= 2){
            for( int numWords = 2 ; numWords <= maxNumWord ; numWords++ ){
                result.addAll( generateResultFor( numWords, totalLength, minWordLength) );
            }
        }
        
        return result;
    }

    private static List<List<Integer>> generateResultFor( int numWords, 
                                                          int totalLength, 
                                                          int minWordLength){
        List<List<Integer>> result = new ArrayList<>();
        
        int[] value = new int[numWords];
        
        //Init the array
        value[0] = totalLength - minWordLength*(numWords-1);
        for(int i=1; i<numWords; i++){
            value[i] = minWordLength;
        }
        result.add( IntStream.of(value).boxed().collect(Collectors.toList()) );
        
        while( !maxDifIsOnlytOne(value, numWords, totalLength) ){
            moveOne( value, numWords, minWordLength );
            result.add( IntStream.of(value).boxed().collect(Collectors.toList()) );
        }
        
        return result;
    }
    
    private static void moveOne( int[] value, int numWords, int totalLength ){
        boolean change = false;
        for(int i=value.length-1; i>0; i--){
            if( value[i-1] > value[i]+1){
                value[i-1] = value[i-1]-1;
                value[i] = value[i]+1;
                change = true;
                break;
            }
        }
        
        if( !change && !maxDifIsOnlytOne(value, numWords, totalLength) ){
            int likeLast = value.length-1;
            for(int i=value.length-2; i>=0; i--){
                if( value[i] == value[value.length-1]){
                    likeLast = i;
                }
                else if( value[i] == value[value.length-1]+2 ){
                    value[i] = value[i]-1;
                    value[likeLast] = value[likeLast]+1;
                    break;
                }
            }
        }
    }
    
    private static boolean maxDifIsOnlytOne( int[] value, int numWords, int totalLength){
        if( totalLength%numWords==0 && value[0] == value[value.length-1]){
            return true;
        }
        if( totalLength%numWords>0 && value[0] == value[value.length-1]+1){
            return true;
        }
        return false;
    }
    
}
