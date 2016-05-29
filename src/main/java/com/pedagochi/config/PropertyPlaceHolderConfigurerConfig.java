package com.pedagochi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by Tobi on 5/25/2016.
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:com/pedagochi/firebase/FirebaseConfig.properties"),
        @PropertySource("classpath:Lucene/LuceneConfig.properties")
})

//@PropertySource("classpath:com/pedagochi/firebase/FirebaseConfig.properties, Lucene/LuceneConfig.properties" )
//@PropertySource("classpath:Lucene/LuceneConfig.properties")
public class PropertyPlaceHolderConfigurerConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
