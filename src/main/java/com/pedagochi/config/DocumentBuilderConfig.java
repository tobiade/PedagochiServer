package com.pedagochi.config;

import com.google.firebase.database.FirebaseDatabase;
import com.pedagochi.firebase.FirebaseDataService;
import com.pedagochi.lucene.DocumentBuilder;
import com.pedagochi.lucene.Indexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tobi on 5/25/2016.
 */
@Configuration
public class DocumentBuilderConfig {
    @Autowired
    private FirebaseDataService dataService;
    @Autowired
    private Indexer indexer;

    @Bean
    public DocumentBuilder documentBuilder(){
        return new DocumentBuilder(dataService,indexer);
    }
}
