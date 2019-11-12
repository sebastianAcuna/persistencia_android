package com.example.persistenciaenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.persistenciaenandroid.clases.Utilidad;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private EditText user, pass;
    private Button btn_login;
    private CheckBox chk_login;
    private SharedPreferences shared;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared =  getSharedPreferences(Utilidad.SHARED_CONFIG_TEL, MODE_PRIVATE);

        bind();

        if(!TextUtils.isEmpty(shared.getString(Utilidad.SHARED_LOGIN_USER,"")) && !TextUtils.isEmpty(shared.getString(Utilidad.SHARED_LOGIN_PASS,""))){
            Intent intent = new Intent(MainActivity.this, actividadPrincipal.class);
            startActivity(intent);
        }



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(user.getText().toString()) && !TextUtils.isEmpty(pass.getText().toString())){
                    if(chk_login.isChecked()){
                        SharedPreferences configuraciones = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        configuraciones.edit().remove(Utilidad.SHARED_LOGIN_USER).apply();
                        shared.edit().putString(Utilidad.SHARED_LOGIN_USER, user.getText().toString()).apply();
                        shared.edit().putString(Utilidad.SHARED_LOGIN_PASS, pass.getText().toString()).apply();
                    }else{
                       shared.edit().remove(Utilidad.SHARED_LOGIN_USER).apply();
                       shared.edit().remove(Utilidad.SHARED_LOGIN_PASS).apply();
                    }

                    Intent intent = new Intent(MainActivity.this, actividadPrincipal.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this, "Debe ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    void bind(){
        chk_login = (CheckBox) findViewById(R.id.chk_login);
        user = (EditText) findViewById(R.id.et_user);
        pass = (EditText) findViewById(R.id.et_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

}
