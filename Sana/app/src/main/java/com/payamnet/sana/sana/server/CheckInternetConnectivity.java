package com.payamnet.sana.sana.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.payamnet.sana.sana.constants.Messages;

/**
 * Created by kosar on 8/4/18.
 */

public class CheckInternetConnectivity extends AsyncTask<Context, Void, Boolean> {

    private Context context;

    public CheckInternetConnectivity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (!b) {
            Toast.makeText(this.context, Messages.NO_INTERNET_CONNECTIVITY, Toast.LENGTH_LONG).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    new CheckInternetConnectivity(context).execute(context);
                }
            }, 10000L);
        }
    }

    @Override
    protected Boolean doInBackground(Context... params) {
        return isNetworkAvailable(params[0]);
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        Boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}