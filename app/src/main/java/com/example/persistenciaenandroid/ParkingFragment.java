package com.example.persistenciaenandroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ParkingFragment extends Fragment {
    private FloatingActionButton button_add_parqueo;
    private View view;
    private String archivo = "parkings.obj";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_parking, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_add_parqueo = (FloatingActionButton) view.findViewById(R.id.button_add_parqueo);


        try{
            if ((getActivity().openFileInput(archivo)) != null){
                cargarArchivo();
            }
        }catch (IOException e){
            Toast.makeText(getActivity(), "No existe un archivo aún", Toast.LENGTH_SHORT).show();
        }

        button_add_parqueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForSuccess("Registrar parqueo");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actividadPrincipal activity = (actividadPrincipal) getActivity();
        if (activity != null){
            activity.updateView("parking Control", "Parqueos");
        }
    }


    public void cargarArchivo(){
        try{
            ObjectInputStream objInput = new ObjectInputStream(getActivity().openFileInput(archivo));
            Parking persona = (Parking) objInput.readObject();
            objInput.close();
            txtContenido.setText(persona.toString());
            Toast.makeText(getActivity(), "Archivo cargado correctamente", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(getActivity(), "Error al cargar el archivo", Toast.LENGTH_SHORT).show();
        }catch (ClassNotFoundException e){
            Log.e("MainActivity", "Error clase no encontrada");
        }
    }

    private void showAlertForSuccess( String title){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        if(title != null) builder.setTitle(title);
        builder.setCancelable(false);
        View viewInfalted = LayoutInflater.from(getActivity()).inflate(R.id.alert_empty_login,null);

        final EditText numParking = (EditText) viewInfalted.findViewById(R.id.num_matricula);
        final EditText idCliente = (EditText) viewInfalted.findViewById(R.id.id_cliente);

        builder.setView(viewInfalted);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(!TextUtils.isEmpty(numParking.getText().toString()) && !TextUtils.isEmpty(idCliente.getText().toString())){
                    try{
                        ObjectOutputStream objOutput = new ObjectOutputStream(getActivity().openFileOutput(archivo, MODE_PRIVATE));
                        objOutput.writeObject(new Parking(numParking.getText().toString(),idCliente.getText().toString()));
                        objOutput.close();
                        Toast.makeText(getActivity(), "Archivo guardado correctamente", Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        Toast.makeText(getActivity(), "Error al guardar el contenido", Toast.LENGTH_SHORT).show();

                    }
                }



            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }
}
