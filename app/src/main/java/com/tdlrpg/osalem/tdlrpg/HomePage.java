package com.tdlrpg.osalem.tdlrpg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HomePage extends AppCompatActivity {
    File owner = null;
    Member usr = null;
    Member p2 = null;
    Member p3 = null;
    Member p4 = null;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;
    IntentFilter intentFilter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        owner = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/OwnerData.txt");
        if(!owner.exists())
        {
            openOwnerCreation();
        }

        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader br = null;
        String sCurrentLine = "";
        try
        {
            br = new BufferedReader(new FileReader(owner.getAbsolutePath()));
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine);
            }
            usr = new Member(contentBuilder.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        TextView lvl = findViewById(R.id.p1level);
        TextView nxt = findViewById(R.id.p1exp);
        TextView hp = findViewById(R.id.p1hp);
        TextView sp = findViewById(R.id.p1sp);
        TextView next = findViewById(R.id.p1next);
        ImageView pic = findViewById(R.id.p1pic);
        ProgressBar p1hp = findViewById(R.id.p1hpbar);
        ProgressBar p1sp = findViewById(R.id.p1spbar);
        p1hp.setMax(100);
        p1sp.setMax(100);
        p1hp.getProgressDrawable().setColorFilter(Color.rgb(10,222,110), android.graphics.PorterDuff.Mode.SRC_IN);
        p1sp.getProgressDrawable().setColorFilter(Color.rgb(222,79,176), android.graphics.PorterDuff.Mode.SRC_IN);
        lvl.setText(lvl.getText() + String.valueOf(usr.level));
        nxt.setText(String.valueOf(usr.getNext()));
        p1hp.setProgress(usr.getHpPercent());
        p1sp.setProgress(usr.getSpPercent());
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/profilepicture.png");
        pic.setImageBitmap(bitmap);
        lvl.setVisibility(View.VISIBLE);
        nxt.setVisibility(View.VISIBLE);
        hp.setVisibility(View.VISIBLE);
        sp.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        pic.setVisibility(View.VISIBLE);
        p1hp.setVisibility(View.VISIBLE);
        p1sp.setVisibility(View.VISIBLE);
        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


    }

    @Override //Inflates Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override //Defines behavior for play, save, and view list
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addNewMember();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void openOwnerCreation()
    {
        Intent intent = new Intent(this, OwnerCreation.class);
        startActivity(intent);
    }

    public void addNewMember()
    {
        Intent intent = new Intent(this, AddMemeber.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
