package com.example.persistenciaenandroid.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.persistenciaenandroid.MainActivity;
import com.example.persistenciaenandroid.clases.Parking;
import com.example.persistenciaenandroid.R;
import com.example.persistenciaenandroid.actividadPrincipal;
import com.example.persistenciaenandroid.adapters.AdapterParking;
import com.example.persistenciaenandroid.clases.Utilidad;
import com.example.persistenciaenandroid.servicios.ServicioParking;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Objects;

public class ParkingFragment extends Fragment {
    private FloatingActionButton button_add_parqueo;
    private View view;
    private String archivo = "parkings.obj";
    AdapterParking adapterParking;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;

    private Toolbar toolbar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(Utilidad.SHARED_CONFIG_TEL, Context.MODE_PRIVATE);
    }

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);


        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);



        cargarArchivo();

//        try{
//            if ((Objects.requireNonNull(getActivity()).openFileInput(archivo)) != null){
//
//            }
//        }catch (IOException e){
//            Toast.makeText(getActivity(), "No existe un archivo aún", Toast.LENGTH_SHORT).show();
//        }

        button_add_parqueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForSuccess("Registrar parqueo");
            }
        });
    }

    //dialogo de lista simples
    private AlertDialog listaRadio(final Activity activity){
        final String[] items = {"Sin conservar los registros localmente" , "Conservando los registros localmente"};

        final int[] number = {0};

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Exportar registros");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                number[0] = which;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView listView = ((AlertDialog) dialog).getListView();
                String seleccion = (String) listView.getAdapter().getItem(listView.getCheckedItemPosition());
                switch (number[0]){
                    case 0:

                        try {
                            ServicioParking.getInstance(activity.getApplicationContext()).eliminarTodo();
                            if(adapterParking != null) adapterParking.notifyDataSetChanged();
                            cargarArchivo();
                        } catch (IOException e) {
                            Toast.makeText(activity.getApplicationContext(), "Error al actualizar el archivo", Toast.LENGTH_SHORT).show();
                        } catch (ClassNotFoundException e) {
                            Toast.makeText(activity.getApplicationContext(), "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(activity.getApplicationContext(), "Datos exportados correctamente", Toast.LENGTH_SHORT).show();

                        break;
                    case 1:
                        Toast.makeText(activity.getApplicationContext(), "Datos exportados correctamente", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.cancel();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        actividadPrincipal acti = (actividadPrincipal) getActivity();
        if (acti != null){
            SharedPreferences configuraciones = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String nombre = configuraciones.getString("nombre",null);
            if (!TextUtils.isEmpty(nombre)){
                acti.cambiarNombreUser(nombre);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                sharedPreferences.edit().remove(Utilidad.SHARED_LOGIN_USER).apply();
                sharedPreferences.edit().remove(Utilidad.SHARED_LOGIN_PASS).apply();

                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.exportar:
                listaRadio(getActivity()).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actividadPrincipal activity = (actividadPrincipal) getActivity();
        if (activity != null){
            activity.updateView("parking Control", "Parqueos");
        }
    }


    private void cargarArchivo(){
            try {
                adapterParking = new AdapterParking(getActivity(), ServicioParking.getInstance(getActivity()).cargarNotas(), new AdapterParking.OnItemClickListener() {
                    @Override
                    public void onItemClick(Parking parking) {

                    }
                }, new AdapterParking.OnLongItemClickListener() {
                    @Override
                    public void onLongItemClick(final Parking parking) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("¿Desea eliminar el parking?")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            ServicioParking.getInstance(getContext()).eliminar(parking);
                                            adapterParking.notifyDataSetChanged();
                                        } catch (IOException e) {
                                            Toast.makeText(getContext(), "Error al actualizar el archivo", Toast.LENGTH_SHORT).show();
                                        } catch (ClassNotFoundException e) {
                                            Toast.makeText(getContext(), "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                })
                                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).create().show();


                    }
                });
                recyclerView.setHasFixedSize(true);
                GridLayoutManager layout = new GridLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext(),2);
                recyclerView.setLayoutManager(layout);
                recyclerView.setAdapter(adapterParking);

            } catch (IOException e) {
                Toast.makeText(getActivity(), "Error al cargar el archivo (onCreate)", Toast.LENGTH_SHORT).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(getActivity(), "Error al cargar el objeto (onCreate)", Toast.LENGTH_SHORT).show();
            }
    }

    private void showAlertForSuccess( String title){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        if(title != null) builder.setTitle(title);
        builder.setCancelable(false);
        View viewInfalted = LayoutInflater.from(getActivity()).inflate(R.layout.alert_empty_login,null);

        final EditText numParking = (EditText) viewInfalted.findViewById(R.id.num_matricula);
        final EditText idCliente = (EditText) viewInfalted.findViewById(R.id.id_cliente);

        builder.setView(viewInfalted);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(!TextUtils.isEmpty(numParking.getText().toString()) && !TextUtils.isEmpty(idCliente.getText().toString())){
                    try {
                        ServicioParking.getInstance(getActivity())
                        .guardarNota(new Parking(numParking.getText().toString(),idCliente.getText().toString()));
                        cargarArchivo();

                    } catch (IOException e) {
                        Toast.makeText(getActivity(), "Error al cargar el archivo (onColor)", Toast.LENGTH_SHORT).show();
                    } catch (ClassNotFoundException e) {
                        Toast.makeText(getActivity(), "Error al cargar el objeto (onColor)", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }
}
