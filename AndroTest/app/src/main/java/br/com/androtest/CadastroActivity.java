package br.com.androtest;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lp3.Usuario;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.androtest.util.RestUrls;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.lp3.Usuario;


public class CadastroActivity extends Activity {

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cadastro);

//        Default font
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

        Intent intent= getIntent();

        Spinner spinner= (Spinner)findViewById(R.id.spinnerCargo);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.cargo,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Selecione Seu Cargo");
        spinner.setAdapter(adapter);

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void submitData(View view){
        Usuario usuario=new Usuario();
        EditText nome = (EditText) findViewById(R.id.inputNome);
        usuario.setNome(nome.getText().toString());
        EditText email = (EditText) findViewById(R.id.inputEmail);
        usuario.setEmail(email.getText().toString());
        EditText senha = (EditText) findViewById(R.id.inputSenha);
        usuario.setSenha(senha.getText().toString());
        EditText confSenha = (EditText) findViewById(R.id.inputConfSenha);
        Spinner spinner= (Spinner)findViewById(R.id.spinnerCargo);
        usuario.setCargo(spinner.getSelectedItem().toString());
        //        "{\"type\":\"example\"}";

        if(validateFields(nome, email, spinner, senha, confSenha)) {
            try {

                JSONObject dataObject = new JSONObject();

                dataObject.put("nome", nome.getText());
                dataObject.put("email", email.getText());
                dataObject.put("cargo", spinner.getSelectedItem().toString());
                dataObject.put("senha", senha.getText());

                sendDataToServer(dataObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean validateFields(EditText nome, EditText email, Spinner spinner, EditText senha, EditText confSenha) {

        boolean success = true;

        if(nome.getText().toString().trim().equals("")){
            success = false;
            nome.setError("É necessário inserir um nome");
        }
        if(email.getText().toString().trim().equals("")){
            success = false;
            email.setError("É necessário inserir um email");
        }

        if(spinner.getSelectedItem().toString().trim().equals("Selecione seu cargo")){
            success = false;
            alertUser("Você deve selecionar um cargo!");
        }

        if(senha.getText().toString().trim().equals("")){
            success = false;
            senha.setError("É necessário inserir uma senha");
        }
        if(confSenha.getText().toString().trim().equals("")){
            success = false;
            confSenha.setError("É necessário inserir a confirmação da senha");
        }

        if(!confSenha.getText().toString().trim().equals(senha.getText().toString().trim())){
            success = false;
            confSenha.setText("");
            confSenha.setError("Senha não confere!");
        }

        return success;
    }

    public void sendDataToServer(JSONObject dataObject){
        String url = RestUrls.host+RestUrls.usuarioCadastrar;
        System.out.println("Enviando objeto para  " + url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (url, dataObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        alertUser("Cadastro enviado com sucesso!");
                        System.out.println(response);

                        try{
                            usuario = new Usuario();
                            JSONObject userResponse = response.getJSONObject("usuario");
                            usuario.setNome(userResponse.getString("nome"));
                            usuario.setEmail(userResponse.getString("email"));
                            usuario.setCargo(userResponse.getString("cargo"));

                            goToHome();

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                        System.out.println("Cadastro enviado com sucesso!");

                        goToLoginScreen();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub, print error message
                        System.out.println("Erro ao enviar o cadastro: "+error.getMessage());
                        alertUser("Erro ao enviar o cadastro: "+error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
        requestQueue.start();

    }

    public void goToLoginScreen(){
        this.finish();
    }

    private void alertUser(String s) {
        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
        TextView myMsg = new TextView(this);
        myMsg.setText(s);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        popupBuilder.setView(myMsg);
        popupBuilder.show();

    }

    private void goToHome(){
        Intent intent= new Intent(this,HomeActivity.class);
        intent.putExtra("usuarioParcelable", usuario);
        startActivity(intent);
    }
}
