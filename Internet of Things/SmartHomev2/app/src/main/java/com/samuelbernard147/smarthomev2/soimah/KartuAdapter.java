package com.samuelbernard147.smarthomev2.soimah;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samuelbernard147.smarthomev2.R;

import java.util.ArrayList;

public class KartuAdapter extends RecyclerView.Adapter<KartuAdapter.KartuViewHolder> {
    private ArrayList<KartuItems> mData = new ArrayList<>();

    public KartuAdapter() {
    }

    /**
     * Gunakan method ini jika semua datanya akan diganti
     *
     * @param items kumpulan data baru yang akan mengganti semua data yang sudah ada
     */
    public void setData(ArrayList<KartuItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Gunakan method ini jika ada 1 data yang ditambahkan
     *
     * @param item data baru yang akan ditambahkan
     */
    public void addItem(final KartuItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public KartuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_soimah_user, viewGroup, false);
        return new KartuViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull KartuViewHolder KartuViewHolder, int position) {
        KartuViewHolder.textViewNoKartu.setText(mData.get(position).getNoKartu());
        KartuViewHolder.textViewNama.setText(mData.get(position).getNama());
        KartuViewHolder.textViewWaktu.setText(mData.get(position).getWaktu());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class KartuViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNoKartu;
        TextView textViewNama;
        TextView textViewWaktu;

        KartuViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNoKartu = itemView.findViewById(R.id.textNoKartu);
            textViewNama = itemView.findViewById(R.id.textNama);
            textViewWaktu = itemView.findViewById(R.id.textHour);
        }
    }
}