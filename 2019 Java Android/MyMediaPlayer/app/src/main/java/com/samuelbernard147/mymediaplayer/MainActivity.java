package com.samuelbernard147.mymediaplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPlay;
    private Button btnStop;

    private final String TAG = MainActivity.class.getSimpleName();
    private Messenger mService = null;
    private Intent mBoundServiceIntent;
    private boolean mServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);

        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        mBoundServiceIntent = new Intent(MainActivity.this, MediaService.class);
        mBoundServiceIntent.setAction(MediaService.ACTION_CREATE);

        startService(mBoundServiceIntent);
        bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_play:
                if (!mServiceBound) return;
                try {
                    mService.send(Message.obtain(null, MediaService.PLAY, 0, 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_stop:
                if (!mServiceBound) return;
                try {
                    mService.send(Message.obtain(null, MediaService.STOP, 0, 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        unbindService(mServiceConnection);
        mBoundServiceIntent.setAction(MediaService.ACTION_DESTROY);

        startService(mBoundServiceIntent);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mServiceBound = true;
        }

    };
}
