package br.com.androtest.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lp3.Viagem;

import java.util.ArrayList;
import java.util.List;

import br.com.androtest.R;

public class AdapterListView extends BaseAdapter{

    private ArrayList<Viagem> listaViagens;
    private Context context;
    private int numViagens=0;

    public AdapterListView(Context context, ArrayList<Viagem> viagems){
        //intens do list view
        this.listaViagens=viagems;
        this.context=context;
        this.numViagens=viagems.size();
    }

    @Override
    public int getCount() {
        return numViagens;
    }

    @Override
    public Viagem getItem(int position) {
        return listaViagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override//atualizador da lista
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the current list item
        final  Viagem viagem =listaViagens.get(position);
        // Get the layout for the list item
        final RelativeLayout activity_item_lista=(RelativeLayout)LayoutInflater.from(context).inflate(R.layout.activity_item_lista,parent,false);

        // Set the text label as defined in our list item
        TextView txtTitulo = (TextView) activity_item_lista.findViewById(R.id.textViewTitulo);
        txtTitulo.setText(viagem.getTituloViagem());
        TextView txtData = (TextView) activity_item_lista.findViewById(R.id.textViewData);
        txtData.setText(viagem.getDataPartida().toString());

        return activity_item_lista;

    }

    public class viagensSuporte{
        TextView titulo, data,notificacao,status;
    }
}
