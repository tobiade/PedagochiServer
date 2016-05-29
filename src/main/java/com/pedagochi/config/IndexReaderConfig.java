package com.pedagochi.config;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Tobi on 5/27/2016.
 */
@Configuration
@Lazy
public class IndexReaderConfig {
    @Value("${index.directory}")
    String indexPath;
    @Autowired
    Directory indexDir;

    @Bean
    public IndexReader indexReader() throws IOException {
        //Directory indexDir = FSDirectory.open(Paths.get(indexPath));
        IndexReader indexReader  = DirectoryReader.open(indexDir);
        return indexReader;

    }
}
