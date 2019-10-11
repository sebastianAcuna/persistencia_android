package com.example.persistenciaenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText user, pass;
    private Button btn_login;
    private CheckBox chk_login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bind();



    }


    void bind(){
        chk_login = (CheckBox) findViewById(R.id.chk_login);
        user = (EditText) findViewById(R.id.et_user);
        pass = (EditText) findViewById(R.id.et_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

}
