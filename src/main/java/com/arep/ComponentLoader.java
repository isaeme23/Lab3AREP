package com.arep;

import com.arep.annotation.Componente;
import com.arep.annotation.GetMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ComponentLoader {

    private static Map<String, Method> servicios = new HashMap<>();

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        cargarComponentes(args);
        System.out.println("EN EJECUCION: "+ ejecutar("/hello", "?name=Pedro&secondname=Perez"));
    }

    public static String ejecutar(String path, String param) throws InvocationTargetException, IllegalAccessException {
        return servicios.get(path).invoke(null, (Object) param) + "";
    }
    public static void cargarComponentes(String[] args) throws ClassNotFoundException {
        for (String a:args){
            Class c = Class.forName(a);
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
