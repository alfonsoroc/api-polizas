package com.polizasservice.polizasservice.configuracion;

import org.json.JSONObject;

import java.util.Base64;
import java.util.Date;

public class LifeToken {

    private int validaEntrada;
    private Boolean vidaToken;

    public Boolean validaVidaToken(String token) {
        Boolean life;
        String[] chunksToken = token.split("\\.");
        Base64.Decoder decoderToken = Base64.getUrlDecoder();
        String converToken = new String(decoderToken.decode(chunksToken[1]));
        JSONObject jsonCoverToken = new JSONObject(converToken);

        long expLong = jsonCoverToken.getLong("exp");
        Date expDate = new Date(expLong * 1000l);
        Date currentDate = new Date();

        life = expDate.after(currentDate);

        return life;

    }


    public String obtenerToken(String tokenResponse) {
        JSONObject json = new JSONObject(tokenResponse);
        tokenResponse = json.getJSONObject("data").getString("token");
        return tokenResponse;
    }

}
