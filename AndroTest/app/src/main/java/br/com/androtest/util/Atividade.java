package br.com.androtest.util;

/**
 * Created by Danilo on 02/06/2015.
 */
public class Atividade {
    Parametros parametros=new Parametros();
    public String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Parametros getParametros() {
        return parametros;
    }

    public void setParametros(String nome, String id) {
        parametros.setEntityNome(nome);
        parametros.setEntityId(id);
    }
}
