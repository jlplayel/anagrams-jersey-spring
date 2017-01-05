package org.jlplayel.anagram.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jlplayel.anagram.tool.LetterTool;
import org.jlplayel.anagram.tool.WordsCombinator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="javaAnagram")
public class AnagramServiceJavaImpl implements AnagramService {
    
    private final static int MIN_WORD_LENGTH = 3;
    
    @Autowired
    private DictionaryService dictionaryService;

    @Override
    public List<String> getAnagramsOf(String phrase) {
        
        List<String> lettersOutOfPhrase = LetterTool.getMissingLetters(phrase);
        List<String> possibleWords = dictionaryService.getWordsWithoutLetters( lettersOutOfPhrase );
        
        String anagramKey = LetterTool.getAnagramKey(phrase);
        
        List<String> wordCombinations = 
                WordsCombinator.getAllPhraseCombinations( anagramKey.length(), 
                                                          possibleWords, 
                                                          MIN_WORD_LENGTH );
        
        List<String> result = filterByAnagram( wordCombinations , anagramKey );
        
        return result;      
    }
    
    
    private List<String> filterByAnagram( List<String> toFilter, String anagramKey ){
        return toFilter.parallelStream()
                       .filter(w -> anagramKey.equals(LetterTool.getAnagramKey(w)) )
                       .collect(Collectors.toList());
    }

}
