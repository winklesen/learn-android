package com.samuelbernard147.smartagriculture.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.samuelbernard147.smartagriculture.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionDialogFragment extends DialogFragment implements View.OnClickListener {

    Button btnChoose, btnClose;
    EditText edtAuthor;
    OnOptionDialogListener optionDialogListener;

    public OptionDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option_author, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnChoose = view.findViewById(R.id.btn_save);
        btnChoose.setOnClickListener(this);
        btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);
        edtAuthor = view.findViewById(R.id.edt_author);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*
        Saat attach maka set optionDialogListener dengan listener dari detailCategoryFragment
         */
        Fragment fragment = getParentFragment();

//            this.optionDialogListener = MainActivity.optionDialogListener;

    }

    @Override
    public void onDetach() {
        super.onDetach();

        /*
        Saat detach maka set null pada optionDialogListener
         */
        this.optionDialogListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                Toast.makeText(getActivity(), "Batal", Toast.LENGTH_SHORT).show();
                getDialog().cancel();
                break;

            case R.id.btn_save:
                Toast.makeText(getActivity(), "Simpan", Toast.LENGTH_SHORT).show();
                break;
        }
        getDialog().dismiss();
    }

    public interface OnOptionDialogListener {
        void onOptionChosen(String text);
    }
}