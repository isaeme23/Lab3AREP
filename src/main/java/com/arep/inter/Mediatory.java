package com.arep.inter;

import java.io.IOException;

public interface Mediatory {
    /**
     * Metodo de respuesta dependiendo del tipo de archivo
     * @throws IOException Excepcion de entrada/salida
     */
    void reply() throws IOException;

    /**
     * Metodo que nos indica el tipo de archivo
     * @return Extension del archivo
     */
    String typeFile();
}
