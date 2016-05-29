package com.pedagochi.config;

import com.pedagochi.lucene.ContextMatcher;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Created by Tobi on 5/26/2016.
 */
@Configuration

public class ContextMatcherConfig {
    @Autowired @Lazy
    private IndexReader indexReader;
    @Autowired @Lazy
    private IndexSearcher indexSearcher;

    @Bean
    public ContextMatcher contextMatcher(){
        return new ContextMatcher(indexReader, indexSearcher);
    }
}
