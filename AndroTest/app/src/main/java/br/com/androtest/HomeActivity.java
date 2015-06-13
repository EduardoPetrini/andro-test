package br.com.androtest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import com.lp3.Parametros;

import com.lp3.Usuario;
import com.lp3.Viagem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.androtest.util.AdapterListView;
import br.com.androtest.util.AndroidUtils;
import com.lp3.Atividade;

import br.com.androtest.util.InstanciaFactory;
import br.com.androtest.util.InstanciaObjeto;
import br.com.androtest.util.RestUrls;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class HomeActivity extends Activity {

    private ListView mainListView;
    private ArrayAdapter<Atividade> adapter;
    Usuario usuario;
    ArrayList<Atividade> listaAtividades = new ArrayList<Atividade>();
    private boolean responseServe = false;
    String entityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        usuario = getIntent().getExtras().getParcelable("usuarioParcelable");

        ImageButton addAtividade=(ImageButton)findViewById(R.id.buttonAddAtividade);

        if (!usuario.grupoUsuario.idBpms.equalsIgnoreCase("3")) {
            addAtividade.setVisibility(View.INVISIBLE);
        }

        TextView nomeUsuario;
        nomeUsuario=(TextView)findViewById(R.id.nomeUsuario);
        nomeUsuario.setText(usuario.getNome());
        System.out.print(usuario.getNome());

        TextView cargoUsuario;
        cargoUsuario=(TextView)findViewById(R.id.cargoUsuario);
        cargoUsuario.setText(usuario.grupoUsuario.getNome());

        solicitaAtividades();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/comfortaa-regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        SpannableString s = new SpannableString("");
        s.setSpan(new TypefaceSpan("fonts/comfortaa-bold.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ViewGroup actionBarLayout = (ViewGroup) this.getLayoutInflater().inflate(R.layout.activity_action_bar, null);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(s);
        actionBar.setIcon(R.mipmap.ic_van);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout,
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );

    }

    public void solicitaAtividades(){
        getDataFromServerUsuario();
    }

    public void getDataFromServerUsuario(){
        String url = RestUrls.host+RestUrls.getAtividades+usuario.getId();
        System.out.println("Url: "+url);
        final Activity currentActivity = this;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Na home...\n"+response);

                        try{
                            if(response.isNull("erro")|| usuario.getGrupoUsuario().toString()!="3"){
                                JSONArray arrayAtividades = response.getJSONArray("atividades");
                                Atividade atividade = null;
                                JSONObject objAtividade;
                                Parametros parametros;

                                for(int i = 0; i < arrayAtividades.length(); i++){
                                    atividade = new Atividade();
                                    objAtividade = arrayAtividades.getJSONObject(i);

                                    atividade.setNome(objAtividade.getString("nome"));

                                    parametros = new Parametros();
                                    parametros.setEntityId(objAtividade.getJSONObject("parametros").getString("entityId"));
                                    parametros.setEntityNome(objAtividade.getJSONObject("parametros").getString("entityNome"));
                                    atividade.setParametros(parametros);

                                    listaAtividades.add(atividade);
                                }

                                buildListView();

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

    public void getEntityFromServer(final Atividade atividade){

        entityName = getEntityNameFromAtividade(atividade);
        String string[]=entityName.split("\\.");
        entityName=string[string.length-1];
        entityName=entityName.toLowerCase();

        String url = RestUrls.host+entityName+"/"+atividade.getParametros().getEntityId();

        final Activity currentActivity = this;
        System.out.println("Url: "+url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Obj response JSON viagem: "+response);

                        try{
                            if(response.isNull("erro")){
                                JSONObject resposta = response.getJSONObject("Viagem");
                                //InstanciaObjeto objeto= InstanciaFactory.getInstancia(entityName);
                                //objeto.createObj(resposta,);

                                switch (entityName){
                                    case "viagem":{
                                        Viagem viagem=new Viagem().createObj(resposta);
                                        //viagem=viagem.createObj(resposta);
                                        viagem.print();
                                        goToUpDateViagem(viagem, atividade);
                                        break;
                                    }
                                    case "usuario":{
                                        break;
                                    }
                                    case "atividade":{

                                        break;
                                    }
                                    case "saude":{

                                        break;
                                    }
                                    default:{
                                        break;
                                    }
                                }

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
                        System.out.println("Erro ao obter atividades: " + error.getMessage());
                        AndroidUtils.alertUser("Erro ao obter atividades: "+error.getMessage(), currentActivity);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
        requestQueue.start();
    }

    private String getEntityNameFromAtividade(Atividade atividade) {
        return atividade.getParametros().getEntityNome();
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

    public void goToUpDateViagem(Viagem viagem, Atividade atividade){

        Intent intent= new Intent(this,ViagemActivity.class);
        intent.putExtra("viagemParcelable",viagem);
        intent.putExtra("usuarioParcelable",usuario);
        intent.putExtra("idAtividade", Integer.toString(atividade.getId()));
        intent.putExtra("idAtividade", Integer.toString(atividade.getId()));
        startActivity(intent);
    }

    public void buildListView(){
        final ListAdapter adapter = new AdapterListView(this,listaAtividades);

        mainListView= (ListView) findViewById(R.id.atividadesListView);
        mainListView.setAdapter(adapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Atividade atividade = (Atividade) adapter.getItem(position);
                System.out.println("Atividade nome: " + atividade.getNome() + " Nome: " + atividade.getParametros().getEntityNome()
                        + "ID: " + atividade.getParametros().getEntityId());

                getEntityFromServer(atividade);

            }
        });
    }

    public void addAtividade(View view){

        Intent intent= new Intent(this,ViagemActivity.class);
        intent.putExtra("usuarioParcelable",usuario);
        startActivity(intent);
    }
}
