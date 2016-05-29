package com.pedagochi.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.pedagochi.app.FirebasePedagochiApp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by Tobi on 5/24/2016.
 */
@Configuration

public class FirebasePedagochiAppConfig {
    @Value("${firebase.root}")
    private String rootUrl;

//    @Bean
//    public FirebasePedagochiApp firebasePedagochiApp(){
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setServiceAccount(this.getClassLoader().getResourceAsStream("com/pedagochi/firebase/My First App-0ec3afcfe8e3.json"))
//                .setDatabaseUrl(rootUrl)
//                .build();
//
//        FirebaseApp.initializeApp(options);
//        return app;
//    }



    public String getRootUrl() {
        return rootUrl;
    }
}
