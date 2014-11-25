package com.example.christopherlim.crossgame;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class wifiP2PInit extends HomeScreen implements WiFiDirectServicesList.DeviceClickListener{
    private final IntentFilter intentFilter = new IntentFilter();
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver receiver;

    public static final String TAG = "Wifi Direct";
    public static final String SERVICE_INSTANCE = "CrossGame";
    public static final String SERVICE_REG_TYPE = "_presence._tcp";

    private TextView statusTxtView;
    static final int SERVER_PORT = 4545;
    private WifiP2pDnsSdServiceRequest serviceRequest;

    private WiFiDirectServicesList deviceList;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private DBHelper mydb;
    private String gender;
    private String orientation;
    class Connection {
        Connection() {
            c_gender = "";
            c_orientation = "";
        }

        public void setGender(String g) {
            c_gender = g;
        }
        public void setOrientation(String o) {
            c_orientation = o;
        }
        public String getGender() {
            return c_gender;
        }
        public String getOrientation() {
            return c_orientation;
        }
        private String c_gender;
        private String c_orientation;

    };
    final HashMap<String, Connection> connections = new HashMap<String, Connection>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "in onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_p2p_init);
        statusTxtView = (TextView) findViewById(R.id.status_text);
        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        gender = "";
        orientation = "";
        startRegistrationAndDiscovery();

        deviceList = new WiFiDirectServicesList();
        getFragmentManager().beginTransaction()
                .add(R.id.container_root, deviceList, "services").commit();

           }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wifi_p2_pinit, menu);
        Log.d(TAG, "in onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "in onOptionsItemSelected");
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "in onRestart");
        Fragment frag = getFragmentManager().findFragmentByTag("services");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
            Log.d(TAG, "inside frag not null. after remove frag");
        }
        super.onRestart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "in onResume");
        Log.d("Wifi Direct:", "in onActivityCreated");
        WiFiDirectServicesList fragment = (WiFiDirectServicesList) getFragmentManager()
                .findFragmentByTag("services");
        if(fragment != null) {
            WiFiDirectServicesList.WiFiDevicesAdapter adapter = ((WiFiDirectServicesList.WiFiDevicesAdapter) fragment
                    .getListAdapter());
            adapter.clear();
        }

        super.onResume();
        receiver = new WiFiBroadcastReceiver(mManager, mChannel, this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        Log.d(TAG, "in onPause");
        WiFiDirectServicesList fragment = (WiFiDirectServicesList) getFragmentManager()
                .findFragmentByTag("services");
        if(fragment != null) {
            WiFiDirectServicesList.WiFiDevicesAdapter adapter = ((WiFiDirectServicesList.WiFiDevicesAdapter) fragment
                    .getListAdapter());
            adapter.clear();
        }
        super.onPause();
        unregisterReceiver(receiver);
    }


    @Override
    protected void onStop() {

        WiFiDirectServicesList fragment = (WiFiDirectServicesList) getFragmentManager()
                .findFragmentByTag("services");
        if(fragment != null) {
            WiFiDirectServicesList.WiFiDevicesAdapter adapter = ((WiFiDirectServicesList.WiFiDevicesAdapter) fragment
                    .getListAdapter());
            adapter.clear();
        }
        if (mManager != null && mChannel != null) {
            mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {

                @Override
                public void onFailure(int reasonCode) {
                    Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);
                }

                @Override
                public void onSuccess() {
                    Toast.makeText(wifiP2PInit.this, "Disconnected", Toast.LENGTH_LONG).show();
                }

            });
        }
        super.onStop();
    }


    private void startRegistrationAndDiscovery() {
        Log.d(TAG, "in startRegistrationAndDiscovery");
        mydb = new DBHelper(this);
        //ArrayList contacts = mydb.getAllContacts();
        Map<String, String> ourRecord = new HashMap<String, String>();
        //Bundle extras = getIntent().getExtras();
        //if(extras != null) {
            //int Value = extras.getInt("id");
           //if(Value > 0) {
                //String name = db.sqlexec(getname);
                ourRecord.put("available", "visible");
               Cursor res = mydb.getData(1);
                res.moveToFirst();
                gender = res.getString(res.getColumnIndex(DBHelper.CONTACTS_COLUMN_GENDER));
                orientation = res.getString(res.getColumnIndex(DBHelper.CONTACTS_COLUMN_ORIENTATION));
                //record.put("name", name);
                ourRecord.put("gender", gender);
                ourRecord.put("orientation", orientation);
            //}
        //}


       WifiP2pDnsSdServiceInfo
                service = WifiP2pDnsSdServiceInfo.newInstance(
                SERVICE_INSTANCE, SERVICE_REG_TYPE, ourRecord);
        mManager.addLocalService(mChannel, service, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                appendStatus("Added Local Service");
            }

            @Override
            public void onFailure(int error) {
                appendStatus("Failed to add a service");
            }
        });

        discoverService();

    }

    private void discoverService() {
        Log.d(TAG, "in discoverService");
        mManager.setDnsSdResponseListeners(mChannel,
                new WifiP2pManager.DnsSdServiceResponseListener() {

                    @Override
                    public void onDnsSdServiceAvailable(String instanceName,
                                                        String registrationType, WifiP2pDevice srcDevice) {

                        // A service has been discovered. Is this our app?

                        if (instanceName.equalsIgnoreCase(SERVICE_INSTANCE)) {

                            // update the UI and add the item the discovered
                            // device.
                            WiFiDirectServicesList fragment = (WiFiDirectServicesList) getFragmentManager()
                                    .findFragmentByTag("services");
                            Log.d(TAG, "inside discoverService after making wifidirectservicelist fragment");
                            if (fragment != null) {
                                WiFiDirectServicesList.WiFiDevicesAdapter adapter = ((WiFiDirectServicesList.WiFiDevicesAdapter) fragment
                                        .getListAdapter());
                                Log.d(TAG, "inside discoverService after making adapter");
                                //adapter.clear();, filter by global hashmap
                                Connection temp_connection = connections.get(srcDevice.deviceAddress);
                                if(!adapter.contains(srcDevice) && temp_connection != null) {
                                   if(temp_connection.getGender().equals(orientation) && temp_connection.getOrientation().equals(gender)) {
                                        //String poopy = temp_connection.getGender();
                                        //String poopier = temp_connection.getOrientation();
                                        adapter.add(srcDevice);
                                        Log.d(TAG, "inside discoverService after adding device to adapter");
                                        adapter.notifyDataSetChanged();
                                        Log.d(TAG, "onBonjourServiceAvailable "
                                                + instanceName);
                                    }
                                }
                            }
                        }

                    }
                }, new WifiP2pManager.DnsSdTxtRecordListener() {


                  @Override
                    public void onDnsSdTxtRecordAvailable(
                            String fullDomainName, Map<String, String> record,
                            WifiP2pDevice device) {
                        Log.d(TAG,
                                device.deviceName + " is "
                                        + record.get("available"));
                     //add to global hashmap
                      Connection c = new Connection();
                      c.setGender(record.get("gender"));
                      c.setOrientation(record.get("orientation"));
                      connections.put(device.deviceAddress, c);

                    }
                });

        // After attaching listeners, create a service request and initiate
        // discovery.
        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        Log.d(TAG, "inside discoverService after making a new serviceRequest");
        mManager.addServiceRequest(mChannel, serviceRequest,
                new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        appendStatus("Added service discovery request");
                    }

                    @Override
                    public void onFailure(int arg0) {
                        appendStatus("Failed adding service discovery request");
                    }
                });
        mManager.discoverServices(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                appendStatus("Service discovery initiated");
            }

            @Override
            public void onFailure(int arg0) {
                appendStatus("Service discovery failed");

            }
        });
    }

    public void connectP2p(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        if (serviceRequest != null)
            mManager.removeServiceRequest(mChannel, serviceRequest,
                    new WifiP2pManager.ActionListener() {

                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int arg0) {
                        }
                    });

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                appendStatus("Connecting to service");
            }

            @Override
            public void onFailure(int errorCode) {
                appendStatus("Failed connecting to service");
            }
        });
    }


    public void appendStatus(String status) {
        String current = statusTxtView.getText().toString();
        statusTxtView.setText(current + "\n" + status);
    }
}



