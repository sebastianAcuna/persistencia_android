package com.example.persistenciaenandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.persistenciaenandroid.clases.Parking;
import com.example.persistenciaenandroid.R;
import com.example.persistenciaenandroid.adapters.viewholder.ViewHolderParking;

import java.util.ArrayList;

public class AdapterParking extends RecyclerView.Adapter<ViewHolderParking> {

    private Context context;
    private ArrayList<Parking> parkings;
    private OnItemClickListener itemClickListener;
    private OnLongItemClickListener itemLongClickListener;


    public AdapterParking(Context context, ArrayList<Parking> parkings, OnItemClickListener itemClickListener, OnLongItemClickListener itemLongClickListener) {
        this.context = context;
        this.parkings = parkings;
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }

    public interface OnItemClickListener { void onItemClick(Parking parking ); }
    public interface OnLongItemClickListener { void onLongItemClick(Parking parking ); }
    @NonNull
    @Override
    public ViewHolderParking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_parking, parent,false);
        return new ViewHolderParking(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderParking holder, int position) {
        holder.bind(context, parkings.get(position),itemClickListener, itemLongClickListener);
    }

    @Override
    public int getItemCount() {
        return parkings.size();
    }
}
