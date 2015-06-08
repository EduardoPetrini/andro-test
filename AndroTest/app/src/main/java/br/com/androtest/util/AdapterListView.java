package br.com.androtest.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lp3.AtividadeApi;
import com.lp3.Viagem;

import java.util.ArrayList;

import br.com.androtest.R;

public class AdapterListView extends BaseAdapter{

    private ArrayList<AtividadeApi> listaAtividades;
    private Context context;
    private int numAtividades=0;

    public AdapterListView(Context context, ArrayList<AtividadeApi> listaAtividades){
        //intens do list view
        this.listaAtividades=listaAtividades;
        this.context=context;
        this.numAtividades=listaAtividades.size();
    }

    @Override
    public int getCount() {
        return numAtividades;
    }

    @Override
    public AtividadeApi getItem(int position) {
        return listaAtividades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override//atualizador da lista
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the current list item
        final  AtividadeApi atividade =listaAtividades.get(position);
        // Get the layout for the list item
        final RelativeLayout activity_item_lista=(RelativeLayout)LayoutInflater.from(context).inflate(R.layout.activity_item_lista,parent,false);
        // Set the text label as defined in our list item
        TextView txtTitulo = (TextView) activity_item_lista.findViewById(R.id.textViewTitulo);

        txtTitulo.setText(atividade.getNome());
        TextView txtData = (TextView) activity_item_lista.findViewById(R.id.textViewTarefa);
        txtData.setText("Solicitada");

        return activity_item_lista;
    }
}
