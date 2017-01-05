package org.jlplayel.anagram.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.StringJoiner;

import org.jlplayel.anagram.tool.LetterTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AnagramDaoImpl implements AnagramDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Override
    public List<String> getAnagrams(String anagramKey, int maxNumWords) {
           
        List<String> noAnagramLetters = LetterTool.getMissingLetters(anagramKey);
        
        StringBuilder querySb = new StringBuilder();
        
        querySb.append("WITH CUT_WORD AS ");
        querySb.append("(SELECT * ");
        querySb.append("   FROM WORD ");
        querySb.append("  WHERE 1=1 ");
        
        for( String letter : noAnagramLetters){
            querySb.append("    AND WORD.");
            querySb.append( letter );
            querySb.append(" IS NULL");
        }
        
        querySb.append(" UNION " );
        querySb.append( getEmtyWordSelectQuery() ); 
        querySb.append(" ) ");
        
        querySb.append( getSqlSelectPart(maxNumWords) );
        querySb.append( getSqlFromPart(maxNumWords) );
        querySb.append( getSqlWherePart(maxNumWords, anagramKey) );
        
        return jdbcTemplate.query(querySb.toString(), getAnagramRowMapper(maxNumWords));
    }
    
    private String getEmtyWordSelectQuery(){
        StringBuilder query = new StringBuilder();
        query.append("SELECT '' AS WORD, 0 AS LENGTH, NULL AS A,NULL AS B,");
        query.append("NULL AS C,NULL AS D,NULL AS E,NULL AS F,NULL AS G,");
        query.append("NULL AS H,NULL AS I,NULL AS J,NULL AS K,NULL AS L,");
        query.append("NULL AS M,NULL AS N,NULL AS O,NULL AS P,NULL AS Q,");
        query.append("NULL AS R,NULL AS S,NULL AS T,NULL AS U,NULL AS V,");
        query.append("NULL AS W,NULL AS X,NULL AS Y,NULL AS Z FROM (VALUES(0))");
        return query.toString();
    }
    
    private String getSqlSelectPart( int wordNum ){
        StringJoiner selectSj = new StringJoiner(", ", " SELECT " , " ");
        for(int num=1; num<wordNum+1; num++){
            selectSj.add("CW"+num+".WORD");
        }
        return selectSj.toString();
    }
    
    private String getSqlFromPart( int wordNum ){
        StringJoiner fromSj = new StringJoiner(", ", " FROM " , " ");
        for(int num=1; num<wordNum+1; num++){
            fromSj.add("CUT_WORD CW"+num);
        }
        return fromSj.toString();
    }
    
    //     WHERE IsNull(CW1.S,0) + IsNull(CW2.S,0) = 2 ");
    //       AND IsNull(CW1.T,0) + IsNull(CW2.T,0) = 2 ");
    private String getSqlWherePart( int wordNum, String anagramKey ){
        StringBuilder whereSb = new StringBuilder();
        
        whereSb.append( " WHERE " );
        
        Map<String,Integer> letterCombination = LetterTool.getLetterAmountMap(anagramKey);
        
        if (letterCombination!=null && !letterCombination.isEmpty()) {
            StringJoiner combCond = new StringJoiner(" AND ");
            
            for (Entry<String, Integer> entry : letterCombination.entrySet()) {
                combCond.add(IntStream
                        .rangeClosed(1, wordNum)
                        .mapToObj(n -> "IsNull(CW" + n + "." + entry.getKey()+",0)")
                        .collect(
                                Collectors.joining(" + ", 
                                                   " ", 
                                                   " = "+ entry.getValue() + " ")));
            }
            whereSb.append( combCond.toString() );
        }
        
        // Avoiding combination of the same words
        for(int j=1; j<wordNum;j++){
            whereSb.append( "AND CW"+j+".WORD <= CW"+(j+1)+".WORD " );  
        }
        
        return whereSb.toString();
    }
    
    private RowMapper<String> getAnagramRowMapper( int maxNumWords ){
        return new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                StringJoiner anagramSj = new StringJoiner(" ");
                for(int j=1; j<maxNumWords+1;j++){
                    anagramSj.add(rs.getString(j));
                }
                return anagramSj.toString().trim();
            }
        };
    }
    

    @Override
    public int getTotalAmountOfAvailableWords() {
        int total = 0;
        
        if( isWordTableCreated() ){
            String sql = "SELECT COUNT(*) FROM WORD";  
            total = jdbcTemplate.queryForObject(sql, Integer.class);
        }
                    
        return total;
    }
    
    
    private boolean isWordTableCreated() {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SYSTEM_TABLES WHERE TABLE_NAME = 'WORD'";
        int total = jdbcTemplate.queryForObject(sql, Integer.class);
        
        if ( total == 0){
            return false;
        }
        return true;
    }


    @Override
    public int addWords(List<String> words) {
        if( !isWordTableCreated() ){
            createWordTable();
        }
        
        int counter = 0;
        for(String word : words){
            counter += addWord(word);
        }
        
        return counter;
    }
    
    private int addWord( String word ){
        
        String cleanWord = LetterTool.getLowerCaseOnlyLetters(word);
        StringBuilder queryKey = new StringBuilder();
        StringBuilder queryValues = new StringBuilder();
        List<Object> sqlParameters = new ArrayList<>();
        
        queryKey.append("INSERT INTO WORD (WORD, LENGTH");
        queryValues.append(") VALUES(?, ?");
        sqlParameters.add(cleanWord);
        sqlParameters.add(cleanWord.length());
        
        Map<String,Integer> letterAmount = LetterTool.getLetterAmountMap(cleanWord);
        
        for( Entry<String,Integer> letterNum : letterAmount.entrySet() ){
            queryKey.append(", ").append(letterNum.getKey());
            queryValues.append(", ?");
            sqlParameters.add(letterNum.getValue().toString());
        }
        queryValues.append(")");
        
        return jdbcTemplate.update(queryKey.append(queryValues).toString(), 
                                   sqlParameters.toArray());
    }
    
    
    private void createWordTable(){
        StringBuilder query = new StringBuilder();
        
        query.append("CREATE TABLE WORD ");
        query.append("(WORD VARCHAR(25), LENGTH TINYINT, ");
        query.append("A TINYINT, B TINYINT, C TINYINT, D TINYINT, E TINYINT, F TINYINT, G TINYINT, ");
        query.append("H TINYINT, I TINYINT, J TINYINT, K TINYINT, L TINYINT, M TINYINT, N TINYINT, ");
        query.append("O TINYINT, P TINYINT, Q TINYINT, R TINYINT, S TINYINT, T TINYINT, U TINYINT, ");
        query.append("V TINYINT, W TINYINT, X TINYINT, Y TINYINT, Z TINYINT)");
        
        jdbcTemplate.execute(query.toString());
    }
   
    
}
