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

import br.com.androtest.util.AdapterListView;
import br.com.androtest.util.AndroidUtils;
import com.lp3.Atividade;
import br.com.androtest.util.RestUrls;


public class HomeActivity extends Activity {

    private ListView mainListView;
    private ArrayAdapter<Atividade> adapter;
    Usuario usuario;
    Viagem viagemV;
    ArrayList<Atividade> listaAtividades = new ArrayList<Atividade>();
    private boolean responseServe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //testar se temgrupo usuario
        usuario = getIntent().getExtras().getParcelable("usuarioParcelable");
        usuario.print();

        int i=9;
        while(i !=1){
            i=0;
        };

        ImageButton addTarefa=(ImageButton)findViewById(R.id.buttonAddTarefa);

        if (!usuario.grupoUsuario.idBpms.equalsIgnoreCase("3")) {
            addTarefa.setVisibility(View.INVISIBLE);
        }

        TextView nomeUsuario;//= new TextView(this);
        nomeUsuario=(TextView)findViewById(R.id.nomeUsuario);
        nomeUsuario.setText(usuario.getNome());
        System.out.print(usuario.getNome());

        TextView cargoUsuario;//= new TextView(this);
        cargoUsuario=(TextView)findViewById(R.id.cargoUsuario);
        cargoUsuario.setText(usuario.grupoUsuario.getNome());

        //obter lista de viagens do usuário futuramente pelo webservice
        //pegar o id do usuário e passar para o web service pedindo como retorno a lista de atividades

        //solicitaAtividades(usuario);

        //Popula ListView
        //com o web service ele já preenche a lista de atividades dentro do metódo sendToServer


        //Chama o webservice para solicitar as atividades, espera pela resposta
        solicitaAtividades();
        while(!responseServe);
        responseServe = false;

        final ListAdapter adapter = new AdapterListView(this,listaAtividades);

        mainListView= (ListView) findViewById(R.id.atividadesListView);
        mainListView.setAdapter(adapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Atividade atividade =(Atividade)adapter.getItem(position);
                Object item =mainListView.getItemAtPosition(position);
                System.out.println("Atividade titulo: "+atividade.getTitulo()+" Nome: "+atividade.getParametros().getEntityNome()
                        +"ID: "+atividade.getParametros().getEntityId());

                //Chamo o web service passando com.lp3.viagem.id
                JSONObject dataObject = new JSONObject();
                try {
                    dataObject.put("nome", atividade.getTitulo());
                    dataObject.put("parametro", atividade.getParametros().getEntityId());
                    System.out.println(dataObject);
                    sendToServerViagem(dataObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void solicitaAtividades(){

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
                                JSONObject objAtividade;
                                //ArrayList<Atividade> arrayAtividade;
                                for(int i=0; i<size; i++){
                                    atividade = new Atividade();
                                    atividade.setTitulo((arrayAtividades.getJSONObject(i).getString("nome")));
                                    objAtividade = arrayAtividades.getJSONObject(i).getJSONArray("parametros").getJSONObject(0);
                                    atividade.setTitulo(objAtividade.getString("nome"));
                                    atividade.setParametros(objAtividade.getString("entityName"),objAtividade.getString("entityID"));
                                    /*parametroApi = new ParametroApi();
                                    parametroApi.setIdBpms(par.getString("idBpms"));
                                    parametroApi.setId(par.getInt("id"));
                                    parametroApi.setNome(par.getString("nome"));
                                    parametroApi.setValor(par.getString("valor"));
                                    arrayPar = new ArrayList<>();
                                    arrayPar.add(parametroApi);
                                    atividade.setParametros(arrayPar);*/
                                  //arrayAtividade.add(atividade);
                                    listaAtividades.add(atividade);
                                }
                                responseServe = true;
                            }else{
                                AndroidUtils.alertUser("Erro ao obter lista de atividades: " + response.getString("erro"), currentActivity);
                                responseServe = true;
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            responseServe = true;
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub, print error message
                        System.out.println("Erro ao obter lista de atividades: "+error.getMessage());
                        AndroidUtils.alertUser("Erro ao obter lista de atividades: " + error.getMessage(), currentActivity);
                        responseServe = true;
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
                                goToUpDateViagem();
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

    public void goToUpDateViagem(){

        Intent intent= new Intent(this,ViagemActivity.class);
        intent.putExtra("viagemParcelable",viagemV);
        startActivity(intent);
    }
}
