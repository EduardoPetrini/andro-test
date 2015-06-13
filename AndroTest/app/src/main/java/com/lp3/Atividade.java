package com.lp3;

/**
 * Created by Danilo on 02/06/2015.
 */
public class Atividade {
    private int id;
    private Parametros parametros;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Parametros getParametros() {
        return parametros;
    }

    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
    }

    public int getId() {
        return id;
    }
}
