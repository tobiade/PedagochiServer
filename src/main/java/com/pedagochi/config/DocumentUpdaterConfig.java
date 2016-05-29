package com.pedagochi.config;

import com.firebase.client.Firebase;
import com.pedagochi.firebase.FirebaseDataService;
import com.pedagochi.lucene.DocumentBuilder;
import com.pedagochi.lucene.DocumentUpdater;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Created by Tobi on 5/27/2016.
 */
@Configuration
public class DocumentUpdaterConfig {
    @Autowired
    private IndexWriter indexWriter;
//    @Autowired @Lazy
//    private IndexReader indexReader;
//    @Autowired @Lazy
//    private IndexSearcher indexSearcher;
    @Autowired
    private FirebaseDataService dataService;
    @Autowired
    private DocumentBuilder documentBuilder;
    @Bean
    public DocumentUpdater documentUpdater(){
        return new DocumentUpdater(indexWriter, dataService, documentBuilder);
    }
}
