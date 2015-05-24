package br.com.androtest;

import br.com.androtest.util.SystemUiHider;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.net.HttpURLConnection;
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
                        .setDefaultFontPath("comfortaa_regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        SpannableString s = new SpannableString("");
        s.setSpan(new TypefaceSpan("comfortaa_regular.ttf"), 0, s.length(),
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

        Intent intent= new Intent(this, HomeActivity.class);

        startActivity(intent);

    }

    public void entrar(View view){
        //Renomear Home Activity para CadastroActivity
        //Intent intent= new Intent(this, HomeActivity.class);

        //pega as informacoes da tela
        EditText editTextUser=(EditText) findViewById(R.id.user_email);
        String user=editTextUser.getText().toString();

        EditText editTextPassword=(EditText) findViewById(R.id.user_password);
        String password=editTextPassword.getText().toString();

        //chama o web service para validar as info
        //URL url= new URL("http://www.android.com/");
        //HttpURLConnection;
        //inicia a interface Home do Usuario
        //startActivity(intent);

    }

}
