/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp3;

/**
 *
 * @author Eduardo
 */
public class UsuarioApi{
    private int id;
    private int idAplicacao;
    private String nome;
    public GrupoUsuarioApi grupoUsuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAplicacao() {
        return idAplicacao;
    }

    public void setIdAplicacao(int idAplicacao) {
        this.idAplicacao = idAplicacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GrupoUsuarioApi getGrupoUsuario() {
        return grupoUsuario;
    }

    public void setGrupoUsuario(GrupoUsuarioApi grupoUsuario) {
        this.grupoUsuario = grupoUsuario;
    }
}
