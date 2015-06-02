package com.lp3;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable{
    public String nome;
    private String email;
    private String senha;
    private String cargo;


    public static final Parcelable.Creator<Usuario>CREATOR=new Parcelable.Creator<Usuario>(){

        public Usuario createFromParcel(Parcel in){
            return new Usuario(in);
        }

        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    private Usuario(Parcel in) {
        readFromParel(in);
    }

    private void readFromParel(Parcel in) {
        nome=in.readString();
        cargo=in.readString();
        email=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(cargo);
        dest.writeString(email);
        dest.writeString(senha);
    }

    public void setNome(String nome){
        this.nome=nome;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setSenha(String senha){
        this.senha=senha;
    }

    public void setCargo(String cargo){
        this.cargo=cargo;
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }

    public String getSenha(){
        return this.senha;
    }

    public String getCargo(){
        return this.cargo;
    }

}
