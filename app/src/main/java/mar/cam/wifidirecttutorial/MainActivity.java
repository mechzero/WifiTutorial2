package mar.cam.wifidirecttutorial;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button btnDiscover, btnOnOff, btnSend;
    ListView listView;
    TextView read_msg_box, connectionStatus;
    EditText writeMsg;

    WifiManager wifiManager;
    WifiP2pManager mManager;

    WifiP2pManager.Channel mChannel;

    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialWork();
        exqListener();

    }

    private void exqListener() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
         btnOnOff.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (wifiManager.isWifiEnabled()){
                     wifiManager.setWifiEnabled(false);
                     btnOnOff.setText("ON");
                 } else { wifiManager.setWifiEnabled(true);
                 btnOnOff.setText("OFF");}
             }
         });
    }

    private void initialWork() {
        btnDiscover = findViewById(R.id.discoveryBtn);
        btnOnOff = findViewById(R.id.wifiOnBtn);
        btnSend = findViewById(R.id.sendButton);

        listView = findViewById(R.id.peerListView);
        read_msg_box = findViewById(R.id.readMsg);

        connectionStatus = findViewById(R.id.connectionStatus);
        writeMsg = findViewById(R.id.writeMsg);




        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

       // mChannel = mManager.initialize(this, Looper.myLooper(), null);


        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);







      mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel,this);

       mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }

        @Override
        protected  void onResume(){
        super.onResume();
        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel,this);
        registerReceiver(mReceiver, mIntentFilter);

        }

        @Override
    protected  void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
        }




}
