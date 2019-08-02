package com.builov.myvendorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class editActivity extends AppCompatActivity {

    EditText etName;
    Button btn_edit_cancel;
    Button btn_edit_OK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initUI();
        final Intent intent=new Intent();

        String name = this.getIntent().getStringExtra("name");
        final String id = this.getIntent().getStringExtra("Id");
        etName.setText(name);

        btn_edit_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("mName", etName.getText().toString());
                intent.putExtra("Id", id);
                //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        btn_edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setResult(RESULT_OK, intent);

    }

    private void initUI(){
        etName = findViewById(R.id.et_edit_name);
        btn_edit_cancel = findViewById(R.id.btn_edit_cancel);
        btn_edit_OK = findViewById(R.id.btn_edit_ok);
    }
}
