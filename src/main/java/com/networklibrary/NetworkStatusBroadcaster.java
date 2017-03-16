package com.networklibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by quadandroid-1 on 15/3/17.
 */

public class NetworkStatusBroadcaster extends BroadcastReceiver {
    INetworkChangeListener callback;

    public NetworkStatusBroadcaster() {
    }

    public NetworkStatusBroadcaster(INetworkChangeListener callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.d("NetworkCheckReceiver", "NetworkCheckReceiver invoked...");


            boolean noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

            if (callback != null)
                callback.onNetworkStatusChanged(!noConnectivity);
        }
    }
}
