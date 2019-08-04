package com.builov.myvendorsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.builov.myvendorsapp.R;
import com.builov.myvendorsapp.adapter.listViewAdapter;
import com.builov.myvendorsapp.database.workWithDb;

import java.util.ArrayList;
import java.util.HashMap;

public class addAdvanceActivity extends AppCompatActivity {

    ListView addAdvanceListView;
    ArrayList<String> stringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initUI();
        addAdvanceListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        String[] from = {"mName"};
        final int[] to = {R.id.mName_holder};

        String rb=this.getIntent().getStringExtra("rb");
        //new workWithDb().showAll(getApplicationContext(),dataset, database, rb);

        //ArrayList<HashMap<String, String>> dataset = this.getIntent().getArraList

        //new listViewAdapter().setAdapter(from, to, rb, addAdvanceListView, dataset, getApplicationContext());
    }

    private void initUI(){

        addAdvanceListView = findViewById(R.id.detailListView);

    }

}
