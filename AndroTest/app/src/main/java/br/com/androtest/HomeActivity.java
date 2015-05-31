package br.com.androtest;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lp3.Usuario;
import com.lp3.Viagem;

import java.util.ArrayList;
import java.util.Date;

import br.com.androtest.util.AdapterListView;


public class HomeActivity extends Activity {

    private ListView mainListView;
    private ArrayAdapter<Viagem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Usuario usuario=getIntent().getExtras().getParcelable("usuarioParcelable");

        ImageButton addTarefa=(ImageButton)findViewById(R.id.buttonAddTarefa);

        if (usuario.getCargo().equals("Solicitante")==false)
            addTarefa.setVisibility(View.INVISIBLE);

        // teste
        TextView nomeUsuario= new TextView(this);
        nomeUsuario=(TextView)findViewById(R.id.nomeUsuario);
        nomeUsuario.setText(usuario.getNome());
        System.out.print(usuario.getNome());

        TextView cargoUsuario= new TextView(this);
        cargoUsuario=(TextView)findViewById(R.id.cargoUsuario);
        cargoUsuario.setText(usuario.getCargo());

        //obter lista de viagens do usuário futuramente pelo webservice
        Viagem viagem1=new Viagem();
        Viagem viagem2=new Viagem();

        Date data=new Date();
        viagem1.setTituloViagem("Festa");
        viagem1.setDataPartida(data);
        viagem1.setCidadeDestino("Bom Sucesso");
        viagem2.setTituloViagem("Excursão");
        viagem2.setDataPartida(data);
        viagem2.setCidadeDestino("Belo Horizonte");

        ArrayList<Viagem> listaViagens=new ArrayList<Viagem>();
        listaViagens.add(viagem1);
        listaViagens.add(viagem2);

        //Popula ListView

        ListAdapter adapter = new AdapterListView(this,listaViagens);

        mainListView= (ListView) findViewById(R.id.viagensListView);
        mainListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
