package com.lp3;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario extends UsuarioApi implements Parcelable {
    private int id;
    private int idApi;
    public String nome;
    private String email;
    private String senha;



    public static final Parcelable.Creator<Usuario>CREATOR=new Parcelable.Creator<Usuario>(){

        public Usuario createFromParcel(Parcel in){
            return new Usuario(in);
        }

        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    private Usuario(Parcel in) {
        readFromParcel(in);
    }

    public Usuario (){}

    private void readFromParcel(Parcel in) {
        nome=in.readString();
        email=in.readString();
        in.readString();
        id = in.readInt();
        super.grupoUsuario = in.readParcelable(null);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeInt(id);
        dest.writeParcelable(super.grupoUsuario, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
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

    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }

    public String getSenha(){
        return this.senha;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void print(){
        System.out.println(this.nome);
        System.out.println(this.id);
        System.out.println(this.email);
        System.out.println(this.senha);
    }
}
