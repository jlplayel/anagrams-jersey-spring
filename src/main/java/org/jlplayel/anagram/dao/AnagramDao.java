package org.jlplayel.anagram.dao;

import java.util.List;

public interface AnagramDao {
    
    public int getTotalAmountOfAvailableWords();
    
    public int addWords(List<String> words);
    
    public List<String> getAnagrams(String anagramKey, int maxNumWords);
    
}
