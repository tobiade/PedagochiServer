package com.tobi.finalyear;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@RestController
public class Controller
{

    @RequestMapping("/")
    public String test(){
        return "Greetings!";
    }
   /* public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }*/
}
