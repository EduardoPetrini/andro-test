package br.com.androtest;

import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lp3.Usuario;
import com.lp3.Viagem;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.androtest.util.AndroidUtils;
import br.com.androtest.util.RestUrls;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class ViagemActivity extends Activity {

    private Usuario usuario;
    private Viagem viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_viagem);

        usuario = getIntent().getExtras().getParcelable("usuarioParcelable");
        viagem = getIntent().getExtras().getParcelable("viagemParcelable");

        Spinner spinner= (Spinner)findViewById(R.id.spinnerMotorista);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.motoristas,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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

        if(viagem!=null){
            preencherTela(viagem);
            Button bottunCriar=(Button)findViewById(R.id.buttonCriar);
            bottunCriar.setVisibility(View.INVISIBLE);
        }

        System.out.println("Usuário não é null");
        LinearLayout layoutBAutorizador=(LinearLayout)findViewById(R.id.layoutBotoesAutorizadr);
        LinearLayout layoutBMotorista=(LinearLayout)findViewById(R.id.layoutBotoesMotorista);
        Button BotaoSolicitante=(Button)findViewById(R.id.buttonCriar);
        Button BotaoTransportador=(Button)findViewById(R.id.buttonEnviarTransportador);

        layoutBAutorizador.setVisibility(View.INVISIBLE);
        layoutBMotorista.setVisibility(View.INVISIBLE);
        BotaoSolicitante.setVisibility(View.INVISIBLE);
        BotaoTransportador.setVisibility(View.INVISIBLE);

        switch (usuario.grupoUsuario.idBpms.toString()){
            case "1":
                layoutBAutorizador.setVisibility(View.VISIBLE);
                break;
            case "2":
                BotaoSolicitante.setVisibility(View.VISIBLE);
                break;
            case "3":
                layoutBMotorista.setVisibility(View.VISIBLE);
                break;
            case "4":
                BotaoTransportador.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void preencherTela(Viagem viagem) {
        EditText titulo = (EditText)findViewById(R.id.inputTitulo);
        titulo.setText(viagem.getTitulo());
        System.out.println("Titulo: " + viagem.getTitulo());
        EditText dataPartida = (EditText)findViewById(R.id.dataPartida);
        dataPartida.setText(viagem.getDataPartida());
        EditText horaPartida = (EditText)findViewById(R.id.horaPartida);
        horaPartida.setText("12:00");// Inserir campo hora
        EditText dataChegada = (EditText)findViewById(R.id.dataChegada);
        dataChegada.setText(viagem.getDataChegada());
        EditText horaChegada = (EditText)findViewById(R.id.horaChegada);
        horaChegada.setText("12:00");
        EditText origem = (EditText)findViewById(R.id.origem);
        origem.setText(viagem.getCidadeOrigem());
        EditText destino = (EditText)findViewById(R.id.destino);
        destino.setText(viagem.getCidadeDestino());
        EditText qtdePessoas = (EditText)findViewById(R.id.qtdePessoas);
        qtdePessoas.setText(Integer.toString(viagem.getQtdePessoas()));
        EditText custoOrcado = (EditText)findViewById(R.id.custoOrcado);
        custoOrcado.setText(Double.toString(viagem.getCustoOrcado()));
        EditText custoReal = (EditText)findViewById(R.id.custoReal);
        custoReal.setText(Double.toString(viagem.getCustoReal()));

    }

    public void criar(View view){
        EditText titulo = (EditText)findViewById(R.id.inputTitulo);
        EditText dataPartida = (EditText)findViewById(R.id.dataPartida);
        EditText horaPartida = (EditText)findViewById(R.id.horaPartida);
        EditText dataChegada = (EditText)findViewById(R.id.dataChegada);
        EditText horaChegada = (EditText)findViewById(R.id.horaChegada);
        EditText origem = (EditText)findViewById(R.id.origem);
        EditText destino = (EditText)findViewById(R.id.destino);
        EditText qtdePessoas = (EditText)findViewById(R.id.qtdePessoas);

        String strDataPartida = dataPartida.getText().toString()+" "+horaPartida.getText().toString();
        String strDataChegada = dataPartida.getText().toString()+" "+horaPartida.getText().toString();
        try {
            JSONObject dataObject = new JSONObject();
            dataObject.put("titulo", titulo.getText().toString());
            dataObject.put("dataPartida", strDataPartida);
            dataObject.put("dataChegada", strDataChegada);
            dataObject.put("origem", origem.getText().toString());
            dataObject.put("destino", destino.getText().toString());
            dataObject.put("qtdePessoas", qtdePessoas.getText().toString());
            dataObject.put("usuarioId", usuario.getId());

            sendViagemToServer(dataObject);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void sendViagemToServer(JSONObject dataObject){
        String url = RestUrls.host+RestUrls.viagemCadastrar;

        final Activity currentActivity = this;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (url, dataObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response);

                        try{
                            String responseStatus = response.get("status").toString();
                            if(responseStatus.equalsIgnoreCase("ok")){
                                AndroidUtils.alertTripUser("Viagem criada com sucesso!", currentActivity);
                            }else{
                                AndroidUtils.alertTripUser("Erro ao criar a viagem! "+  response.get("erro").toString(), currentActivity);
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub, print error message
                        System.out.println("Erro ao enviar o cadastro: "+error.getMessage());
                        AndroidUtils.alertUser("Erro ao enviar o cadastro: " + error.getMessage(), currentActivity);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
        requestQueue.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viagem, menu);
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
