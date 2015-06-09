package br.com.androtest.util;

import com.lp3.Viagem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Danilo on 09/06/2015.
 */
public class InstanciaFactory {

    public static InstanciaObjeto getInstancia(String entityName){
        //http://pt.wikipedia.org/wiki/Factory_Method
        //http://www.robsonsoares.com/?p=149
        //http://alvinalexander.com/java/java-factory-pattern-example
        if(entityName.equalsIgnoreCase("viagem")) {
            return null;//return new Viagem(); so pra não dar errado return null
        }
        else
            return null;
    }
}
