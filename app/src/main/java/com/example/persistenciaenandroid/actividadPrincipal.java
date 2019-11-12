package com.example.persistenciaenandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class actividadPrincipal extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toogle;
    private NavigationView navigationView;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        bind();

        shared =  getSharedPreferences(Utilidad.SHARED_CONFIG_TEL, MODE_PRIVATE);

        if (savedInstanceState == null){
            if (navigationView != null) navigationView.setCheckedItem(R.id.parqueos);
            cambiarFragment(new ParkingFragment(), Utilidad.FRAGMENT_PARKING);
        }

        if (navigationView != null) navigationView.setNavigationItemSelectedListener(this);


        cambiarNombreUser();





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }


    public void updateView(String title, String subtitle){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            toolbar.setTitle(title);
            toolbar.setSubtitle(subtitle);
        }

        if(drawerLayout != null){
            setSupportActionBar(toolbar);
            toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
            drawerLayout.addDrawerListener(toogle);
            toogle.syncState();
        }

    }


    public void  cambiarNombreUser(){
        if (navigationView != null){

            String nombre = shared.getString(Utilidad.SHARED_LOGIN_USER,"user");
            String correo = nombre+"@nextu.com";

            TextView nombreUsuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name_title);
            if (nombreUsuario != null) nombreUsuario.setText(nombre);


            TextView correoUsuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
            if (correoUsuario != null) correoUsuario.setText(correo);


        }
    }

    public void cambiarFragment(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment,tag)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        hideKeyboard(this);


        int id = item.getItemId();
        switch (id){
            case R.id.parqueos:
                cambiarFragment(new ParkingFragment(), Utilidad.FRAGMENT_PARKING);
                break;
            case R.id.account:
                cambiarFragment(new CuentaFragment(), Utilidad.FRAGMENT_CUENTA);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        Objects.requireNonNull(inputManager).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public void bind(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }
}
