package br.com.androtest;

import br.com.androtest.util.SystemUiHider;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


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

        //final Button button_entrar=(Button) findViewById(R.id.butonLogin);
        //button_entrar.setOnClickListener(button_entrar());
    }

    public void cadastrar(View view){

        Intent intent= new Intent(this, CadastroActivity.class);

        startActivity(intent);

    }

    public void entrar(View view){
        //Renomear Home Activity para CadastroActivity
        //Intent intent= new Intent(this, CadastroActivity.class);

        //pega as informacoes da tela
        EditText editTextUser=(EditText) findViewById(R.id.user_email);
        String user=editTextUser.getText().toString();

        EditText editTextPassword=(EditText) findViewById(R.id.user_password);
        String password=editTextPassword.getText().toString();

        //Criar objeto JSON para passar para o web
        // JSONObject

        //HttpRequestTask httpRequestTask= new HttpRequestTask();
        //httpRequestTask.execute();

        //chama o web service para validar as info
        //URL url= new URL("http://www.android.com/");
        //HttpURLConnection;
        //inicia a interface Home do Usuario
        //startActivity(intent);

    }


    public class HttpRequestTask extends AsyncTask <Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //Conteúdo a ser enviado PREENCHER/trocar sei la, rsrsrsrs STRING COM DADOS COM O REST
           // http://developer.android.com/reference/java/net/URLConnection.html#setDoOutput(boolean)
            //http://developer.android.com/reference/java/net/URLConnection.html#setRequestProperty(java.lang.String, java.lang.String)
            //http://stackoverflow.com/questions/9767952/how-to-add-parameters-to-httpurlconnection-using-post
            //http://www.arquivodecodigos.net/dicas/java-usando-um-objeto-da-classe-httpurlconnection-para-enviar-dados-a-uma-pagina-php-jsp-asp-net-etc-usando-o-metodo-post-3505.html
            //https://ihofmann.wordpress.com/2012/08/09/restful-web-services-with-json-in-android/
            String dados=null;

            DataOutputStream out;
            DataInputStream in;

            try{
                //criar conexão
                //trocar isso por JSON
                URL url= new URL("http://www.android.com/");//mudar para receber ela por parametro
                HttpURLConnection urlConnection= (HttpURLConnection)url.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                //urlConnection.setRequestProperty();

                out =new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(dados);

                in= new DataInputStream(urlConnection.getInputStream());


                urlConnection.disconnect();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();//URL invalida
            } catch (IOException e) {
                e.printStackTrace();//não pode criar input stream
            }
            finally {

            }
            //inicia a interface Home do Usuario

            return null;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
