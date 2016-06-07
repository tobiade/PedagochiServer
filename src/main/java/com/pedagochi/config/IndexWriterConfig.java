package com.pedagochi.config;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Tobi on 5/27/2016.
 */
@Configuration
public class IndexWriterConfig {
    @Value("${index.directory}")
    String indexPath;
    @Autowired
    Directory indexDir;

    @Bean
    public IndexWriter indexWriter() throws IOException {
        //FileUtils.cleanDirectory(new File(indexPath));
        //Directory indexDir = FSDirectory.open(Paths.get(indexPath));
        //Directory indexDir = new RAMDirectory();
        org.apache.lucene.index.IndexWriterConfig config = new org.apache.lucene.index.IndexWriterConfig(new StandardAnalyzer());
        IndexWriter indexWriter = new IndexWriter(indexDir, config);
        return indexWriter;
    }
}
