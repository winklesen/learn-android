package com.samuelbernard147.myreadwritefile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnNew, btnOpen, btnSave;
    EditText editContent, editTitle;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = findViewById(R.id.button_new);
        btnOpen = findViewById(R.id.button_open);
        btnSave = findViewById(R.id.button_save);
        editContent = findViewById(R.id.edit_file);
        editTitle = findViewById(R.id.edit_title);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        path = getFilesDir();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }

//    Di reset edit text
    public void newFile(){
        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this,"Clearing file",Toast.LENGTH_SHORT).show();
    }

    public void saveFile(){
//        Cek kalo kosong
        if (editTitle.getText().toString().isEmpty()){
            Toast.makeText(this,"Title harus diisi terlebih dahulu",Toast.LENGTH_SHORT).show();
        }else if (editContent.getText().toString().isEmpty()){
            Toast.makeText(this,"Konten harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFileName(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel,this);
            Toast.makeText(this,"Saving" + fileModel.getFileName()+" file",Toast.LENGTH_SHORT).show();
        }
    }

//    Load data file berdasarkan title
    private void loadData(String title){
        FileModel fileModel = FileHelper.readFromFile(this,title);
        editTitle.setText(fileModel.getFileName());
        editContent.setText(fileModel.getData());
        Toast.makeText(this,"Loading"+fileModel.getFileName()+"data",Toast.LENGTH_SHORT).show();
    }

    public void openFile(){
        showList();
    }

    private void showList(){
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, path.list());

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
