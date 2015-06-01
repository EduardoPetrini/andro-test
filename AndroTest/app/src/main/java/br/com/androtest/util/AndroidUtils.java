package br.com.androtest.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by eduardo on 01/06/15.
 */
public class AndroidUtils {

    public static void alertUser(String mensagem, Activity activity){
        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(activity);
        TextView myMsg = new TextView(activity);
        myMsg.setText(mensagem);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        popupBuilder.setView(myMsg);
        popupBuilder.show();
    }
}
