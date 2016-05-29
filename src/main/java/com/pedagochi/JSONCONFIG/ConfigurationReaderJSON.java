package com.pedagochi.JSONCONFIG;

import java.io.InputStream;

/**
 * Created by Tobi on 5/24/2016.
 */
public class ConfigurationReaderJSON {

    public static void main (String[] args){
        InputStream in = ConfigurationReaderJSON.class.getClassLoader().getResourceAsStream("com/pedagochi/firebase/My First App-0ec3afcfe8e3.json");
        System.out.println(in.toString());
    }

}
