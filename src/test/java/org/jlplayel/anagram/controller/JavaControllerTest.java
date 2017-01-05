package org.jlplayel.anagram.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.jlplayel.anagram.config.JerseySpringTest;
import org.jlplayel.anagram.service.AnagramService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class JavaControllerTest  extends JerseySpringTest{
    private final String PHRASE = "pystom";
    private final int HTTP_SUCCESS_STATUS_CODE = Response.Status.OK.getStatusCode();
    private final List<String> result = Arrays.asList("anagram1", "ana gram2", "ana gram 3");
    
    private JavaController javaController;
    private AnagramService anagramService;
    
    @Override
    protected ResourceConfig configure(){
        javaController = new JavaController();
        return new ResourceConfig().register(javaController);
    }
    
    @Before
    public void initiation(){
        if( anagramService == null ){
            anagramService = Mockito.mock(AnagramService.class);
            ReflectionTestUtils.setField(javaController, "anagramService", anagramService);
        }
        else{
            Mockito.reset(anagramService);
        }
    }
    
    @Test
    public void testJavaController_ResponseStatusAndMediaType() {
        Mockito.when( anagramService.getAnagramsOf(Mockito.anyString()) )
               .thenReturn(result);
        
        Response response = target("javasolution/anagrams/"+PHRASE).request().get();
        
        assertEquals(HTTP_SUCCESS_STATUS_CODE, response.getStatus());
        assertEquals(MediaType.APPLICATION_XML, response.getMediaType().toString());
    }
    
    
    @Test
    public void testJavaController_AnagramsContainsResult() {
        
        Mockito.when( anagramService.getAnagramsOf(Mockito.anyString()) )
               .thenReturn(result);
        
        String response = target("javasolution/anagrams/"+PHRASE).request().get(String.class);
        
        assertTrue( "There should be a resultNum XMLtag with value equal 1.", 
                    response.contains("<resultNum>3</resultNum>") );
        assertTrue( "There should be a phrase XMLtag with value equal 'mop sty'.",
                    response.contains("<phrase>ana gram 3</phrase>") );
    }
    
}
