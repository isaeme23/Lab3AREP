package com.arep;

import com.arep.annotation.Componente;
import com.arep.annotation.GetMapping;
import com.arep.inter.Mediatory;
import com.arep.inter.impl.ImageMediatory;
import com.arep.inter.impl.TextMediatory;

import java.io.IOException;
import java.net.Socket;

@Componente
public class HelloServices {

    private static Mediatory m;

    @GetMapping("/hello")
    public static String hola(String arg){
        return "Hola "+ arg;
    }

    @GetMapping("/hellopost")
    public static String post(String arg){
        return "Hola post "+arg;
    }

    @GetMapping("/image")
    public static void getImage(String arg, Socket socket) throws IOException {
        System.out.println("Si llega aqui");
        m = new ImageMediatory(arg, socket);
        m.reply();
    }

    @GetMapping("/file")
    public static void getFile(String arg, Socket socket) throws IOException {
        m = new TextMediatory(arg, socket);
        m.reply();
    }
}