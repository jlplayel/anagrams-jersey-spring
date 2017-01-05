package org.jlplayel.anagram.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jlplayel.anagram.resource.Anagrams;
import org.jlplayel.anagram.service.AnagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Path("/javasolution") 
@Controller
public class JavaController {

    @Autowired
    @Qualifier("javaAnagram") 
    private AnagramService anagramService;
    
    @GET
    @Path("/anagrams/{phrase}")
    @Produces(MediaType.APPLICATION_XML)
    public Anagrams getAnagrams( @PathParam("phrase") String phrase ){
        
        long initTime = System.currentTimeMillis();
        
        Anagrams result = new Anagrams();
        result.addAll( anagramService.getAnagramsOf( phrase ) );
        result.setTimeMillisec(System.currentTimeMillis() - initTime);
        
        return result;
    }
    
}
//http://localhost:8080/rest/javasolution/anagrams/Monterreal
