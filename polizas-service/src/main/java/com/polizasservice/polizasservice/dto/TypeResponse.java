package com.polizasservice.polizasservice.dto;

public class TypeResponse {
    private TypeResponse(){}

    public static final short TYPE_OK = 0;
    public static final short TYPE_INFORMATIVE = 1;
    public static final short TYPE_CONDITIONAL = 2;
    public static final short TYPE_ERROR = 3;

    public static final short TYPE_RESTICTIVE= 5;
    public static final short TYPE_UNAUTHORISED = 11;
}
