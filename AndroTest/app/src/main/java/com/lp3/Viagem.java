package com.lp3;

import java.util.Date;

/**
 * Created by Danilo on 26/05/2015.
 */
public class Viagem {

    private int id, qtdePessoas;
    private String titulo,status,cidadeOrigem,cidadeDestino;
    private double custoOrcado,custoReal;
    private Date dataPartida,dataChegada;


    public void setId(int id) {
        this.id = id;
    }

    public void setQtdePessoas(int quantidade_pessoas) {
        this.qtdePessoas = quantidade_pessoas;
    }

    public void setTitulo(String titulo_viagem) {
        this.titulo = titulo_viagem;
    }

    public void setCidadeOrigem(String cidade_origem) {
        this.cidadeOrigem = cidade_origem;
    }

    public void setCidadeDestino(String cidade_destino) {
        this.cidadeDestino = cidade_destino;
    }

    public void setCustoOrcado(double custo_orcado) {
        this.custoOrcado = custo_orcado;
    }

    public void setCustoReal(double custo_real) {
        this.custoReal = custo_real;
    }

    public void setDataPartida(Date data_partida) {
        this.dataPartida = data_partida;
    }

    public void setDataChegada(Date data_chegada) {
        this.dataChegada = data_chegada;
    }


    public int getId() {
        return id;
    }

    public int getQtdePessoas() {
        return qtdePessoas;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCidadeOrigem() {
        return cidadeOrigem;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

    public double getCustoOrcado() {
        return custoOrcado;
    }

    public double getCustoReal() {
        return custoReal;
    }

    public Date getDataPartida() {
        return dataPartida;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
