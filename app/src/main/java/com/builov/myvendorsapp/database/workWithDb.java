package com.builov.myvendorsapp.database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
public class workWithDb {

    public void showAll(String table, ArrayList<HashMap<String, String>> dataset,
                        SQLiteDatabase database, HashMap<String, String> dataItem, RadioButton rbMaterials) {
        dataset.clear();
        if (rbMaterials.isChecked()) table="materials";
        else table="manufacturers";
        String query = "SELECT * FROM " + " " + table;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
//Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            //Toast.makeText(context,"Work",Toast.LENGTH_SHORT).show();
            dataItem = new HashMap<String, String>();
//Заполняем клиента
            if (rbMaterials.isChecked()) {
                dataItem.put("Id", cursor.getString(0));
                dataItem.put("mName", cursor.getString(1));
            } else {
                dataItem.put("id", cursor.getString(0));
                dataItem.put("Name", cursor.getString(1));
                dataItem.put("INN", cursor.getString(2));
            }
//Закидываем клиента в список клиентов
            dataset.add(dataItem);
//Переходим к следующему клиенту
            cursor.moveToNext();
        }
        cursor.close();
    }
}