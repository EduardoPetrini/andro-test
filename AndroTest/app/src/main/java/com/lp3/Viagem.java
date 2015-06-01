package com.lp3;

import java.util.Date;

/**
 * Created by Danilo on 26/05/2015.
 */
public class Viagem {

    private int id, quantidadePessoas;
    private String tituloViagem,cidadeOrigem,cidadeDestino,dataPartida,dataChegada;
    private double custoOrcado,custoReal;


    public void setId(int id) {
        this.id = id;
    }

    public void setQuantidadePessoas(int quantidade_pessoas) {
        this.quantidadePessoas = quantidade_pessoas;
    }

    public void setTituloViagem(String titulo_viagem) {
        this.tituloViagem = titulo_viagem;
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

    public void setDataPartida(String data_partida) {
        this.dataPartida = data_partida;
    }

    public void setDataChegada(String data_chegada) {
        this.dataChegada = data_chegada;
    }


    public int getId() {
        return id;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public String getTituloViagem() {
        return tituloViagem;
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

    public String getDataPartida() {
        return dataPartida;
    }

    public String getDataChegada() {
        return dataChegada;
    }

}
