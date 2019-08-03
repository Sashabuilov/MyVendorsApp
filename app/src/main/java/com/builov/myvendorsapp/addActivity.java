package com.builov.myvendorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

public class addActivity extends AppCompatActivity {
    Button add;
    RadioButton rbMater;
    RadioButton rbManuf;
    EditText etName;
    EditText etINN;
    EditText etchange;
    String rb="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        initUI();
        setTitle("Добавление");
        rbMater.isChecked();
        etINN.setEnabled(false);
        etINN.setText("Не доступно");

        rbMater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton
                                                 buttonView, boolean isChecked) {
                if (rbMater.isChecked()) {
                    etINN.setEnabled(false);rb="1";
                    etINN.setText("Не доступно");
                } else {
                    etINN.setEnabled(true);rb="2";
                    etINN.setText("");
                }
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
    }

    public void initUI() {
        rbManuf = findViewById(R.id.rbManuf);
        rbMater = findViewById(R.id.rbMater);
        etName = findViewById(R.id.etName);
        etINN = findViewById(R.id.etInn);
        //etchange = findViewById(R.id.etMan);
        add = findViewById(R.id.button_add_new);
    }
}