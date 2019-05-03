package com.samuelbernard147.mylistview;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] dataName;
    String[] dataDescription;
    TypedArray dataPhoto;
    HeroAdapter adapter;

//    String[] dataName = {
//            "Cut Nyak Dien",
//            "Ki Hajar Dewantara",
//            "Moh Yamin",
//            "Patimura",
//            "R A Kartini",
//            "Sukarno"
//    };

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv_list);
//        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, dataName);
//        listView.setAdapter(adapter);

        adapter = new HeroAdapter(this);
        listView = findViewById(R.id.lv_list);
        listView.setAdapter(adapter);

        prepare();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this,dataName[position],Toast.LENGTH_LONG).show();
            }
        });
    }



    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_name);
        dataDescription = getResources().getStringArray(R.array.data_description);
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
    }

    private void addItem() {
        ArrayList<Hero> heroes = new ArrayList<>();

        for (int i = 0; i < dataName.length; i++) {
            Hero hero = new Hero();
            hero.setPhoto(dataPhoto.getResourceId(i, -1));
            hero.setName(dataName[i]);
            hero.setDescription(dataDescription[i]);
            heroes.add(hero);
        }
        adapter.setHeroes(heroes);
    }

}
