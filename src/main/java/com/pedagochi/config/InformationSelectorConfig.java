package com.pedagochi.config;

import com.pedagochi.user.InformationSelector;
import org.apache.lucene.search.IndexSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Created by Tobi on 5/27/2016.
 */
@Configuration
public class InformationSelectorConfig {
    @Autowired @Lazy
    IndexSearcher indexSearcher;

    @Bean
    public InformationSelector informationSelector(){
        return new InformationSelector(indexSearcher);
    }
}
