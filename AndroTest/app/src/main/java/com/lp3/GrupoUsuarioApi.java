/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp3;


import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author Eduardo
 */
public class GrupoUsuarioApi implements Parcelable{
    private String id;
    public String idBpms;
    public String idAPP;
    private String nome;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBpms() {
        return idBpms;
    }

    public void setIdBpms(String idBpms) {
        this.idBpms = idBpms;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdAPP() {
        return idAPP;
    }

    public void setIdAPP(String idAPP) {
        this.idAPP = idAPP;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        id = in.readString();
        idBpms = in.readString();
        nome = in.readString();
        idAPP = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idBpms);
        dest.writeString(nome);
        dest.writeString(idAPP);
    }
}
