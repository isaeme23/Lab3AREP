package com.arep;

import com.arep.annotation.Componente;
import com.arep.annotation.GetMapping;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ComponentLoader {

    private static Map<String, Method> servicios = new HashMap<>();

    public static void principal(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        cargarComponentes();
        System.out.println("EN EJECUCION: "+ ejecutar("/hello", "?name=Pedro&secondname=Perez"));
    }

    public static String ejecutar(String path, String param) throws InvocationTargetException, IllegalAccessException {
        return servicios.get(path).invoke(null, (Object) param) + "";
    }

    public static void ejecutar(String path, String param, Socket socket) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Esto es: "+servicios.get(path));
        servicios.get(path).invoke(null, (Object) param, (Object) socket);
    }
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
    }
}
