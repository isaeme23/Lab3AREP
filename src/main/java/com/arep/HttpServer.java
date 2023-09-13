package com.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Clase que funciona como servidor HTTP
 * @author Isabella Manrique
 * @version 13-9-2023
 */
public class HttpServer {

    /**
     * Metodo principal de la clase
     * @param args Arreglo de argumentos
     * @throws IOException Excepcion de entrada/salida
     */
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }


            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine = null;



            boolean firstLine = true;
            String uriString = "";



            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    // POST /hellopost?name=John HTTP/1.1
                    uriString = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
            }
            System.out.println("URI: " + uriString);

            ComponentLoader.cargarComponentes();
            if (uriString.startsWith("/image") || uriString.startsWith("/file")){
                System.out.println("Esta cosa es: "+uriString.split("[?]")[0]);
                System.out.println("Estamos buscando "+uriString.split("=")[1]);
                ComponentLoader.ejecutar(uriString.split("[?]")[0], uriString.split("=")[1], clientSocket);
            } else if (uriString.contains("?")) {
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + ComponentLoader.ejecutar(uriString.split("[?]")[0], uriString);
                out.println(outputLine);

                out.close();
                in.close();
                clientSocket.close();
            } else{
                outputLine = getIndexResponse();
                out.println(outputLine);

                out.close();
                in.close();
                clientSocket.close();
            }
            in.close();
            clientSocket.close();

        }
        serverSocket.close();
    }



    public static String getHello(String uri) {
        String response = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{ \"msg\": \"Hello Pedro\" }";
        return response;
    }



    public static String getIndexResponse() {
        String response = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Form Example</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Form with GET</h1>\n"
                + "        <form action=\"/hello\">\n"
                + "            <label for=\"name\">Name:</label><br>\n"
                + "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n"
                + "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                + "        </form> \n"
                + "        <div id=\"getrespmsg\"></div>\n"
                + "\n"
                + "        <script>\n"
                + "            function loadGetMsg() {\n"
                + "                let nameVar = document.getElementById(\"name\").value;\n"
                + "                const xhttp = new XMLHttpRequest();\n"
                + "                xhttp.onload = function() {\n"
                + "                    document.getElementById(\"getrespmsg\").innerHTML =\n"
                + "                    this.responseText;\n"
                + "                }\n"
                + "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n"
                + "                xhttp.send();\n"
                + "            }\n"
                + "        </script>\n"
                + "\n"
                + "        <h1>Form with POST</h1>\n"
                + "        <form action=\"/hellopost\">\n"
                + "            <label for=\"postname\">Name:</label><br>\n"
                + "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n"
                + "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n"
                + "        </form>\n"
                + "        \n"
                + "        <div id=\"postrespmsg\"></div>\n"
                + "        \n"
                + "        <script>\n"
                + "            function loadPostMsg(name){\n"
                + "                let url = \"/hellopost?name=\" + name.value;\n"
                + "\n"
                + "                fetch (url, {method: 'POST'})\n"
                + "                    .then(x => x.text())\n"
                + "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n"
                + "            }\n"
                + "        </script>\n"
                + "    </body>\n"
                + "</html>";
        return response;



    }
}
