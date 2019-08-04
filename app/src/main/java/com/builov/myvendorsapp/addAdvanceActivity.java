package com.builov.myvendorsapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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
    HashMap<String, String> dataItem;
    //ArrayList<String> stringArray = new ArrayList<String>();


    private DataBaseHelper mDBHelper;
    private SQLiteDatabase database;
    ListView listView;
    String rb;
    Context context;
    Button buttonOk;
    //CheckBox mCheckBox;
    String row;
    //String rowName;
    String tables;

    String tblMat = "materials";
    String tblMan = "manufacturers";
    //String tblSvod = "manufacturers_materials";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initUI();


        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        buttonOk.setText("OK");

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });
        rb=getIntent().getStringExtra("rb");

        context = this.getApplicationContext();
        mDBHelper = new DataBaseHelper(this);
        database = mDBHelper.getWritableDatabase();


        if(rb.equals("1")){rb="2";} else if (rb.equals("2")){rb="1";}

        //Toast.makeText(getApplicationContext(),"Измененный= "+rb,Toast.LENGTH_SHORT).show();

        new workWithDb().showAll(getApplicationContext(),dataset, database, rb);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                HashMap<String, String> editHashMap = dataset.get(position);

                if (rb.equals("1")){
                    tables=tblMan;
                    row = "id";
                    rb="2";
                } else {

                    tables=tblMat;
                    row = "Id";
                    rb="1";
                }

                Bundle bundle = new getDataActivity().getPosition(database,context,editHashMap,tables,row,rb);

                if (rb.equals("1")){rb="2";}

                else if(rb.equals("2")){rb="1";}

                setResult(RESULT_OK, null);
                finish();

            }
        });



        if (rb.equals("1")){//в первом окне чек бокс стоял на Материалах,нужно отобразить Производителей

            rb="2";
            new workWithDb().showAll(getApplicationContext(),dataset, database, rb);
            //получили dataset из manufacturers
            rb="1";
            String[] from = {"Name", "INN"};
            int[] to = {R.id.mName_holder, R.id.mINN_Holder};
            new listViewAdapter().setAdvAdapter(from, to, rb, listView, dataset, getApplicationContext());

        }

        if (rb.equals("2")) {//в первом окне чек бокс стоял на Производителях,нужно отобразить материалы
            rb = "1";
            new workWithDb().showAll(getApplicationContext(), dataset, database, rb);
            String[] from = {"mName"};
            rb = "2";
            final int[] to = {R.id.mName_holder};
            new listViewAdapter().setAdvAdapter(from, to, rb, listView, dataset, getApplicationContext());
        }
    }

    private void initUI() {
        listView = findViewById(R.id.detailListView);
        buttonOk = findViewById(R.id.buttonBack);
    }


    private void getData(){



    }
}
