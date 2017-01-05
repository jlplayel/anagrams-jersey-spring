package org.jlplayel.anagram.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordLengthCombinator {
    
    private final int minWordLength;
    
    private int totalLength;
    private List<List<Integer>> result;

    public WordLengthCombinator( int totalLength, int minWordLength) {
        this.minWordLength = minWordLength;
        this.totalLength = totalLength;
        this.result = new ArrayList<>();
        generateWordLengthOptions();
    }
    
    private void generateWordLengthOptions(){
        int maxNumWord = totalLength/minWordLength;
        
        this.result.add(Arrays.asList( totalLength ));
        
        if( maxNumWord >= 2){
            for( int numWords = 2 ; numWords <= maxNumWord ; numWords++ ){
                generateResultFor( numWords );
            }
        }
    }

    private void generateResultFor( int numWords ){
        int[] value = new int[numWords];
        
        //Init the array
        value[0] = totalLength - minWordLength*(numWords-1);
        for(int i=1; i<numWords; i++){
            value[i] = minWordLength;
        }
        result.add( IntStream.of(value).boxed().collect(Collectors.toList()) );
        
        while( !maxDifIsOnlytOne(value, numWords) ){
            moveOne( value, numWords );
            result.add( IntStream.of(value).boxed().collect(Collectors.toList()) );
        }
    }
    
    private void moveOne( int[] value, int numWords ){
        boolean change = false;
        for(int i=value.length-1; i>0; i--){
            if( value[i-1] > value[i]+1){
                value[i-1] = value[i-1]-1;
                value[i] = value[i]+1;
                change = true;
                break;
            }
        }
        
        if( !change && !maxDifIsOnlytOne(value, numWords) ){
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
    
    private boolean maxDifIsOnlytOne( int[] value, int numWords){
        if( totalLength%numWords==0 && value[0] == value[value.length-1]){
            return true;
        }
        if( totalLength%numWords>0 && value[0] == value[value.length-1]+1){
            return true;
        }
        return false;
    }
    

    public List<List<Integer>> getResult() {
        return result;
    }
    
}
