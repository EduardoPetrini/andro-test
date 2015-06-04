package com.lp3;

import android.os.Parcel;
import android.os.Parcelable;

public class Viagem implements Parcelable {

    private int id, qtdePessoas;
    private String titulo,status,cidadeOrigem,cidadeDestino,dataPartida,dataChegada,horaPartida,horaChegada;
    private double custoOrcado,custoReal;

    public static final Parcelable.Creator<Viagem>CREATOR=new Parcelable.Creator<Viagem>(){

        public Viagem createFromParcel(Parcel in){
            return new Viagem(in);
        }

        public Viagem[] newArray(int size) {
            return new Viagem[size];
        }
    };
    
    private Viagem(Parcel in) {
        readFromParcel(in);
    }

    public Viagem(){}

    private void readFromParcel(Parcel in) {
        id=in.readInt();
        qtdePessoas=in.readInt();
        titulo=in.readString();
        status=in.readString();
        cidadeOrigem=in.readString();
        cidadeDestino=in.readString();
        dataChegada=in.readString();
        dataPartida=in.readString();
        custoOrcado=in.readDouble();
        custoReal=in.readDouble();
        horaPartida=in.readString();
        horaChegada=in.readString();
        in.readString();

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(qtdePessoas);
        dest.writeString(titulo);
        dest.writeString(status);
        dest.writeString(cidadeOrigem);
        dest.writeString(cidadeDestino);
        dest.writeString(dataChegada);
        dest.writeString(dataPartida);
        dest.writeDouble(custoOrcado);
        dest.writeDouble(custoReal);
        dest.writeString(horaPartida);
        dest.writeString(horaChegada);

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQtdePessoas(int qtdePessoas) {
        this.qtdePessoas = qtdePessoas;
    }

    public void setTitulo(String tituloViagem) {
        this.titulo = tituloViagem;
    }

    public void setCidadeOrigem(String cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public void setCidadeDestino(String cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public void setCustoOrcado(double custoOrcado) {
        this.custoOrcado = custoOrcado;
    }

    public void setCustoReal(double custoReal) {
        this.custoReal = custoReal;
    }

    public void setDataPartida(String dataPartida) {
        this.dataPartida = dataPartida;
    }

    public void setDataChegada(String dataChegada) {
        this.dataChegada = dataChegada;
    }

    public void setHoraPartida (String horaPartida){
        this.horaPartida=horaPartida;
    }

    public void setHoraChegada (String horaChegada){
        this.horaChegada=horaChegada;
    }

    public int getId() {
        return this.id;
    }

    public int getQtdePessoas() {
        return this.qtdePessoas;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getCidadeOrigem() {
        return this.cidadeOrigem;
    }

    public String getCidadeDestino() {
        return this.cidadeDestino;
    }

    public double getCustoOrcado() {
        return this.custoOrcado;
    }

    public double getCustoReal() {
        return this.custoReal;
    }

    public String getDataPartida() {
        return this.dataPartida;
    }

    public String getDataChegada() {
        return this.dataChegada;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHoraPartida() {
        return this.horaPartida;
    }

    public String getHoraChegada() {
        return this.horaChegada;
    }


    public void print(){
        System.out.println(titulo);
        System.out.println(status);
        System.out.println(id);
        System.out.println(dataPartida);
        System.out.println(dataChegada);
        System.out.println(cidadeOrigem);
        System.out.println(cidadeDestino);
        System.out.println(qtdePessoas);
        System.out.println(custoOrcado);
        System.out.println(custoReal);
        System.out.println(horaPartida);
        System.out.println(horaChegada);
    }
}
