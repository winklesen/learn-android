package com.samuelbernard147.myrecyclerview;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener{
    private int position;
    private OnItemClickCallback onItemClickCallback;
    CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback){
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(View v, int position);
    }
}
