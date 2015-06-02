package br.com.androtest;

import br.com.androtest.util.AndroidUtils;
import br.com.androtest.util.RestUrls;
import br.com.androtest.util.SystemUiHider;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.app.ActionBar;
import android.app.Activity;
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

    private Usuario usuario;

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
        //Intent intent= new Intent(this, CadastroActivity.class);

        //pega as informacoes da tela
        System.out.println("Logando no sistema...");
        EditText emailField = (EditText)findViewById(R.id.usuario_email);
        EditText senhaField = (EditText)findViewById(R.id.usuario_password);

        if(validateFields(emailField,senhaField)) {

            JSONObject dataObject = new JSONObject();
            try {
                dataObject.put("email", emailField.getText().toString());
                dataObject.put("senha", senhaField.getText().toString());
                System.out.println(dataObject);
                sendLoginToServer(dataObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean validateFields(EditText emailField, EditText senhaField){
        boolean success = true;
        if(emailField.getText().toString().trim().equals("")){
            success = false;
            emailField.setError("É necessário inserir um email");
        }
        if(senhaField.getText().toString().trim().equals("")){
            success = false;
            senhaField.setError("É necessário inserir uma senha");
        }

        return success;
    }

    public void sendLoginToServer(JSONObject dataObject){
        String url = RestUrls.host+RestUrls.login;
        final Activity currentActivity = this;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (url, dataObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response);

                        try{
                            if(response.isNull("erro")){
                                JSONObject userResponse = response.getJSONObject("usuario");
                                usuario = new Usuario();
                                usuario.setNome(userResponse.getString("nome"));
                                usuario.setId(userResponse.getInt("id"));
                                usuario.setEmail(userResponse.getString("email"));
                                usuario.setCargo(userResponse.getString("cargo"));

                                goToHome();

                            }else{
                                AndroidUtils.alertUser("Erro ao logar: "+response.getString("erro"), currentActivity);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub, print error message
                        System.out.println("Erro ao logar: "+error.getMessage());
                        AndroidUtils.alertUser("Erro ao logar: "+error.getMessage(), currentActivity);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
        requestQueue.start();
    }

    private void goToHome(){
        Intent intent= new Intent(this,HomeActivity.class);
        intent.putExtra("usuarioParcelable", usuario);
        startActivity(intent);
    }
}