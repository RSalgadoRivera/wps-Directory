package com.example.rabdos7.wpsdirectory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

public class NTres extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button btnaula;
    Button btncenman;
    Button btnlabpro;
    Button btnlia;
    Button btnltcye;

    public ImageView area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnaula = (Button) findViewById(R.id.btnAula);
        btnlabpro = (Button) findViewById(R.id.btnLabpro);
        btnlia = (Button) findViewById(R.id.btnLia);
        btnltcye = (Button) findViewById(R.id.btnLtcye);
        btncenman = (Button) findViewById(R.id.btnCenman);
        area = (ImageView) findViewById(R.id.imgPlano);

        btnaula.setOnClickListener(this);
        btnlabpro.setOnClickListener(this);
        btnlia.setOnClickListener(this);
        btnltcye.setOnClickListener(this);
        btncenman.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ntres, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAula:
                area.setImageResource(R.drawable.aula431);
                break;

            case R.id.btnLabpro:
                area.setImageResource(R.drawable.labpro);
                break;

            case R.id.btnLia:
                area.setImageResource(R.drawable.lia);
                break;

            case R.id.btnLtcye:
                area.setImageResource(R.drawable.ltcye);
                break;

            case R.id.btnCenman:
                area.setImageResource(R.drawable.cenman);
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
