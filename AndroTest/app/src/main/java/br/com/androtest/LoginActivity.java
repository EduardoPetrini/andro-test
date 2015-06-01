package br.com.androtest;

import br.com.androtest.util.SystemUiHider;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lp3.Usuario;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

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

    }

    public void cadastrar(View view){

        Intent intent= new Intent(this, CadastroActivity.class);

        startActivity(intent);

    }

    public void entrar(View view){
        Usuario usuario= new Usuario();

        EditText editTextusuario=(EditText) findViewById(R.id.usuario_email);
        EditText editTextpassword=(EditText) findViewById(R.id.usuario_password);

         if(validateFields(editTextusuario,editTextpassword)) {
            try {
                JSONObject dataObject = new JSONObject("{\"nome\":\"" + editTextusuario.getText() + "\"," +
                        "\"senha\":\"" + editTextpassword.getText() + "\"}");

                sendDataToServer(dataObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //ALTERARRRR   receber resposta do Web Service e passar o usuário para a HomeActivity
        usuario.setNome(editTextusuario.getText().toString());
        usuario.setSenha(editTextpassword.getText().toString());
        usuario.setCargo("Solicitante");

        Intent intent= new Intent(this,HomeActivity.class);
        intent.putExtra("usuarioParcelable",usuario);
        startActivity(intent);

    }

    private boolean validateFields(EditText usuario, EditText passward) {

        boolean success = true;

        if(usuario.getText().toString().trim().equals("")){
            success = false;
            usuario.setError("É necessário inserir um nome");
        }
        if(passward.getText().toString().trim().equals("")){
            success = false;
            passward.setError("É necessário inserir um email");
        }
        return success;
    }

    public void sendDataToServer(JSONObject dataObject){
        String url = "http://jsonplaceholder.typicode.com/posts";
        System.out.println("Enviando objeto para  "+url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (url, dataObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        mTxtDisplay.setText("Response: " + response.toString());
//                        Get response and set
                        System.out.println(response);
                        System.out.println("Cadastro enviado com sucesso!");
                        alertUser("Cadastro enviado com sucesso!");
                        goToHomeScrem();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub, print error message
                        System.out.println("Erro ao logaenviar o cadastro: "+error.getMessage());
                        alertUser("Erro ao realizar login: "+error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
        requestQueue.start();

    }

    private void alertUser(String s) {
        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
        TextView myMsg = new TextView(this);
        myMsg.setText(s);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        popupBuilder.setView(myMsg);
        popupBuilder.show();

    }

    public void goToHomeScrem(){
        this.finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
