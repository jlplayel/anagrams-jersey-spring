package org.jlplayel.anagram.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.jlplayel.anagram.dao.DictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryServiceImpl implements DictionaryService{
    
    @Autowired
    private DictionaryDao dictionaryDao;
    
    
    @Override
    public List<String> getAllWords() {
        
        return dictionaryDao.getAllWords();
    }
    
    @Override
    public List<String> getWordsWithoutLetters( List<String> letters ){
        
        List<String> words = getAllWords();

        return words.parallelStream()
                    .filter( word->!isWordContainingAnyLetter(word, letters) )
                    .collect(Collectors.toList());
    }
    
    
    private boolean isWordContainingAnyLetter( String word, List<String> letters){
        return CollectionUtils.containsAny(
                word.chars()
                    .mapToObj( character->Character.toString((char)character) )
                    .collect( Collectors.toList()), letters );
    }

}
