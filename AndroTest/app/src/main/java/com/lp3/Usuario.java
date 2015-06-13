package com.lp3;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario extends UsuarioApi implements Parcelable {
    private int id;
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
        this.grupoUsuario = new GrupoUsuarioApi();
        this.grupoUsuario.setNome(in.readString());
        this.grupoUsuario.setId(in.readString());
        this.grupoUsuario.setIdBpms(in.readString());
        //this.grupoUsuario = in.readParcelable(ClassLoader.getSystemClassLoader());
        //super.grupoUsuario = in.readParcelable(ClassLoader.getSystemClassLoader());

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
        dest.writeString(this.grupoUsuario.getNome());
        dest.writeString(this.grupoUsuario.getId());
        dest.writeString(this.grupoUsuario.getIdBpms());
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
        System.out.println("Nome:"+this.nome);
        System.out.println("id:"+this.id);
        System.out.println("email:"+this.email);
        System.out.println("senha:"+this.senha);
        System.out.println("NomeGrupo:"+super.grupoUsuario.getNome());
        System.out.println("Nome Id:"+super.grupoUsuario.getId());
        System.out.println("NomeIdBpms:"+super.grupoUsuario.getIdBpms());
    }
}
