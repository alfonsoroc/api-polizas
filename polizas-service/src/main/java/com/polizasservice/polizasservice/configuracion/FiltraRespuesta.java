package com.polizasservice.polizasservice.configuracion;

import org.hibernate.AssertionFailure;
import org.springframework.web.util.HtmlUtils;

public class FiltraRespuesta {

    private FiltraRespuesta(){
        throw new AssertionError("No puede instanciar la clase");
    }
    public static String LimpiarCode(String input) {
        if (input == null) {
            return null;
        }
        return HtmlUtils.htmlEscape(input);
    }
}
