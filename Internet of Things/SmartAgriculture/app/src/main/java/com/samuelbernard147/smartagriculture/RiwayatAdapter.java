package com.samuelbernard147.smartagriculture;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Emeth on 10/31/2016.
 */

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {
    private ArrayList<RiwayatItems> mData = new ArrayList<>();

    public RiwayatAdapter() {
    }

    /**
     * Gunakan method ini jika semua datanya akan diganti
     *
     * @param items kumpulan data baru yang akan mengganti semua data yang sudah ada
     */
    public void setData(ArrayList<RiwayatItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Gunakan method ini jika ada 1 data yang ditambahkan
     *
     * @param item data baru yang akan ditambahkan
     */
    public void addItem(final RiwayatItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.riwayat_items, viewGroup, false);
        return new RiwayatViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder riwayatViewHolder, int position) {
        riwayatViewHolder.textViewHumidity.setText(mData.get(position).getHumidity());
        riwayatViewHolder.textViewTime.setText(mData.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewAuthor;
        TextView textViewHumidity;
        TextView textViewTime;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewNamaAuthor = itemView.findViewById(R.id.textAuthor);
            textViewHumidity = itemView.findViewById(R.id.textKelambapan);
            textViewTime = itemView.findViewById(R.id.textTime);
        }
    }
}
