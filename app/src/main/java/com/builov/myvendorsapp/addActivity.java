package com.builov.myvendorsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class addActivity extends AppCompatActivity {
    Button add;
    Button btn_add_advance;
    Button btn_cancel;
    EditText etName;
    EditText etINN;
    String rb="";
    TextView tv_Choise;
    ListView addListView;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        mContext = this.getApplicationContext();



        /*final ArrayList<HashMap<String,String>> dataset = (ArrayList<HashMap<String,String>>) getIntent().getSerializableExtra("dataset");
        final HashMap<String,String> dataItem = (HashMap<String,String>) getIntent().getSerializableExtra("datItem");
        final String[] strings = getIntent().getStringArrayExtra("strings");
        final String j = getIntent().getStringExtra("j");

        final String row = getIntent().getStringExtra("row");
        final String rowName = getIntent().getStringExtra("rowName");
        final String tables = getIntent().getStringExtra("tables");
        final String selectName = getIntent().getStringExtra("selectName");*/

        initUI();
        setTitle("Добавление");
        etINN.setEnabled(false);
        etINN.setText("Не доступно");
        rb = this.getIntent().getStringExtra("rb");

        if (rb.equals("1")) {
            etINN.setEnabled(false);
            etINN.setText("Не доступно");
            setTitle("МАТЕРИАЛЫ");
        }

        if (rb.equals("2")){
            etINN.setEnabled(true);
            etINN.setText("");
            setTitle("ПРОИЗВОДИТЕЛИ");
            tv_Choise.setText("ВЫБЕРИТЕ МАТЕРИАЛ");
        }

        btn_add_advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Кнопка ДОБАВИТЬ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(addActivity.this, addAdvanceActivity.class);
                intent.putExtra("rb", rb);

                /*intent.putExtra("dataset",dataset);
                intent.putExtra("dataItem", dataItem);
                intent.putExtra("strings",strings);
                intent.putExtra("j",j);

                intent.putExtra("row",row);
                intent.putExtra("rowName",rowName);
                intent.putExtra("tables",tables);
                intent.putExtra("selectName",selectName);
*/
                startActivityForResult(intent,1);

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("inn", etINN.getText().toString());
                intent.putExtra("rb", rb);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = data.getBundleExtra("bundle");

        String Id = bundle.getString("Id");
        String mName = bundle.getString("mName");

        Toast.makeText(getApplicationContext(), mName,Toast.LENGTH_SHORT).show();

    }

    public void initUI() {
        etName = findViewById(R.id.etName);
        etINN = findViewById(R.id.etInn);
        add = findViewById(R.id.button_add_new);
        btn_add_advance =findViewById(R.id.button_add);
        btn_cancel=findViewById(R.id.btn_add_cancel);
        tv_Choise=findViewById(R.id.tv_Choise);
        addListView = findViewById(R.id.addListView);
    }
}