package com.pedagochi.config;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tobi on 5/29/2016.
 */
@Configuration
public class DirectoryConfig {

    @Bean
    public Directory directory(){
        return new RAMDirectory();
    }
}
