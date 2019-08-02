package com.builov.myvendorsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.builov.myvendorsapp.adapter.listViewAdapter;
import com.builov.myvendorsapp.database.DataBaseHelper;
import com.builov.myvendorsapp.database.getDataActivity;
import com.builov.myvendorsapp.database.sqlQuery;
import com.builov.myvendorsapp.database.workWithDb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> dataset = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> dataItem;
    ArrayList<String> stringArray = new ArrayList<String>();
    private RadioButton rbMaterials;
    private Button btnAdd;
    private Button btnSearch;
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase database;
    private EditText etSearch;
    ListView listView;
    String tblMat = "materials";
    String tblMan = "manufacturers";
    String tblSvod = "manufacturers_materials";
    String[] strings;
    int j = 0;
    String row;
    String rowName;
    String tables;
    String selectName;

    final int REQUEST_CODE_ADD=1;
    final int REQUEST_CODE_EDIT=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        row="Id";rowName="mat.Id";tables=tblMan;selectName="Name";
        registerForContextMenu(listView);
        mDBHelper = new DataBaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("Невозможно обновить БД");
        }

        database = mDBHelper.getWritableDatabase();

//Отображение данных при открытии приложения:
        new workWithDb().showAll(tblMat, dataset, database, dataItem, rbMaterials);

        String[] from = {"mName"};
        final int[] to = {R.id.mName_holder};
        new listViewAdapter().setAdapter(from, to, rbMaterials, listView, dataset, getApplicationContext());

        rbMaterials.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rbMaterials.isChecked()) {
                    row="Id";rowName="mat.Id";tables=tblMan;selectName="Name";
                    new workWithDb().showAll(tblMat, dataset, database, dataItem, rbMaterials);
                    String[] from = {"mName"};
                    int[] to = {R.id.mName_holder};
                    new listViewAdapter().setAdapter(from, to, rbMaterials, listView, dataset, getApplicationContext());
                } else {
                    row="id";rowName="man.id";tables=tblMat;selectName="mName";
                    new workWithDb().showAll(tblMan, dataset, database, dataItem, rbMaterials);
                    String[] from = {"Name", "INN"};
                    int[] to = {R.id.mName_holder, R.id.mINN_Holder};
                    new listViewAdapter().setAdapter(from, to, rbMaterials, listView, dataset, getApplicationContext());
                }
            }
        });
//Обработка кнопки Добавить
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addActivity.class);
//Запускаем активность с ожиданием возврада данных  при ее методе finish ();
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
//обработка кнопки Поиск
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (etSearch.getHint().toString().equals("Поиск")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите значение поиска", Toast.LENGTH_SHORT);
                    toast.show();
                } else
                {*/
                   // new workWithDb().showAll(tblMat,dataset,database,dataItem,rbMaterials);
                String query = "SELECT * FROM materials WHERE mName="+"'"+etSearch.getText().toString()+"'" ;
               Cursor crsr = database.rawQuery(query,null);
                crsr.moveToFirst();
                //Пробегаем по всем клиентам
                while (!crsr.isAfterLast()) {
                    Toast.makeText(getApplicationContext(),crsr.getString(0),Toast.LENGTH_SHORT).show();
                    crsr.moveToNext();
                } crsr.close();

               // }
            }
        });
    }

    //Получение и работа с данными из других активностей:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD:

                    if (data == null) {
                        return;
                    }
                    String name = data.getStringExtra("name");
                    String inn = data.getStringExtra("inn");
                    int rb = data.getIntExtra("rb", 1);
                    ContentValues contentValues = new ContentValues();
                    new sqlQuery().insert(contentValues, rbMaterials,
                            database, rb, name, inn, tblMat, tblMan, dataset, dataItem);
                    break;
                case REQUEST_CODE_EDIT:

                    assert data != null;

                    //поменять при программировании людей, сейчас обновляет только материалы
                    String mid = data.getStringExtra("Id");
                    String mname = data.getStringExtra("mName");

                    new sqlQuery().update(database,mid,mname);
                    new workWithDb().showAll(tblMat, dataset, database, dataItem, rbMaterials);
                    String[] from = {"mName"};
                    final int[] to = {R.id.mName_holder};
                    new listViewAdapter().setAdapter(from, to, rbMaterials, listView, dataset, getApplicationContext());

                    //Toast.makeText(this, "Данные из редактирования:" +data.getStringExtra("mName")+" "+ data.getStringExtra("Id"), Toast.LENGTH_SHORT).show();

                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Системная ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    //Обработка нажатия на кнопки контекстного меню:
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            //кнопка детализация
            case R.id.show_advance:
                Intent intent = new Intent(this, detailsActivity.class);
                HashMap<String, String> dataedit = dataset.get(info.position);
                Bundle bundle = new getDataActivity().showAdvance(tblMan,tblMat,tblSvod,dataedit, database,strings,j,getApplicationContext(),row,rowName,tables,selectName);
                j = bundle.getInt("j");
                strings = bundle.getStringArray("strings");
                intent.putExtra("count", j);
                stringArray.addAll(Arrays.asList(strings).subList(0, j));
                intent.putExtra("Name", stringArray);
                startActivity(intent);
                stringArray.clear();
                j = 0;
                return true;

                //кнопка редактирования
            case R.id.edit:

                Intent editIntent = new Intent(MainActivity.this, editActivity.class);
                Bundle editBundle;
                //получаем позицию и ID выбранного элемента
                HashMap<String, String> editHashMap = dataset.get(info.position);
                editBundle = new getDataActivity().getPosition(database,getApplicationContext(),editHashMap);
                //наполняем этими данными Intent и отправляем его в editActivity

                String mName = editBundle.getString("mName");
                String mId = editBundle.getString("Id");
                editIntent.putExtra("name", mName);
                editIntent.putExtra("Id",mId);
                startActivityForResult(editIntent,REQUEST_CODE_EDIT);
                return true;

                //кнопка удалить
            case R.id.delete:
                HashMap<String, String> datadelet = dataset.get(info.position);
                deleteItem(datadelet);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //работа с удалением элементов
    public void deleteItem(HashMap<String, String> data) {
        if (rbMaterials.isChecked()) {

            new sqlQuery().del(database, "Id", tblMat, data, rbMaterials, getApplicationContext());
            new sqlQuery().svodDel(database, "Id", data, rbMaterials, getApplicationContext());
            new workWithDb().showAll(tblMat, dataset, database, data, rbMaterials);
            String[] from = {"mName"};
            final int[] to = {R.id.mName_holder};
            new listViewAdapter().setAdapter(from, to, rbMaterials, listView, dataset, getApplicationContext());
        } else {
            new sqlQuery().del(database, "id", tblMan, data, rbMaterials, getApplicationContext());
            new sqlQuery().svodDel(database, "id", data, rbMaterials, getApplicationContext());
            new workWithDb().showAll(tblMan, dataset, database, data, rbMaterials);
            String[] from = {"Name", "INN"};
            final int[] to = {R.id.mName_holder};
            new listViewAdapter().setAdapter(from, to, rbMaterials, listView, dataset, getApplicationContext());
        }
    }

    //Инициализация контекстного меню:
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    //инициализация UI элементов:
    public void initUI() {
        btnAdd = findViewById(R.id.btnAdd);
        rbMaterials = findViewById(R.id.radioMat);
        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
        listView = findViewById(R.id.listHolder);
    }
}
