package com.samuelbernard147.smarthomev2.soimah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        setContentView(R.layout.activity_soimah_help);
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

        listDataHeader.add(getResources().getString(R.string.lampu));
        listDataHeader.add(getResources().getString(R.string.door_lock));

        List<String> lampu = new ArrayList<String>();
        lampu.add(getResources().getString(R.string.carapake_lampu1));

        List<String> doorLock = new ArrayList<String>();
        doorLock.add(getResources().getString(R.string.carapake_doorlock1));
        doorLock.add(getResources().getString(R.string.carapake_doorlock2));


        listDataChild.put(listDataHeader.get(0), lampu);
        listDataChild.put(listDataHeader.get(1), doorLock);
    }
}
