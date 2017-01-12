package org.jlplayel.anagram.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.jlplayel.anagram.dao.AnagramDao;
import org.jlplayel.anagram.tool.LetterTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="sqlAnagram")
public class AnagramServiceSqlImpl implements AnagramService {
    
    private final static int MIN_WORD_LENGTH = 3;
    
    @Autowired
    private AnagramDao anagramDao;
    
    @Autowired
    private DictionaryService dictionaryService;
    
    @PostConstruct
    public void init() {
        loadAllWordsIfTheyAreMissing();
    }
    
    @Override
    public List<String> getAnagramsOf(String phrase) {
        
        String anagramKey = LetterTool.getAnagramKey(phrase);
        
        if( !isValidPhrase(anagramKey) ){
            return Collections.emptyList();
        }
        
        loadAllWordsIfTheyAreMissing();
        
        int maxNumOfWords = anagramKey.length()/MIN_WORD_LENGTH;
        
        return anagramDao.getAnagrams(anagramKey, maxNumOfWords);
    }
    
    
    
    private void loadAllWordsIfTheyAreMissing(){
        if(anagramDao.getTotalAmountOfAvailableWords() == 0){
            List<String> words = filterLength(dictionaryService.getAllWords());
            anagramDao.addWords(words);
        }
    }
    
    
    private List<String> filterLength(List<String> unfilterWords ){
        return unfilterWords.stream()
                            .filter( word -> word.length()>=MIN_WORD_LENGTH )
                            .collect(Collectors.toList());
    }
    
    private boolean isValidPhrase( String phrase ){
        if( phrase==null || phrase.length()<MIN_WORD_LENGTH ){
            return false;
        }
        return true;
    }

}
