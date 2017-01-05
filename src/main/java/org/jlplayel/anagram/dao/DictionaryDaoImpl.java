package org.jlplayel.anagram.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class DictionaryDaoImpl implements DictionaryDao {
    
    private final String FILE_PATH = "org/jlplayel/anagram/dao/";
    private final String FILE_NAME = "wordlist.txt";
    
    private List<String> words;

    @Override
    public List<String> getAllWords() {
        if( words == null){
            loadAllWords();
        }
        return words;
    }
    
    private void loadAllWords(){
        final DefaultResourceLoader loader = new DefaultResourceLoader();
        
        Resource resource = loader.getResource( FILE_PATH + FILE_NAME );
        try {
            words = getWordsListFrom( resource.getInputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }           
    }
    
    private List<String> getWordsListFrom( InputStream inputStream )
    {    
         List<String> dicWords = null;
         BufferedReader br = null;
         
         try {
              br = new BufferedReader( new InputStreamReader(inputStream) );
              dicWords = new ArrayList<String>();
              
              String line = br.readLine();
              while ( line!=null ) {   
                  dicWords.add( line.trim() );
                  line = br.readLine();
              }

         } catch (IOException e) {
               e.printStackTrace();  
         } finally {
             closeBufferedReader( br );
         }
         
         return dicWords;
    }
    
    
    private void closeBufferedReader( BufferedReader br ){
        try {
            br.close();
        } 
        catch (IOException e) {
           e.printStackTrace();
        }
    }
    

}
