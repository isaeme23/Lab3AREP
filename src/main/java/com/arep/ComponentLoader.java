package com.arep;

import com.arep.annotation.Componente;
import com.arep.annotation.GetMapping;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase para cargar las clases funcionales
 * @author Isabella Manrique
 * @version 13-9-2023
 */
public class ComponentLoader {

    // Atributos de la clase
    private static Map<String, Method> servicios = new HashMap<>();

    /**
     * Metodo para ejecutar el metodo por parametro
     * @param path nombre del metodo
     * @param param parametro del metodo
     * @return nombre del metodo
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static String ejecutar(String path, String param) throws InvocationTargetException, IllegalAccessException {
        return servicios.get(path).invoke(null, (Object) param) + "";
    }

    /**
     * Metodo para ejecutar el metodo por parametro
     * @param path nombre del metodo
     * @param param parametro del metodo
     * @param socket socket a conectar por el metodo
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */

    public static void ejecutar(String path, String param, Socket socket) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Esto es: "+servicios.get(path));
        servicios.get(path).invoke(null, (Object) param, (Object) socket);
    } // Cierre del metodo

    /**
     * Metodo principal de la clase que carga las clases que contienen la etiqueta de Componente y carga los metodos de dicha clase
     * @throws ClassNotFoundException
     */
    public static void cargarComponentes() throws ClassNotFoundException {
        File file = new File("target/classes/com/arep/");
        String[] files = file.list();
        for (String f: files){
            if (!f.equals("ComponentLoader.class") && f.contains(".class")){
                System.out.println("Nombre de la clase:" + f.split("\\.")[0]);
                Class c = Class.forName("com.arep."+f.split("\\.")[0]);
                if (c.isAnnotationPresent(Componente.class)){
                    Method[] metodos = c.getDeclaredMethods();
                    for (Method m:metodos){
                        if (m.isAnnotationPresent(GetMapping.class)){
                            // Extraccion del valor del parametro
                            String path = m.getAnnotation(GetMapping.class).value();
                            // Nombre del metodo
                            System.out.println("Nombre del metodo: " +m.getName());
                            // Crear la lista de tipos del metodo
                            servicios.put(path, m);
                            // Obtener el metodo
                        }
                    }
                }
            }

        }
    } // Cierre del metodo
} // Cierre de la clase
