package com.samuelbernard147.smarthomev2.alirkan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.samuelbernard147.smarthomev2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity {
    ExpendableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
//        Rubah title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Bantuan");
        }
//        Buat back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ListExpendable
        expListView = findViewById(R.id.elv_about);
        prepareListData();
        listAdapter = new ExpendableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void prepareListData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add(getResources().getString(R.string.penyiraman));
        listDataHeader.add(getResources().getString(R.string.Kelembapan));

        List<String> penyiraman = new ArrayList<String>();
        penyiraman.add(getResources().getString(R.string.petunjukPenyiraman1));
        penyiraman.add(getResources().getString(R.string.petunjukPenyiraman2));

        List<String> kelembapan = new ArrayList<String>();
        kelembapan.add(getResources().getString(R.string.petunjukKelembapan1));
        kelembapan.add(getResources().getString(R.string.petunjukKelembapan2));
        kelembapan.add(getResources().getString(R.string.petunjukKelembapan3));

        listDataChild.put(listDataHeader.get(0), penyiraman);
        listDataChild.put(listDataHeader.get(1), kelembapan);
    }
}
