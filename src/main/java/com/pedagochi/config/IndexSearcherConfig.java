package com.pedagochi.config;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Created by Tobi on 5/27/2016.
 */
@Configuration
@Lazy
public class IndexSearcherConfig {
    @Autowired
    IndexReader indexReader;
    @Bean
    public IndexSearcher indexSearcher(){
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //indexSearcher.setSimilarity(new ClassicSimilarity());
        return indexSearcher;
    }
}
