package com.example.rabdos7.wpsdirectory;

import android.content.Intent;
import android.net.Uri;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public DatabaseAccess databaseAccess;
    public ImageView floor;
    public ImageView pb;
    public ImageView p1;
    public ImageView p2;
    public ImageView p3;
    public ImageView p4;
    public ImageView p5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floor = (ImageView)findViewById(R.id.imgPlano);
        pb = (ImageView)findViewById(R.id.imgPB);
        p1 = (ImageView)findViewById(R.id.imgP1);
        p2 = (ImageView)findViewById(R.id.imgP2);
        p3 = (ImageView)findViewById(R.id.imgP3);
        p4 = (ImageView)findViewById(R.id.imgP4);
        p5 = (ImageView)findViewById(R.id.imgP5);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        databaseAccess = DatabaseAccess.getInstance(this);

        new LongRunningTask().execute();

        databaseAccess.close();

    }

    private class LongRunningTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            do{
                databaseAccess.open();

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.startScan();
                List<ScanResult> wifiScanResultList = wifiManager.getScanResults();
                Collections.sort(wifiScanResultList, new Comparator<ScanResult>() {
                    @Override
                    public int compare(ScanResult o1, ScanResult o2) {
                        return Integer.valueOf(o2.level).compareTo(o1.level);
                    }
                });

                String piso = "";


                for (int i =0;i< wifiScanResultList.size();i++){

                    piso = databaseAccess.getFloor(wifiScanResultList.get(i).BSSID.toUpperCase());

                    System.out.println(wifiScanResultList.get(i).BSSID.toUpperCase());
                    System.out.println(wifiScanResultList.get(i).level);
                    System.out.println(piso);

                    if (piso.length()>1){

                        break;
                    }

                    System.out.println(wifiScanResultList.get(i).BSSID.toUpperCase()+","+wifiScanResultList.get(i).level);
                }

                changeFloor(piso,floor);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                databaseAccess.close();
            }while (true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ayuda) {
            AlertDialog.Builder ayuda = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
            ayuda.setCancelable(true);
            ayuda.setPositiveButton(
                    "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            ayuda.setMessage("Si creé que no se le está ubicando correctamente por favor acerquese a las escaleras e intente nuevamente.");
            AlertDialog alertDiag = ayuda.create();
            alertDiag.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_pb) {

            Intent intent = new  Intent(this,PlantaBaja.class);
            startActivityForResult(intent,0);

        } else if (id == R.id.nav_n_1) {
            Intent intent = new  Intent(this,NUno.class);
            startActivityForResult(intent,0);

        } else if (id == R.id.nav_n_2) {
            Intent intent = new  Intent(this,NDos.class);
            startActivityForResult(intent,0);

        } else if (id == R.id.nav_n_3) {
            Intent intent = new  Intent(this,NTres.class);
            startActivityForResult(intent,0);

        } else if (id == R.id.nav_n_4) {
            Intent intent = new  Intent(this,NCuatro.class);
            startActivityForResult(intent,0);

        } else if (id == R.id.nav_n_5) {
            Intent intent = new  Intent(this,NCinco.class);
            startActivityForResult(intent,0);

        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFloor (String piso, final ImageView floor){

        switch (piso){

            case "N-1":

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI

                        floor.setImageResource(R.drawable.n1p);
                        floor.setBackgroundResource(R.drawable.select_border);
                        pb.setBackgroundResource(R.drawable.unselected_border);
                        p1.setBackgroundResource(R.drawable.select_border);
                        p2.setBackgroundResource(R.drawable.unselected_border);
                        p3.setBackgroundResource(R.drawable.unselected_border);
                        p4.setBackgroundResource(R.drawable.unselected_border);
                        p5.setBackgroundResource(R.drawable.unselected_border);

                    }
                });

                break;

            case "N-2":

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        floor.setImageResource(R.drawable.n2p);
                        floor.setBackgroundResource(R.drawable.select_border);
                        pb.setBackgroundResource(R.drawable.unselected_border);
                        p1.setBackgroundResource(R.drawable.unselected_border);
                        p2.setBackgroundResource(R.drawable.select_border);
                        p3.setBackgroundResource(R.drawable.unselected_border);
                        p4.setBackgroundResource(R.drawable.unselected_border);
                        p5.setBackgroundResource(R.drawable.unselected_border);

                    }
                });

                break;

            case "N-3":

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        floor.setImageResource(R.drawable.n3p);
                        floor.setBackgroundResource(R.drawable.select_border);
                        pb.setBackgroundResource(R.drawable.unselected_border);
                        p1.setBackgroundResource(R.drawable.unselected_border);
                        p2.setBackgroundResource(R.drawable.unselected_border);
                        p3.setBackgroundResource(R.drawable.select_border);
                        p4.setBackgroundResource(R.drawable.unselected_border);
                        p5.setBackgroundResource(R.drawable.unselected_border);

                    }
                });

                break;

            case "N-4":

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        floor.setImageResource(R.drawable.n4p);
                        floor.setBackgroundResource(R.drawable.select_border);
                        pb.setBackgroundResource(R.drawable.unselected_border);
                        p1.setBackgroundResource(R.drawable.unselected_border);
                        p2.setBackgroundResource(R.drawable.unselected_border);
                        p3.setBackgroundResource(R.drawable.unselected_border);
                        p4.setBackgroundResource(R.drawable.select_border);
                        p5.setBackgroundResource(R.drawable.unselected_border);

                    }
                });

                break;

            case "N-5":

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        floor.setImageResource(R.drawable.n5p);
                        floor.setBackgroundResource(R.drawable.select_border);
                        pb.setBackgroundResource(R.drawable.unselected_border);
                        p1.setBackgroundResource(R.drawable.unselected_border);
                        p2.setBackgroundResource(R.drawable.unselected_border);
                        p3.setBackgroundResource(R.drawable.unselected_border);
                        p4.setBackgroundResource(R.drawable.unselected_border);
                        p5.setBackgroundResource(R.drawable.select_border);

                    }
                });

                break;

            default:

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        floor.setImageResource(R.drawable.pbp);
                        floor.setBackgroundResource(R.drawable.select_border);
                        pb.setBackgroundResource(R.drawable.select_border);
                        p1.setBackgroundResource(R.drawable.unselected_border);
                        p2.setBackgroundResource(R.drawable.unselected_border);
                        p3.setBackgroundResource(R.drawable.unselected_border);
                        p4.setBackgroundResource(R.drawable.unselected_border);
                        p5.setBackgroundResource(R.drawable.unselected_border);
                    }
                });

                break;

        }

    }

}
