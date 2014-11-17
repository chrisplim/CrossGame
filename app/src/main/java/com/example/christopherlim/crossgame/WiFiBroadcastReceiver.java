package com.example.christopherlim.crossgame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

/**
 * Created by Sean on 11/15/2014.
 */
public class WiFiBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private wifiP2PInit mActivity;
    //WifiP2pManager.PeerListListener peerListListener;


    public WiFiBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                 wifiP2PInit activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }



    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();



        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                Toast.makeText(context, R.string.wifi_enabled, Toast.LENGTH_LONG).show();


            } else {
                // Wi-Fi P2P is not enabled
                Toast.makeText(context, R.string.wifi_disabled, Toast.LENGTH_LONG).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()

            if (mManager != null) {
                //mManager.requestPeers(mChannel, peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }
/*
    public void connect(final Context context) {
        // Picking the first device found on the network.
         //WifiP2pDevice device = (WifiP2pDevice) peers.get(0);

        WifiP2pConfig config = new WifiP2pConfig();
        //config.deviceAddress = device.deviceAddress;
        //config.wps.setup = WpsInfo.PBC;

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
                //Toast.makeText(final Context context, "Connected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(context, "Connect failed. Retry.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void disconnect(final Context context) {
        // final DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()
        //         .findFragmentById(R.id.frag_detail);
        //fragment.resetViews();
        mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onFailure(int reasonCode) {
                //Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);

            }

            @Override
            public void onSuccess() {

                //fragment.getView().setVisibility(View.GONE);
                Toast.makeText(context, "successfully connected", Toast.LENGTH_LONG).show();
            }

        });
    }
*/
}
