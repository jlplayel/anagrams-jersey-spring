package org.jlplayel.anagram.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class WordsCombinator {         
    
    
    /**
     * Given a list of words and number of letters, return a list of sentence that are combinations
     * of the list and have the exactly number of letters.
     * 
     * @param letterNum, the exactly number of letter in the solution senteces.
     * @param words. This words are going to be combined.
     * @return List of sentence with all combinations of words with lenth 3.
     */
    public static List<String> getAllPhraseCombinations( int letterNum,
                                                         List<String> words,
                                                         int minWordLength){
        Map<Integer,List<String>> lengthWord = generateLengthWordMap( words );
        
        List<String> result = new ArrayList<>();
        
        List<List<Integer>> combinations = 
                WordLengthCombinator.getValuesFor(letterNum, minWordLength);
        
        for( List<Integer> lengths : combinations ){
            if ( areThereWordsWithNeededLengths(lengths, lengthWord) ) {
                List<List<String>> wordsToCombine = lengths.parallelStream()
                                                           .map( n -> lengthWord.get(n) )
                                                           .collect(Collectors.toList());
                if ( !isEmpty(wordsToCombine) ) {
                    List<List<String>> phraseSets = combineLists(wordsToCombine);
                    Set<String> phrases = joinWords( phraseSets );
                    result.addAll(phrases);
                }
            }
        }
        return result;
    }
    
    private static boolean areThereWordsWithNeededLengths( List<Integer> lengths,
                                                           Map<Integer,List<String>> lengthWord){
        return lengthWord.keySet().containsAll( lengths );
    }
    
    private static boolean isEmpty( List<List<String>> wordsComb ){
        return wordsComb.size()==0 || wordsComb.get(0) == null;
    }
    
    // { {A,B,C} , {a,b} } --> { {A,a}, {B,a}, {C,a}, {A,b}, {B,b}, {C,b}}
    private static List<List<String>> combineLists(List<List<String>> lists) {
        List<List<String>> combinations = Arrays.asList(Arrays.asList());
        for (List<String> list : lists) {
            List<List<String>> extraColumnCombinations = new ArrayList<>();
            for (List<String> combination : combinations) {
                for (String element : list) {
                    List<String> newCombination = new ArrayList<>(combination);
                    newCombination.add(element);
                    extraColumnCombinations.add(newCombination);
                }
            }
            combinations = extraColumnCombinations;
        }
        return combinations;
    }
    
    
    private static Set<String> joinWords( List<List<String>> phraseSets ){
        return phraseSets.parallelStream()
                         .map( l -> l.stream()
                                     .sorted()
                                     .collect(Collectors.joining (" ")) )
                         .collect(Collectors.toSet());
    }
     
    
    // Generate the Map<wordLength, words> {2=[fd, ab, gt], 4=[abcd, 1234], 5=[fghiw], 6=[lriwjf]}
    private static Map<Integer,List<String>> generateLengthWordMap( List<String> words ){
        return words.parallelStream()
                    .collect(Collectors.groupingBy(String::length, Collectors.toList()));
    }
    
}
