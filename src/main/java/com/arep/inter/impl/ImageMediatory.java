package com.arep.inter.impl;

import com.arep.inter.Mediatory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase que se encarga de mostrar las imagenes para la aplicacion web
 * @author Isabella Manrique
 * @version 13-9-2023
 */
public class ImageMediatory implements Mediatory {

    // Atributos de la clase
    String path;
    Socket client;
    String outputLine = "HTTP/1.1 200 OK \r\n";

    /**
     * Constructor de la clase
     * @param path Ruta
     * @param client Socket a conectar
     */
    public ImageMediatory(String path, Socket client){
        this.path = path;
        this.client = client;
    }

    /**
     * Metodo encargado de responder con la imagen encontrada dependiendo de la ruta
     * @throws IOException Excepcion de entrada-salida
     */
    @Override
    public void reply() throws IOException {
        String type = typeFile();
        Path p = Paths.get("target/classes/public/"+path);
        BufferedImage bImage = ImageIO.read(new File(p.toUri()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, type, bos );

        OutputStream clientout = client.getOutputStream();
        String contentType = outputLine + "Content-Type: image/"+type+" \r\n" + "\r\n";
        clientout.write(contentType.getBytes());
        clientout.write(bos.toByteArray());
        clientout.close();

        client.close();
    } // Cierre del metodo

    /**
     * Metodo encargado de devolver el tipo de la imagen almacenada en la ruta
     * @return String con el tipo de la imagen
     */
    @Override
    public String typeFile() {
        if (path.contains(".")) {
            return path.split("\\.")[1];
        } else{
            return "";
        }
    }// Cierre del metodo
} // Cierre de la clase
