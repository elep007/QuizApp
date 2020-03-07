package com.ttb.quiz.connections;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ttb.quiz.R;

/**
 * Created by Rahul Bhardwaj.
 */

public class Utils {
    public static String site_url = "http://138.68.76.227/";
    public static String base_url = site_url + "api/index.php/";
    public static String user_image_url = site_url + "/admin/assets/uploads/user_images/";
    public static String country_flag_url = site_url + "admin/assets/flags/";
    public static String subtopic_images = site_url + "admin/assets/uploads/subtopic_images/";
    public static String acheivement_images = site_url + "admin/assets/uploads/acheivement_images/";
    public static String question_images = site_url + "admin/assets/uploads/question_images/";
    public static int service_charges = 2;


    public static final boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static final void nointernet(Context context) {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
            alertDialog.setIcon(R.drawable.dialog_warning);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // finish();
                }
            });

            alertDialog.show();
        } catch (Exception e) {
            Log.d("no Internet", "Show Dialog: " + e.getMessage());
        }
    }

    public static void errorDialog(final Context c, final String Subject, final String message) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(
                c);

        alert.setTitle("Message");
        alert.setMessage("Something unexpected happened! Please contact support if the problem persists.");
        alert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {


                    }
                });

        alert.show();


    }

    public static void errorDialognouser(final Context c, final String Subject, final String message) {


        final AlertDialog.Builder alert = new AlertDialog.Builder(
                c);

        alert.setTitle("Message");
        alert.setMessage("Something unexpected happened! Please contact support if the problem persists.");
        alert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {


                    }
                });

        alert.show();


    }


}
