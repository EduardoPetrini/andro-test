/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp3;

import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */

public class AtividadeApi {
    private int id;
    private String nome;
    private String idBpms;
    private GrupoUsuarioApi grupoUsuario;
    private ArrayList<ParametroApi> parametros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdBpms() {
        return idBpms;
    }

    public void setIdBpms(String idBpms) {
        this.idBpms = idBpms;
    }

    public ArrayList<ParametroApi> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<ParametroApi> parametros) {
        this.parametros = parametros;
    }

    public GrupoUsuarioApi getGrupoUsuario() {
        return grupoUsuario;
    }

    public void setGrupoUsuario(GrupoUsuarioApi grupoUsuario) {
        this.grupoUsuario = grupoUsuario;
    }
}
