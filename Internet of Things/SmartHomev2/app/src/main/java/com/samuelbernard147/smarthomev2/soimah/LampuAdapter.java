package com.samuelbernard147.smarthomev2.soimah;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.samuelbernard147.smarthomev2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LampuAdapter extends RecyclerView.Adapter<LampuAdapter.LampuViewHolder> {
    private ArrayList<LampuItems> mData = new ArrayList<>();
    Context context;

    public LampuAdapter(Context context) {
        this.context = context;
    }

    /**
     * Gunakan method ini jika semua datanya akan diganti
     *
     * @param items kumpulan data baru yang akan mengganti semua data yang sudah ada
     */
    public void setData(ArrayList<LampuItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Gunakan method ini jika ada 1 data yang ditambahkan
     *
     * @param item data baru yang akan ditambahkan
     */
    public void addItem(final LampuItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public LampuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_soimah_lamp, viewGroup, false);
        return new LampuViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull LampuViewHolder LampuViewHolder, final int position) {
        LampuViewHolder.roomName.setText(mData.get(position).getRuangan());
        LampuViewHolder.roomStatus.setChecked(cekStatus(mData.get(position).getStatus()));
        String urlImage = "http://pameran.hopecoding.com/public/image/";
        Picasso.get()
                .load(urlImage + mData.get(position).getGambar())
                .into(LampuViewHolder.imgRoom);

        LampuViewHolder.roomStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                postStatus(isChecked, mData.get(position).getId());
            }
        });

    }

    private void postLampStatus(final int id, final int status) {
        Log.d("postLampStatus", "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://pameran.hopecoding.com/public/ubahlampu";

        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("status", status);

        client.get(context, url, param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("POST", "BERHASIL");
                Log.e("POST", "Id = " + id + "Status = " + status);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("POST", "GAGAL");
            }
        });
    }

    private void postStatus(Boolean status, int id) {
        if (status) {
            postLampStatus(id, 0);
        } else {
            postLampStatus(id, 1);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private boolean cekStatus(int i) {
        boolean status = true;
        if (i == 1) {
            status = false;
        }
        return status;
    }

    class LampuViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRoom;
        TextView roomName;
        Switch roomStatus;

        LampuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.img_room);
            roomName = itemView.findViewById(R.id.tv_room);
            roomStatus = itemView.findViewById(R.id.switch_lamp);
        }
    }
}