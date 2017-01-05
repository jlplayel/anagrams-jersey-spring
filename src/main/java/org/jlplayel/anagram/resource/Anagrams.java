package org.jlplayel.anagram.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="anagrams")
@XmlType(propOrder={"resultNum", "timeMillisec", "phrase"})
public class Anagrams {
    
    private long timeMillisec;
    private List<String> phrases;
    

    public Anagrams() {
        super();
        phrases = new ArrayList<String>();
    }
    
    
    //@JsonCreator If JSON instead XML is desired
    public Anagrams(/*@JsonProperty("resultNum")*/int resultNum,
                         /*@JsonProperty("timeMillisec")*/ long timeMillisec, 
                         /*@JsonProperty("phrases")*/ List<String> phrases) {
        super();
        this.timeMillisec = timeMillisec;
        this.phrases = phrases;
    }



    public void add(String phrase){
        this.phrases.add(phrase);
    }
    
    public void addAll(List<String> phrases){
        this.phrases.addAll(phrases);
    }
    
    public List<String> getPhrase() {
        return phrases;
    }
    
    @XmlElement
    public void setPhrase(List<String> phrases) {
        this.phrases = phrases;
    }
    
    public long getTimeMillisec() {
        return timeMillisec;
    }
    
    @XmlElement
    public void setTimeMillisec(long timeMillisec) {
        this.timeMillisec = timeMillisec;
    }
    
    @XmlElement
    public int getResultNum(){
        return phrases.size();
    }
    
}
