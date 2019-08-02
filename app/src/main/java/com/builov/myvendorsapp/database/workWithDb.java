package com.builov.myvendorsapp.database;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.HashMap;
public class workWithDb {
    public void showAll(String table, ArrayList<HashMap<String, String>> dataset,
                        SQLiteDatabase database, HashMap<String, String> data, RadioButton rbMaterials) {
        dataset.clear();
        String query = "SELECT * FROM " + " " + table;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
//Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            data = new HashMap<String, String>();
//Заполняем клиента
            if (rbMaterials.isChecked()) {
                //data.put("Id", cursor.getString(0));
                data.put("mName", cursor.getString(1));
            } else {
                //data.put("id", cursor.getString(0));
                data.put("Name", cursor.getString(1));
                data.put("INN", cursor.getString(2));
            }
//Закидываем клиента в список клиентов
            dataset.add(data);
//Переходим к следующему клиенту
            cursor.moveToNext();
        }
        cursor.close();
    }
}