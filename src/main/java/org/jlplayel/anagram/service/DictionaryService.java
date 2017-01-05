package org.jlplayel.anagram.service;

import java.util.List;

public interface DictionaryService {
    
    public List<String> getAllWords();
    
    public List<String> getWordsWithoutLetters( List<String> letters );
}
