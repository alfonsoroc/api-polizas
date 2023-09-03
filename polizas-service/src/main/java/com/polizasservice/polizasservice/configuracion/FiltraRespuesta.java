package com.polizasservice.polizasservice.configuracion;

import org.springframework.web.util.HtmlUtils;

public class FiltraRespuesta {

    private FiltraRespuesta(){
        throw new AssertionError("No puede instanciar la clase");
    }
    public static String limpiarCode(String input) {
        if (input == null) {
            return null;
        }
        return HtmlUtils.htmlEscape(input);
    }
}
