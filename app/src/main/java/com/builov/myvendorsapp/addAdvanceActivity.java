package com.builov.myvendorsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.builov.myvendorsapp.R;
import com.builov.myvendorsapp.adapter.listViewAdapter;
import com.builov.myvendorsapp.database.DataBaseHelper;
import com.builov.myvendorsapp.database.getDataActivity;
import com.builov.myvendorsapp.database.workWithDb;

import java.util.ArrayList;
import java.util.HashMap;

public class addAdvanceActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> dataset = new ArrayList<HashMap<String, String>>();
    //HashMap<String, String> dataItem;
    //ArrayList<String> stringArray = new ArrayList<String>();


    private DataBaseHelper mDBHelper;
    private SQLiteDatabase database;
    ListView listView;
    String rb;
    Context context;
    Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initUI();
        buttonOk.setText("OK");
        rb=getIntent().getStringExtra("rb");

        context = this.getApplicationContext();
        mDBHelper = new DataBaseHelper(this);
        database = mDBHelper.getWritableDatabase();

        if (rb.equals("1")){//в первом окне чек бокс стоял на Материалах,нужно отобразить Производителей

            rb="2";
            new workWithDb().showAll(getApplicationContext(),dataset, database, rb);
            //получили dataset из manufacturers
            rb="1";
            String[] from = {"Name", "INN"};
            int[] to = {R.id.mName_holder, R.id.mINN_Holder};

            new listViewAdapter().setAdvAdapter(from, to, rb, listView, dataset, getApplicationContext());
        }



        if (rb.equals("2")){//в первом окне чек бокс стоял на Производителях,нужно отобразить материалы

            rb="1";
            new workWithDb().showAll(getApplicationContext(),dataset, database, rb);
            String[] from = {"mName"};
            rb="2";
            final int[] to = {R.id.mName_holder};
            new listViewAdapter().setAdvAdapter(from, to, rb, listView, dataset, getApplicationContext());
        }


    }

    private void initUI() {

        listView = findViewById(R.id.detailListView);
        buttonOk = findViewById(R.id.buttonBack);

    }

}





/*final ArrayList<HashMap<String, String>> dataset = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("dataset");
        final HashMap<String,String> dataItem = (HashMap<String,String>) getIntent().getSerializableExtra("datItem");
        final String[] strings = getIntent().getStringArrayExtra("strings");
        final String js = getIntent().getStringExtra("j");


        //j=Integer.parseInt(js);

        final String row = getIntent().getStringExtra("row");
        final String rowName = getIntent().getStringExtra("rowName");
        final String tables = getIntent().getStringExtra("tables");
        final String selectName = getIntent().getStringExtra("selectName");*/

//Toast.makeText(getApplicationContext(),rowName,Toast.LENGTH_SHORT).show();
//Toast.makeText(getApplicationContext(),tables,Toast.LENGTH_SHORT).show();
//Toast.makeText(getApplicationContext(),selectName,Toast.LENGTH_SHORT).show();
//Toast.makeText(getApplicationContext(),rowName,Toast.LENGTH_SHORT).show();


