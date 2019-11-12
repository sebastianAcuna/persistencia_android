package com.example.persistenciaenandroid.servicios;

import android.content.Context;

import com.example.persistenciaenandroid.clases.Parking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServicioParking {
    private ArrayList<Parking> lista;
    private String nombreArchivo;
    private Context context;
    private static ServicioParking instance;

    private ServicioParking(Context context) throws IOException, ClassNotFoundException {
        this.context = context;
        this.nombreArchivo = "notas.obj";
        try{
            lista = cargarNotas();
        }catch (IOException e) {
            lista = new ArrayList<>();
//            guardarNota(new Parking("",""));
        }
    }

    public static ServicioParking getInstance(Context context) throws IOException, ClassNotFoundException {
        if (instance == null)
            instance = new ServicioParking(context);
        return instance;
    }

    public void guardarNota(Parking nota) throws IOException {
        lista.add(nota);
        ObjectOutputStream output = new ObjectOutputStream(context.openFileOutput(nombreArchivo
                , Context.MODE_PRIVATE));
        output.writeObject(lista);
        output.close();
    }

    public ArrayList<Parking> cargarNotas() throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(context.openFileInput(nombreArchivo));
        lista = (ArrayList<Parking>) input.readObject();
        input.close();

        return lista;
    }

    public void eliminar(Parking nota) throws IOException {
        lista.remove(nota);
        ObjectOutputStream output = new ObjectOutputStream(context.openFileOutput(nombreArchivo
                , Context.MODE_PRIVATE));
        output.writeObject(lista);
        output.close();
    }

    public void eliminarTodo() throws IOException{
        if(lista.size() > 0){
            for(int i = 0; i < lista.size(); i++){
                lista.remove(i);
            }
        }
        ObjectOutputStream output = new ObjectOutputStream(context.openFileOutput(nombreArchivo,Context.MODE_PRIVATE));
        output.writeObject(output);
        output.close();
    }
}
