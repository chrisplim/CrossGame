package com.example.christopherlim.crossgame;

import android.app.ListFragment;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 11/16/2014.
 */
public class WiFiDirectServicesList extends ListFragment {
    WiFiDevicesAdapter listAdapter = null;

    interface DeviceClickListener {
        public void connectP2p(WifiP2pDevice wifiP2pService);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Wifi Direct:", "in onCreateView");
        return inflater.inflate(R.layout.devices_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listAdapter = new WiFiDevicesAdapter(this.getActivity(),
                android.R.layout.simple_list_item_2, android.R.id.text1,
                new ArrayList<WifiP2pDevice>());
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //take this out eventually and make it automatic??
        ((DeviceClickListener) getActivity()).connectP2p((WifiP2pDevice) l
                .getItemAtPosition(position));
        ((TextView) v.findViewById(android.R.id.text2)).setText("Connecting");

    }

    public class WiFiDevicesAdapter extends ArrayAdapter<WifiP2pDevice> {
        private List<WifiP2pDevice> items;

        public WiFiDevicesAdapter(Context context, int resource,
                                  int textViewResourceId, List<WifiP2pDevice> items) {
            super(context, resource, textViewResourceId, items);
            this.items = items;
            Log.d("Wifi Direct:", "in WiFiDevicesAdapter constructor");
        }

      public boolean contains(WifiP2pDevice device)
        {
            for(int i = 0; i<items.size(); i++)
            {
                if((items.get(i).deviceName).equals(device.deviceName))
                    return true;
            }
            return false;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("Wifi Direct:", "in getView");
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(android.R.layout.simple_list_item_2, null);
            }
            WifiP2pDevice device = items.get(position);
            if (device != null) {
                TextView nameText = (TextView) v
                        .findViewById(android.R.id.text1);

                if (nameText != null) {
                    nameText.setText(device.deviceName);
                }
                TextView statusText = (TextView) v
                        .findViewById(android.R.id.text2);
                statusText.setText(getDeviceStatus(device.status));
            }
            return v;
        }
    }

    public static String getDeviceStatus(int statusCode) {
        switch (statusCode) {
            case WifiP2pDevice.CONNECTED:
                return "Connected";
            case WifiP2pDevice.INVITED:
                return "Invited";
            case WifiP2pDevice.FAILED:
                return "Failed";
            case WifiP2pDevice.AVAILABLE:
                return "Available";
            case WifiP2pDevice.UNAVAILABLE:
                return "Unavailable";
            default:
                return "Unknown";

        }
    }


}
