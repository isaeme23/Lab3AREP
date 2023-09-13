package com.arep;

import com.arep.annotation.Componente;
import com.arep.annotation.GetMapping;
import com.arep.inter.Mediatory;
import com.arep.inter.impl.ImageMediatory;
import com.arep.inter.impl.TextMediatory;

import java.io.IOException;
import java.net.Socket;

/**
 * Controlador de la aplicacion web
 * @author Isabella Manrique
 * @version 13-9-2023
 */
@Componente
public class HelloServices {

    // Atributos de la clase
    private static Mediatory m;

    /**
     * Respyesta al path "/hella"
     * @param arg String
     * @return Texto de respuesta
     */
    @GetMapping("/hello")
    public static String hola(String arg){
        return "Hola "+ arg;
    }

    /**
     * Respyesta al path "/hellapost"
     * @param arg String
     * @return Texto de respuesta
     */
    @GetMapping("/hellopost")
    public static String post(String arg){
        return "Hola post "+arg;
    }

    /**
     * Respuesta al path "/image"
     * @param arg Nombre de la imagen
     * @param socket Socket a conectar
     * @throws IOException Excepcion de entrada-salida
     */
    @GetMapping("/image")
    public static void getImage(String arg, Socket socket) throws IOException {
        System.out.println("Si llega aqui");
        m = new ImageMediatory(arg, socket);
        m.reply();
    }

    /**
     * Respuesta al path "/file"
     * @param arg Nombre del archivo
     * @param socket Socket a conectar
     * @throws IOException Excepcion de entrada-salida
     */
    @GetMapping("/file")
    public static void getFile(String arg, Socket socket) throws IOException {
        m = new TextMediatory(arg, socket);
        m.reply();
    }
}