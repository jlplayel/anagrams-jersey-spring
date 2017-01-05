package org.jlplayel.anagram.config;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {
    public JerseyApplication() {
        packages("org.jlplayel.anagram.controller");
    }
}
