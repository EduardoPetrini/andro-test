package br.com.androtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lp3.Usuario;
import com.lp3.Viagem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import br.com.androtest.util.AdapterListView;
import br.com.androtest.util.AndroidUtils;
import br.com.androtest.util.Atividade;
import br.com.androtest.util.RestUrls;


public class HomeActivity extends Activity {

    private ListView mainListView;
    private ArrayAdapter<Atividade> adapter;
    Usuario usuario;
    Viagem viagemV;
    ArrayList<Atividade> listaAtividades=new ArrayList<Atividade>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*usuario = getIntent().getExtras().getParcelable("usuarioParcelable");
        usuario.print();*/
        usuario=new Usuario();
        usuario.setCargo("Solicitante");
        usuario.setNome("Danilo");

        ImageButton addTarefa=(ImageButton)findViewById(R.id.buttonAddTarefa);

        if (usuario.getCargo().equalsIgnoreCase("Solicitante")==false)
            addTarefa.setVisibility(View.INVISIBLE);

        TextView nomeUsuario;//= new TextView(this);
        nomeUsuario=(TextView)findViewById(R.id.nomeUsuario);
        nomeUsuario.setText(usuario.getNome());
        System.out.print(usuario.getNome());

        TextView cargoUsuario;//= new TextView(this);
        cargoUsuario=(TextView)findViewById(R.id.cargoUsuario);
        cargoUsuario.setText(usuario.getCargo());

        //obter lista de viagens do usuário futuramente pelo webservice
        //pegar o id do usuário e passar para o web service pedindo como retorno a lista de atividades

        //solicitaAtividades(usuario);

        //Popula ListView
        //com o web service ele já preenche a lista de atividades dentro do metódo sendToServer
        Atividade atividade1=new Atividade();
        Atividade atividade2=new Atividade();
        Atividade atividade3=new Atividade();
        atividade1.setTitulo("Viagem BH");
        atividade2.setTitulo("Viagem RJ");
        atividade3.setTitulo("Viagem RS");
        atividade1.setParametros("com.lp3.viagem.01", "01");
        atividade2.setParametros("com.lp3.viagem.02", "02");
        atividade3.setParametros("com.lp3.viagem.03","03");

        listaAtividades.add(atividade1);
        listaAtividades.add(atividade2);
        listaAtividades.add(atividade3);

        final ListAdapter adapter = new AdapterListView(this,listaAtividades);

        mainListView= (ListView) findViewById(R.id.atividadesListView);
        mainListView.setAdapter(adapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Atividade atividade =(Atividade)adapter.getItem(position);
                Object item =mainListView.getItemAtPosition(position);
                System.out.println("Atividade titulo: "+atividade.getTitulo()+"Nome: "+atividade.getParametros().getEntityNome()
                        +"ID: "+atividade.getParametros().getEntityId());
                //coloquei pra testar
                viagemV=new Viagem();
                viagemV.setId(10);
                viagemV.setQtdePessoas(100);
                viagemV.setTitulo("Viagem BH");
                viagemV.setStatus("Partiu BH");
                viagemV.setCidadeOrigem("Lavras");
                viagemV.setCidadeDestino("BH");
                viagemV.setDataPartida("03/06/2015");
                viagemV.setDataChegada("27/06/2015");
                viagemV.setHoraPartida("12:12");
                viagemV.setHoraChegada("15:13");
                viagemV.setCustoOrcado(600);
                viagemV.setCustoReal(1600);
                goToAtividade();
                //Chamo o web service passando com.lp3.viagem.id
                /*JSONObject dataObject = new JSONObject();
                try {
                    dataObject.put("titulo", atividade.getTitulo());
                    dataObject.put("nome", atividade.getParametros().getEntityNome());
                    dataObject.put("nome", atividade.getParametros().getEntityId());
                    System.out.println(dataObject);
                    sendToServerViagem(dataObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });

    }

    public void solicitaAtividades(Usuario usuario){

        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put("id", (usuario.getId()));
            System.out.println(dataObject);
            sendToServerUsuario(dataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendToServerUsuario(JSONObject dataObject){
        String url = RestUrls.host+RestUrls.getUsuarioById;
        final Activity currentActivity = this;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (url, dataObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response);

                        try{
                            if(response.isNull("erro")){
                                JSONArray arrayAtividades = response.getJSONArray("listaAtividades");
                                //transformar objeto em lista de atividades
                                //http://www.leveluplunch.com/java/examples/convert-json-array-to-arraylist-of-objects-jackson/
                                //http://stackoverflow.com/questions/26814673/android-jsonarray-to-arraylist
                                int size=arrayAtividades.length();
                                Atividade atividade = null;
                                for(int i=0; i<size; i++){
                                    atividade.setTitulo(arrayAtividades.getJSONObject(i).getString("titulo"));
                                    atividade.setParametros(arrayAtividades.getJSONObject(i).getString("nome"),
                                            (arrayAtividades.getJSONObject(i).getString("id")));
                                    listaAtividades.add(atividade);
                                }
                            }else{
                                AndroidUtils.alertUser("Erro ao obter lista de atividades: " + response.getString("erro"), currentActivity);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub, print error message
                        System.out.println("Erro ao obter lista de atividades: "+error.getMessage());
                        AndroidUtils.alertUser("Erro ao obter lista de atividades: "+error.getMessage(), currentActivity);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
        requestQueue.start();
    }

    public void sendToServerViagem(JSONObject dataObject){
        String url = RestUrls.host+RestUrls.getViagemById;
        final Activity currentActivity = this;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (url, dataObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Obj response JSON viagem: "+response);

                        try{
                            if(response.isNull("erro")){
                                JSONObject viagem = response.getJSONObject("viagem");
                                viagemV.setId(Integer.parseInt(viagem.getString("id")));
                                viagemV.setQtdePessoas(Integer.parseInt(viagem.getString("qtdePessoas")));
                                viagemV.setTitulo(viagem.getString("titulo"));
                                viagemV.setStatus(viagem.getString("status"));
                                viagemV.setCidadeOrigem(viagem.getString("cidadeOrigen"));
                                viagemV.setCidadeDestino(viagem.getString("cidadeDestino"));
                                viagemV.setDataPartida(viagem.getString("dataPartida"));
                                viagemV.setDataChegada(viagem.getString("dataChegada"));
                                viagemV.setCustoOrcado(Double.parseDouble(viagem.getString("custoOrcado")));
                                viagemV.setCustoReal(Double.parseDouble(viagem.getString("custoReal")));
                                viagemV.setHoraPartida(viagem.getString("horaPartida"));
                                viagemV.setHoraChegada(viagem.getString("horaChegada"));
                                goToAtividade();
                            }else{
                                AndroidUtils.alertUser("Erro ao obter atividades: " + response.getString("erro"), currentActivity);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub, print error message
                        System.out.println("Erro ao obter atividades: "+error.getMessage());
                        AndroidUtils.alertUser("Erro ao obter atividades: "+error.getMessage(), currentActivity);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
        requestQueue.start();
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

    public void goToAtividade(){

        Intent intent= new Intent(this,ViagemActivity.class);
        intent.putExtra("viagemParcelable",viagemV);
        startActivity(intent);
    }
}
