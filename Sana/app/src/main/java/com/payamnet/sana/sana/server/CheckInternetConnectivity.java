package com.payamnet.sana.sana.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.payamnet.sana.sana.constants.Constants;
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
        } /*else {
            Toast.makeText(this.context, "Network is available.", Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    protected Boolean doInBackground(Context... params) {
//        if (isNetworkAvailable(params[0])) {
//            try {
//                HttpURLConnection urlC = (HttpURLConnection)
//                        (new URL("http://google.com")
//                                .openConnection());
//                urlC.setRequestProperty("User-Agent", "Android");
//                urlC.setRequestProperty("Connection", "close");
//                urlC.setConnectTimeout(15000);
//                urlC.connect();
//                return (urlC.getResponseCode() == 200/* && urlC.getContentLength() == 0*/);
//            } catch (IOException e) {
//                Log.i(Constants.TAG, "Error checking internet connection: " + e.getMessage());
//            }
//        } else {
//            Log.i(Constants.TAG, "No network available!");
//        }
        return isNetworkAvailable(params[0]);
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        Boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.i(Constants.TAG, "isNetworkAvailable: is connected: " + isConnected);
        return isConnected;
    }

}

