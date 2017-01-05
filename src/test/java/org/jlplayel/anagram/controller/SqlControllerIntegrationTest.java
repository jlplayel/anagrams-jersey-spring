package org.jlplayel.anagram.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.jlplayel.anagram.config.JerseySpringTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SqlControllerIntegrationTest extends JerseySpringTest {
    
    private final String PHRASE = "pystom";
    private final int HTTP_SUCCESS_STATUS_CODE = Response.Status.OK.getStatusCode();
    
    @Override
    protected ResourceConfig configure(){   
        return new ResourceConfig(SqlController.class);
    }
    
    @Test
    public void testJavaSolution_ResponseStatusAndMediaType() {
        Response response = target("sqlsolution/anagrams/"+PHRASE).request().get();
        
        assertEquals(HTTP_SUCCESS_STATUS_CODE, response.getStatus());
        assertEquals(MediaType.APPLICATION_XML, response.getMediaType().toString());
    }

    
    @Test
    public void testJavaSolution_AnagramsContainsResult() {
        String response = target("sqlsolution/anagrams/"+PHRASE).request().get(String.class);
        
        assertTrue( "There should be a resultNum XMLtag with value equal 1.", 
                    response.contains("<resultNum>1</resultNum>") );
        assertTrue( "There should be a phrase XMLtag with value equal 'mop sty'.",
                    response.contains("<phrase>mop sty</phrase>") );
    }

}
