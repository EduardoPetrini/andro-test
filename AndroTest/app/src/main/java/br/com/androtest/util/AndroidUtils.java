package br.com.androtest.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.TextView;

import br.com.androtest.R;

/**
 * Created by eduardo on 01/06/15.
 */
public class AndroidUtils {

    public static void alertUser(String mensagem, final Activity activity){
        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(activity);

        TextView myMsg = new TextView(activity);
        myMsg.setText(mensagem);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        popupBuilder.setView(myMsg);
        popupBuilder.show();
    }
    public static void alertTripUser(String mensagem, final Activity activity){
        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(activity);

        TextView myMsg = new TextView(activity);
        myMsg.setText(mensagem);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        popupBuilder.setView(myMsg);
        popupBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        popupBuilder.show();
    }
}
