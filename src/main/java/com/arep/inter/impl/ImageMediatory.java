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

public class ImageMediatory implements Mediatory {

    String path;
    Socket client;
    String outputLine = "HTTP/1.1 200 OK \r\n";

    public ImageMediatory(String path, Socket client){
        this.path = path;
        this.client = client;
    }
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
    }

    @Override
    public String typeFile() {
        if (path.contains(".")) {
            return path.split("\\.")[1];
        } else{
            return "";
        }
    }
}
