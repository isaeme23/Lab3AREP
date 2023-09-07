package com.arep;

import com.arep.annotation.Componente;
import com.arep.annotation.GetMapping;

@Componente
public class HelloServices {

    @GetMapping("/hello")
    public static String hola(String arg){
        return "Hola "+ arg;
    }

    @GetMapping("/hellopost")
    public static String post(String arg){
        return "Hola post "+arg;
    }
}