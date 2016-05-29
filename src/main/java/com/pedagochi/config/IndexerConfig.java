package com.pedagochi.config;

import com.pedagochi.lucene.Indexer;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Tobi on 5/25/2016.
 */
@Configuration

public class IndexerConfig {
    @Autowired
    private IndexWriter indexWriter;
    @Bean
    public Indexer indexer() throws IOException {

        return new Indexer(indexWriter);
    }
}
