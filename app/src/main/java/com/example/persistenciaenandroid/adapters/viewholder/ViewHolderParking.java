package com.example.persistenciaenandroid.adapters.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.persistenciaenandroid.clases.Parking;
import com.example.persistenciaenandroid.R;
import com.example.persistenciaenandroid.adapters.AdapterParking;

public class ViewHolderParking extends RecyclerView.ViewHolder {

    private TextView num_parkig, id_cliente;

    public ViewHolderParking(@NonNull View itemView) {
        super(itemView);

        num_parkig = itemView.findViewById(R.id.num_parking_list);
        id_cliente = itemView.findViewById(R.id.id_cliente_list);
    }


    public void bind(Context context, final Parking parking, final AdapterParking.OnItemClickListener item, final AdapterParking.OnLongItemClickListener longitem){
        num_parkig.setText(parking.getNumParking());
        id_cliente.setText(parking.getIdCliente());


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.onItemClick(parking);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longitem.onLongItemClick(parking);
                return false;
            }
        });
    }


}
