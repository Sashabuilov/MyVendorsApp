package com.builov.myvendorsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import com.builov.myvendorsapp.adapter.listViewAdapter;
import com.builov.myvendorsapp.database.DataBaseHelper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
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
                    new workWithDb().showAll(tblMat, dataset, database, dataItem, rbMaterials);
                    String[] from = {"mName"};
                    int[] to = {R.id.mName_holder};
                    new listViewAdapter().setAdapter(from, to,
                            rbMaterials, listView, dataset, getApplicationContext());
                } else {
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
                startActivityForResult(intent, 1);
            }
        });
//обработка кнопки Поиск
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSearch.getHint().toString().equals("Поиск")) {
                    Toast toast =
                            Toast.makeText(getApplicationContext(), "Введите значение поиска",
                                    Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    //при получении данных с активности AddActivity начинаем складывать их  в стек данных(при помощи метода sqlQuery().insert)

    @Override
    protected void onActivityResult(int requestCode, int
            resultCode, @Nullable Intent data) {
        if (data == null) {
            return;
        }
        String name = data.getStringExtra("name");
        String inn = data.getStringExtra("inn");
        int rb = data.getIntExtra("rb", 1);
        ContentValues contentValues = new ContentValues();
        new sqlQuery().insert(contentValues, rbMaterials,
                database, rb, name, inn, tblMat, tblMan, dataset, dataItem);
    }

    //инициализация UI элементов:
    public void initUI() {
        btnAdd = findViewById(R.id.btnAdd);
        rbMaterials = findViewById(R.id.radioMat);
        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
        listView = findViewById(R.id.listHolder);
    }

    //создание контекстного меню:
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
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
                advanceItem(dataedit);
               // intent.putExtra("count", j);

                //Toast.makeText(getApplicationContext(),Integer.toString(j),Toast.LENGTH_SHORT).show();
                stringArray.addAll(Arrays.asList(strings).subList(0, j));
                intent.putExtra("count", j);
                intent.putExtra("Name", stringArray);
                //startActivity(intent);
                stringArray.clear();
                j = 0;
                return true;
//кнопка редактировать
            case R.id.edit:

                return true;
//кнопка удалить
            case R.id.delete:
                HashMap<String, String> datadelet =
                        dataset.get(info.position);
                deleteItem(datadelet);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //работа с удалением элементов
    public void deleteItem(HashMap<String, String> data) {
        if (rbMaterials.isChecked()) {
            new sqlQuery().del(database, "Id", tblMat, data, rbMaterials);
            new sqlQuery().svodDel(database, "Id", tblMat, data, rbMaterials,getApplicationContext());
            new workWithDb().showAll(tblMat, dataset, database, data, rbMaterials);
            String[] from = {"mName"};
            final int[] to = {R.id.mName_holder};
            new listViewAdapter().setAdapter(from, to, rbMaterials, listView, dataset, getApplicationContext());
        } else {
            new sqlQuery().del(database, "id", tblMan, data, rbMaterials);
            new sqlQuery().svodDel(database, "id", tblMan, data, rbMaterials,getApplicationContext());
            new workWithDb().showAll(tblMan, dataset, database, data, rbMaterials);
            String[] from = {"Name", "INN"};
            final int[] to = {R.id.mName_holder};
            new listViewAdapter().setAdapter(from, to, rbMaterials, listView, dataset, getApplicationContext());
        }
    }

    //работа с детализацией
    public void advanceItem(HashMap<String, String> data) {
        String query = "SELECT Name FROM " + tblMan + " as man," +
                tblMat + " as mat," + tblSvod + " as svod " +
                "WHERE man.id=svod.manufacturers_id " +
                "AND mat.Id=svod.materials_id " +
                "AND mat.Id=" + data.get("Id");
        Cursor cursor = database.rawQuery(query, null);
//получаем длину таблицы
        long rowCount = DatabaseUtils.queryNumEntries(database, "manufacturers");
        String str = Long.toString(rowCount);
        int i = Integer.parseInt(str);
        strings = new String[i];
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            strings[j] = cursor.getString(0);
            Toast.makeText(getApplicationContext(),"Work",Toast.LENGTH_SHORT).show();
            j = j + 1;
            cursor.moveToNext();
        }
        cursor.close();
    }
}

//Integer.toString(i)