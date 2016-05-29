package com.pedagochi.config;

import com.pedagochi.firebase.FirebaseDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tobi on 5/25/2016.
 */
@Configuration
public class FirebaseDataServiceConfig {
    @Bean
    public FirebaseDataService firebaseDataService(){
        return new FirebaseDataService();
    }
}
