package com.pedagochi.config;

import com.pedagochi.lucene.ContextQueryBuilder;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Tobi on 5/25/2016.
 */
@Configuration
public class ContextQueryBuilderConfig {


    @Bean
    public ContextQueryBuilder contextQueryBuilder() {

        return  new ContextQueryBuilder();
    }
}
